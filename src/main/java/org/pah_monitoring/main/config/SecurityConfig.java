package org.pah_monitoring.main.config;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.utils.UrlUtils;
import org.pah_monitoring.main.entities.main.examinations.indicators.AnalysisFile;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.utils.UrlUtilsException;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common.FileIndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Configuration
public class SecurityConfig {

    @Qualifier("analysisFileService")
    private FileIndicatorService<AnalysisFile> analysisFileService;

    private CurrentUserCheckService checkService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Autowired
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/uploads/**").access(authorizationDecision())
                        .anyRequest().permitAll()
                )
                // todo: remove csrf in final version
                .csrf(
                        AbstractHttpConfigurer::disable
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login/processing")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                );
        return http.build();
    }

    private AuthorizationManager<RequestAuthorizationContext> authorizationDecision() {

        return (auth, obj) -> {

            String filename;
            try {
                filename = UrlUtils.getLastUrlPart(URLDecoder.decode(obj.getRequest().getRequestURI(), StandardCharsets.UTF_8));
            } catch (UrlUtilsException e) {
                return new AuthorizationDecision(false);
            }

            AnalysisFile analysisFile;
            try {
                analysisFile = analysisFileService.findByFilename(filename);
            } catch (DataSearchingServiceException e) {
                return new AuthorizationDecision(false);
            }

            Patient patient = analysisFile.getPatient();
            if (!(
                    patient.isActive() && (checkService.isSelf(patient) || checkService.isOwnDoctor(patient)) ||
                    patient.isNotActive() && checkService.isDoctorFromSameHospital(patient.getHospital())
            )) {
                return new AuthorizationDecision(false);
            } else {
                return new AuthorizationDecision(true);
            }

        };

    }

}
