package www.sahara.com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import www.sahara.com.domain.User;
import www.sahara.com.service.UserService;

@Controller
public class WelcomeController {
	public static final Logger LOGGER = LoggerFactory.getLogger(WelcomeController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome() {
		
		/*
		 * RestTemplate restTemplate = new RestTemplate(); ResponseEntity<List<Product>>
		 * response = restTemplate.exchange( "http://localhost:8081/getProducts",
		 * HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>(){});
		 * List<Product> products = response.getBody();
		 * LOGGER.info("==========================="); if(products != null) {
		 * LOGGER.info(products.toString()); }
		 * LOGGER.info("===========================");
		 */
		return "welcomePage";
	}

	
	
	
	@RequestMapping(value = "/reg", method = RequestMethod.GET)
	public String getRegistration() {
		User user = new User();
		user.setUsername("aman");
		user.setPassword("amanest");
		
		User admin = new User();
		admin.setUsername("henok");
		admin.setPassword("henokest");
		
		userService.saveUser(user, "user");
		userService.saveUser(admin, "admin");
		return "reg";
	}

	@RequestMapping(value = "user/welcome", method = RequestMethod.GET)
	public String getUserWelcome() {
		return "userWelcome";
	}

	@RequestMapping(value = "admin/welcome", method = RequestMethod.GET)
	public String getAdminWelcome() {
		return "adminWelcome";
	}

	@RequestMapping(value = "/ex", method = RequestMethod.GET)
	public String exeception() throws Throwable{
		throw new Throwable("testing controller advice class");
	}
	 
}