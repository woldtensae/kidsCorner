package www.sahara.com.service;

import java.util.Arrays;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import www.sahara.com.app.MyUserPrincipal;
import www.sahara.com.domain.Role;
import www.sahara.com.domain.User;
import www.sahara.com.repository.RoleRepository;
import www.sahara.com.repository.UserRepository;

@Service
@Transactional
public class UserService implements UserDetailsService{
	
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder passwordEncoder;
	
	
	
	@Autowired
	public UserService(UserRepository userRepository, 
			BCryptPasswordEncoder passwordencoder, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordencoder;
		this.roleRepository = roleRepository;
	}
	
	public void saveUser(User user, String roleType) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		 Role role = roleRepository.findByRoleType(roleType); 
		 user.setRoles(new HashSet<Role>(Arrays.asList(role)));
		 
		userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new MyUserPrincipal(user);
	}
	
}
