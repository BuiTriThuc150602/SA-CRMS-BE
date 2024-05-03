package vn.edu.iuh.fit.authservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.authservice.models.UserCredential;

public interface UserCredentialRepository extends JpaRepository<UserCredential, String> {

}