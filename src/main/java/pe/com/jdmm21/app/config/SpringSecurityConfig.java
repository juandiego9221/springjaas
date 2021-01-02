package pe.com.jdmm21.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.authentication.jaas.DefaultJaasAuthenticationProvider;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import pe.com.jdmm21.app.loginmodule.JaasAuthorityGranter;

import javax.security.auth.login.AppConfigurationEntry;
import java.util.HashMap;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = JaasAuthorityGranter.class)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    JaasAuthorityGranter jaasAuthorityGranter = new JaasAuthorityGranter();

    @Bean
    DefaultJaasAuthenticationProvider jaasAuthenticationProvider() {
        AppConfigurationEntry appConfig = new AppConfigurationEntry("pe.com.jdmm21.app.loginmodule.JaasLoginModule",
                AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, new HashMap());
        InMemoryConfiguration memoryConfiguration = new InMemoryConfiguration(new AppConfigurationEntry[]{appConfig});
        DefaultJaasAuthenticationProvider def = new DefaultJaasAuthenticationProvider();
        def.setConfiguration(memoryConfiguration);
        def.setAuthorityGranters(new AuthorityGranter[]{jaasAuthorityGranter});
        return def;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jaasAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().regexMatchers("/admin/*").hasRole("ADMIN").anyRequest()
                .authenticated().and().httpBasic();
        http.formLogin().loginPage("/login").permitAll();
        http.logout().logoutSuccessUrl("/");
        http.exceptionHandling().accessDeniedPage("/noaccess");
    }
}
