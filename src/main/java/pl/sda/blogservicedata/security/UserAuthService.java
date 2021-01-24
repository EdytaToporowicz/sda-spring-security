package pl.sda.blogservicedata.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import pl.sda.blogservicedata.exception.UserNotFoundException;
import pl.sda.blogservicedata.model.User;
import pl.sda.blogservicedata.repository.UserRepository;

@Controller
public class UserAuthService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(username).orElseThrow(() -> new UserNotFoundException("User not " +
                "found: " + username));
        return new UserAdapter(user);
    }
}
