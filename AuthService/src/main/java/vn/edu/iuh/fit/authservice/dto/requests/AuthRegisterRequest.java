package vn.edu.iuh.fit.authservice.dto.requests;

import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRegisterRequest {

  private String id;
  private String name;
  private String email;
  private String password;
  private Set<String> roles;

}
