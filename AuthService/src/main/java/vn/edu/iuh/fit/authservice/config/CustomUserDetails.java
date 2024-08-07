package vn.edu.iuh.fit.authservice.config;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.edu.iuh.fit.authservice.models.UserCredential;

public class CustomUserDetails implements UserDetails {
  private String  id;
  private String  password;
  private Collection<? extends GrantedAuthority> authorities;

  public CustomUserDetails(UserCredential userCredential, Collection<? extends GrantedAuthority> authorities){
    this.id = userCredential.getId();
    this.password = userCredential.getPassword();
    this.authorities = authorities;
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;

  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return id;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
