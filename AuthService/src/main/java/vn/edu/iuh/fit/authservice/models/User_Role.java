package vn.edu.iuh.fit.authservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import vn.edu.iuh.fit.authservice.ids.UserRoleIDClass;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "user_roles")
@Entity
@IdClass(UserRoleIDClass.class)
public class User_Role {

  @Id
  @ManyToOne
  @JoinColumn(name = "role_id")
  private Role role;
  @Id
  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserCredential user;

}
