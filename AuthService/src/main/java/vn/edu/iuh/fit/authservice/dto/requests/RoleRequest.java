package vn.edu.iuh.fit.authservice.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class RoleRequest {
  private String roleName;
  private String description;

}
