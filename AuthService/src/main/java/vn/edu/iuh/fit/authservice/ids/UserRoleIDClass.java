package vn.edu.iuh.fit.authservice.ids;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserRoleIDClass implements Serializable {
  private Long role;
  private String user;

}
