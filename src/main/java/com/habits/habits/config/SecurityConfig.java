package com.habits.habits.config;

import com.habits.habits.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
public class SecurityConfig {
    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }



    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.formLogin(Customizer.withDefaults())
                .formLogin(form -> form
                        .loginPage("/login")
                     //   .defaultSuccessUrl("/profile", true) тут начальный страница болу керек
                        .permitAll()
                )
                // Конфигурация выхода из системы
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers(
                                new OrRequestMatcher(
                                        new AntPathRequestMatcher("/logout", "POST")
                                )
                        )
                )


                .authorizeHttpRequests(req -> req
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/login/**","/register/**").permitAll()
                        .requestMatchers("/home/**").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/img/**" , "/scss/**").permitAll()
                        .requestMatchers("/admin/**").hasAnyAuthority("admin")
                        .requestMatchers("/profile" , "/habits","/calendar","/start","/achievement", "/feedback","/feedbacks","/game_shablon","/game1","/game2","/game3","/instructions","/payment").authenticated()

                        .requestMatchers("/user/**").hasAnyAuthority("admin", "user")
                        .anyRequest().authenticated())
                .userDetailsService(userService).build();
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        HiddenHttpMethodFilter filter = new HiddenHttpMethodFilter();
        return filter;
    }


}

