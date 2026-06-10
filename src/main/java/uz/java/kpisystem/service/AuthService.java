package uz.java.kpisystem.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import uz.java.kpisystem.client.KeycloakServiceClient;
import uz.java.kpisystem.config.CustomAuthenticationProvider;
import uz.java.kpisystem.config.CustomUserDetails;
import uz.java.kpisystem.dto.TokenResponse;
import uz.java.kpisystem.dto.UserInfo;
import uz.java.kpisystem.entity.User;
import uz.java.kpisystem.exception.GenericRuntimeException;
import uz.java.kpisystem.repository.UserRepository;

import java.io.IOException;

@Service
public class AuthService {

    private final KeycloakServiceClient keycloakServiceClient;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final UserRepository userRepository;
    @Value("${app.keycloak.client-id}")
    private String clientId;

    @Value("${app.keycloak.client-secret}")
    private String clientSecret;

    public AuthService(@Value("${app.keycloak.keycloak-server-url}") String baseUrl,
                       CustomAuthenticationProvider customAuthenticationProvider, UserRepository userRepository) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        this.customAuthenticationProvider = customAuthenticationProvider;
        this.userRepository = userRepository;
        this.keycloakServiceClient = retrofit.create(KeycloakServiceClient.class);
    }


    public TokenResponse login(String username, String password) { // 1234
        try {
            Response<TokenResponse> response = keycloakServiceClient.getToken(
                    "password",
                    clientId,
                    clientSecret,
                    username,
                    password
            ).execute();
            String errorBody = "";
            if (!response.isSuccessful()) {
                try {
                    if (response.errorBody() != null) {
                        errorBody = response.errorBody().string();
                        throw new BadRequestException("Token olishda xatolik: " + errorBody);
                    }
                } catch (IOException e) {
                    throw new BadRequestException("Xatolikni o'qishda muammo yuz berdi.");
                }
            }
            if (response.body() == null) {
                throw new BadRequestException("Token olishda xatolik : response.body() == null" + "\n" + response.message() + "\n" + " --- getPinfl");
            }
            if (response.body().getAccessToken() == null) {
                throw new BadRequestException("Token olishda xatolik : response.body().getAccessToken() == null" + "\n" + response.message() + "\n" + " --- getPinfl");
            }
            DecodedJWT data = JWT.decode(response.body().getAccessToken());

            customAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(
                    data.getClaim("preferred_username").asString(), password
            ));
            return TokenResponse.builder()
                    .accessToken(response.body().getAccessToken())
                    .refreshToken(response.body().getRefreshToken())
                    .build();
        } catch (IOException e) {
            throw new GenericRuntimeException("Token olishda xatolik: " + e.getMessage());
        }
    }

    public UserInfo me() {
        CustomUserDetails userDetails =
                (CustomUserDetails) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(userDetails.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        return UserInfo.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .build();
    }
}
