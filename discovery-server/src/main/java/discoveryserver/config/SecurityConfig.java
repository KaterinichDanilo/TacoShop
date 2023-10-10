package discoveryserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${eureka.username}")
    private String EUREKA_USERNAME;
    @Value("${eureka.password}")
    private String EUREKA_PASSWORD;

//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        System.out.println(EUREKA_USERNAME);
//        System.out.println(EUREKA_PASSWORD);
//        manager.createUser(
//                User.withDefaultPasswordEncoder()
//                .username(EUREKA_USERNAME)
//                .password(EUREKA_PASSWORD)
//                .roles("USER")
//                .build());
//        return manager;
//    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        System.out.println(EUREKA_USERNAME);
//        System.out.println(EUREKA_PASSWORD);
//
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(EUREKA_PASSWORD);
//
//        UserDetails user = User.builder()
//                .username(EUREKA_USERNAME)
//                .password(encodedPassword)
//                .roles("USER")
//                .build();
//
//        manager.createUser(user);
//        return manager;
//    }

//    @Bean
//    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder =
//                http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.inMemoryAuthentication()
//                .passwordEncoder(NoOpPasswordEncoder.getInstance())
//                .withUser(EUREKA_USERNAME)
//                .password(EUREKA_PASSWORD)
//                .authorities("USER");
//        return authenticationManagerBuilder.build();
//    }

//    @Bean
//    public DefaultSecurityFilterChain springSecurityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf()
//                .disable()
//                .authorizeRequests().anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
//        return http.build();
//    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        System.out.println(EUREKA_USERNAME);
        System.out.println(EUREKA_PASSWORD);
        UserDetails user = User.withDefaultPasswordEncoder()
                .username(EUREKA_USERNAME)
                .password(EUREKA_PASSWORD)
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((authz) -> authz
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());
        return http.build();
    }
}