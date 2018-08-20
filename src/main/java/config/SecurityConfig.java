package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity(debug = false)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/").hasAnyRole("ANONYMOUS","USER")
                .antMatchers("/login/*").hasAnyRole("ANONYMOUS","USER")
                .antMatchers("/logout/*").hasAnyRole("ANONYMOUS","USER")
                .antMatchers("/admin/*").hasRole("ADMIN")
                .antMatchers("/**").hasRole("USER")
                .and().formLogin()
                    .loginPage("/login/form")
                    .loginProcessingUrl("/login")
                    .failureUrl("/login/form?error")
                    .usernameParameter("username")
                    .passwordParameter("password")
                .and().httpBasic()
                .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login/form?logout")
                .and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("lisi").password("lisi").roles("USER")
                .and()
                .withUser("admin").password("admin").roles("ADMIN");

    }
}
