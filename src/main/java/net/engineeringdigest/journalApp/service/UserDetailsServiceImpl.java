package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.UserObject;
import net.engineeringdigest.journalApp.repository.userEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private userEntryRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserObject byUserName = userRepo.findByUserName(username);
        if(byUserName != null){
            return User.builder()
                    .username(byUserName.getUserName())
                    .password(byUserName.getPassword())
                    .roles(byUserName.getRoles().toArray(new String[0]))
                    .build();
        }

        throw new UsernameNotFoundException("User Not Found for username: " + username);
    }
}
