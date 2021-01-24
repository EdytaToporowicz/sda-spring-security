package pl.sda.blogservicedata;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public SpringSecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService userAuthService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userAuthService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/blogPosts", "/api/*/comments").authenticated()
                .antMatchers("/blogPosts/*", "/blogPostsForm").authenticated()
                .anyRequest().permitAll()
                .and()
                .httpBasic()
                .and()
                .formLogin()
                .and()
                .csrf(csrf -> csrf.ignoringAntMatchers("/api/**"));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

//                .withUser("test1").password(passwordEncoder.encode("pass1")).roles("READER")
//                .and()
//                .withUser("test2").password(passwordEncoder.encode("pass2")).roles("AUTHOR");
    }
}
