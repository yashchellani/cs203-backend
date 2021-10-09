package net.codejava.CodeJavaApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    private UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userSvc){
        this.userDetailsService = userSvc;
    }
    
    /** 
     * Attach the user details and password encoder.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
        throws Exception {
        auth
        .userDetailsService(userDetailsService)
        .passwordEncoder(encoder());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .httpBasic()
            .and() //  "and()"" method allows us to continue configuring the parent
        .authorizeRequests()
            //authorization for business
            .antMatchers(HttpMethod.POST, "/users/*/businesses").hasAnyRole("ADMIN", "USER")
            .antMatchers(HttpMethod.PUT, "/users/*/businesses/*").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/users/*/businesses/*").hasRole("ADMIN")
            //authorization for restrictions
            .antMatchers(HttpMethod.GET, "/restrictions").hasRole("ADMIN")
            .antMatchers(HttpMethod.POST, "/restrictions").hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT, "/restrictions/*").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/restrictions/*").hasRole("ADMIN")
            .antMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
            .antMatchers(HttpMethod.GET, "/users/search").permitAll()
            .antMatchers(HttpMethod.POST, "/users").permitAll()
            .and()
        .csrf().disable() // CSRF protection is needed only for browser based attacks
        .formLogin().disable()
        .headers().disable()
        ;
    }
    // @Override
    // protected void configure(HttpSecurity http) throws Exception {
    //     http
    //     .httpBasic()
    //         .and() //  "and()"" method allows us to continue configuring the parent
    //     .authorizeRequests()
    //         .antMatchers(HttpMethod.POST, "/businesses").permitAll()
    //         .antMatchers(HttpMethod.PUT, "/businesses/*").permitAll()
    //         .antMatchers(HttpMethod.DELETE, "/businesses/*").permitAll()
    //         // your code here
    //         .antMatchers(HttpMethod.POST, "/restrictions").authenticated()
    //         .antMatchers(HttpMethod.PUT, "/restrictions/*").permitAll()
    //         .antMatchers(HttpMethod.DELETE, "/restrictions/*").permitAll()
    //         .antMatchers(HttpMethod.GET, "/users").permitAll()
    //         .antMatchers(HttpMethod.POST, "/users").permitAll()
    //         .and()
    //     .csrf().disable() // CSRF protection is needed only for browser based attacks
    //     .formLogin().disable()
    //     .headers().disable()
    //     ;
    // }

    /**
     * @Bean annotation is used to declare a PasswordEncoder bean in the Spring application context. 
     * Any calls to encoder() will then be intercepted to return the bean instance.
     */
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}