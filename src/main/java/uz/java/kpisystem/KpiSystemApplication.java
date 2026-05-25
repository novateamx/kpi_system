package uz.java.kpisystem;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties
public class KpiSystemApplication {

//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final RoleRepository roleRepository;
//
//    public KpiSystemApplication(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.roleRepository = roleRepository;
//    }

    public static void main(String[] args) {
        SpringApplication.run(KpiSystemApplication.class, args);
    }


//    @PostConstruct
//    public void createUser() {
//        User user = new User();
//        user.setUsername("admin");
//        user.setPassword(passwordEncoder.encode("1234"));
//        user.setFirstName("Admin");
//        Role role = roleRepository.findById(1L).orElse(null);
//        user.setRole(role);
//        userRepository.save(user);
//        log.info("User created");
//    }
//    library
//    framework

//            kutubxonalani saqlaydigan bizada pom.xml(faqat maven projectda) bizani loyihada gradle bolgani uchun build.gradle

//            Spring bu default da Apache Tomcat server da kotariladi uning default port 8080
//            Spring modullari: Spring Core, Spring Boot, Spring MVC(Ham backend ham Front yozsak boladi), Spring Security, Spring Cloud, Spring AOP
//            REST - Representation Statement Transfer (http, https)
//            Methodlari: GET, POST, PUT, DELETE, PATCH
//            REST alternativlari: SOAP, GraphQl, JSONRPC, Websocket(ws, wss)
//            Qanday datalar bilan ishlaydi: JSON, text, file(har qanday turdagi data)
//            Front request yuboradi, Backend uni qabul qilib validatsiyaga tekshiradi, service laga yuboradi va bazaga saqlab kn Frontga response yuboradi
//
//           Request turlari: param, body, pathvariable, file format, header, cookie
//
//            Spring da object bu Bean deyiladi
//            IOC(Inversion of Control) - bu 1ta kontayner bo'lib barcha bean larni yigadi.
//            bean olish, bean ustida ishlash bilan AplicationContect(BeanFactory) shugullanadi
//            Spring da ko'proq annotation lar bilan ishlaymiz(ex: @Getter, @Setter)
//     DI(Dependency Injection) - bu 1 ta bean class ichida boshqa bir bean class ni chaqirib ishlatish uchun kerak
//     1. Field Injection
//     2. Constructor-based injection
//     3. Setter-based Injection
//     4. @RequiredArgsConstructor annotation bilan

//    Spring da layer lar bor: 1) Model layer(entity, dto classlar yaratiladi)
//        2) Web layer(bunda controller class lar)
//        3) Data Access Layer(repository class lar yaratiladi)
//        4) Business Logic layer(service class lar yoziladi)

//    Class ni bean qiladigan annotation lar:
//    1) @Component, 2) @Service, 3) @Repository, @RestController

//    application.yml
//    bunda constant qiymatlar, boshqa serverlar bilan integratsiyalar
//    va configuration lar yoziladi.
//    2-usuli application.yml bilan

//    .gitignore bizada git ga push qilinmaydigan narsalar

}
