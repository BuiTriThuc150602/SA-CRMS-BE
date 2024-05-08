package vn.edu.iuh.fit.authservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class UserCredential {
  @Id
  private  String id;
  @Column(name = "user_name")
  private String name;
  @Column(name = "user_email")
  private String email;
  @Column(name = "user_password")
  private String password;
  @Column(name = "user_active")
  private boolean active;
  @ManyToMany
  private Set<Role> roles;



}
