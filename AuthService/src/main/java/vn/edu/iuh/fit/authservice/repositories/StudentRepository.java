package vn.edu.iuh.fit.authservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.authservice.models.Student;

public interface StudentRepository extends JpaRepository<Student, String> {

}