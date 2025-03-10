//package com.Ankssss.LoanManagement.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import javax.servlet.http.HttpServletResponse;
//
//@Configuration
//@EnableWebSecurity
//public class MyConfig extends WebSecurityConfigurerAdapter {
//
//    @Bean
//    public UserDetailsService getUserDetailService(){
//        return new CustomUserDetailService();
//    }
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(getUserDetailService());
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        return daoAuthenticationProvider;
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws  Exception{
//        auth.authenticationProvider(authenticationProvider());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/auth/register", "/auth/login").permitAll()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/customer/**").hasRole("CUSTOMER")
//                .anyRequest().authenticated()
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint((request, response, authException) -> {
//                    response.setContentType("application/json");
//                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                    response.getWriter().write("{\"error\": \"Forbidden\", \"message\": \"Access Denied\"}");
//                    response.getWriter().flush();
//                })
//                .and()
//                .logout()
//                .logoutUrl("/auth/logout")  // Set logout URL
//                .logoutSuccessHandler((request, response, authentication) -> {
//                    response.setContentType("application/json");
//                    response.setCharacterEncoding("UTF-8");
//                    response.getWriter().write("{\"success\": true, \"message\": \"Logout Successful\"}");
//                    response.getWriter().flush();
//                })
//                .deleteCookies("JSESSIONID")  // Remove session cookie
//                .invalidateHttpSession(true)  // Invalidate session
//                .permitAll();
//    }
//}
