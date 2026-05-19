package uz.java.kpisystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.java.kpisystem.config.CustomUserDetails;
import uz.java.kpisystem.entity.User;
import uz.java.kpisystem.repository.UserRepository;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    private final static Function<String, SimpleGrantedAuthority> authority = SimpleGrantedAuthority::new;

    @Override
    public CustomUserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(() ->
                new UsernameNotFoundException("User not found with id: " + userId));

        Set<GrantedAuthority> authorities = new HashSet<>();
        if (Objects.nonNull(user.getRole()))
            authorities.add(authority.apply(user.getRole().getAuthority()));

        return new CustomUserDetails(user, authorities);
    }

}
