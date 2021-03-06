package www.sahara.com.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import www.sahara.com.repository.UserRepository;
import www.sahara.com.service.UserService;
@Configuration
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	
	/*
	 * @Value("${spring.sql.users-query}") private String userQuery;
	 * 
	 * @Value("${spring.sql.roles-query}") private String roleQuery;
	 */ 
	
	

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService)
			.passwordEncoder(passwordEncoder);
		
		/*jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery(userQuery)
			.authoritiesByUsernameQuery(roleQuery)			
			.passwordEncoder(passwordEncoder);*/
	}
	
	/*
	 * @Bean DaoAuthenticationProvider authenticationProvider() {
	 * DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	 * provider.setUserDetailsService(userService);
	 * provider.setPasswordEncoder(passwordEncoder); return provider; }
	 */
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().disable().authorizeRequests()
			.antMatchers("/login").permitAll()
			.antMatchers("/reg").permitAll()
			.antMatchers("/productPage").permitAll()
			.antMatchers("/user/**").hasRole("USER")
			.antMatchers("/admin/**").hasRole("ADMIN")
			
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.successHandler(authenticationSuccessHandler)
			//.loginPage("/login")
			.failureUrl("/failuer");
			//.defaultSuccessUrl("/home/welcome");
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/resources/**");
	}
}
