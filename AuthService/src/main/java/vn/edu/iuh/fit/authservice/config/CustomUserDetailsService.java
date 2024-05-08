package vn.edu.iuh.fit.authservice.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import vn.edu.iuh.fit.authservice.models.Role;
import vn.edu.iuh.fit.authservice.models.UserCredential;
import vn.edu.iuh.fit.authservice.repositories.UserCredentialRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired
  private UserCredentialRepository repository;

  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    UserCredential userCredential = repository.findById(userId)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    var roles = userCredential.getRoles();
    Collection<? extends GrantedAuthority> authorities = getAuthorities(roles);
    return new CustomUserDetails(userCredential, authorities);
  }
  private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {;
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    for (var role : roles) {
      authorities.add(new SimpleGrantedAuthority(role.getName()));
    }
    return authorities;
  }
}
