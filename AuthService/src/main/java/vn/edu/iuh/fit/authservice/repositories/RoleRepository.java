package vn.edu.iuh.fit.authservice.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.iuh.fit.authservice.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

  @Query("select r from Role r where upper(r.name) = upper(?1)")
  Optional<Role> findByNameIgnoreCase(String name);

  Set<Role> findByNameIn(Set<String> roles);
}