package vn.edu.iuh.fit.authservice.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.iuh.fit.authservice.ids.UserRoleIDClass;
import vn.edu.iuh.fit.authservice.models.User_Role;

public interface User_RoleRepository extends JpaRepository<User_Role, UserRoleIDClass> {

  @Query("select u from User_Role u where u.user.id = ?1")
  List<User_Role> findByUser_Id(String id);
}