package jwtTokenDemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jwtTokenDemo.Entity.Users;
import jwtTokenDemo.Repository.UsersRepository;
@Service
public class CustomUserDetails implements UserDetailsService
{
@Autowired
private UsersRepository usersRepository;

@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	Users user=usersRepository.findByUserName(username).get();
	return user;
}

}
