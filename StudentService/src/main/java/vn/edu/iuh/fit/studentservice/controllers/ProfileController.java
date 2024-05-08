package vn.edu.iuh.fit.studentservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.studentservice.dto.requests.StudentInfoRequest;
import vn.edu.iuh.fit.studentservice.models.Student;
import vn.edu.iuh.fit.studentservice.services.StudentService;

@RestController
@RequestMapping("/students")
@Slf4j
public class ProfileController {

  @Autowired
  private StudentService studentService;

  @GetMapping("/info")
  public ResponseEntity<?> getStudentInfo(@RequestHeader("Authorization") String authorization) {
    try {
      if (authorization == null) {
        return ResponseEntity.badRequest().build();
      } else if (!authorization.startsWith("Bearer")) {
        return ResponseEntity.badRequest().build();
      }
      String token = authorization.substring(7);
      log.info("Token: {}", token);
      Student student = studentService.getStudentByToken(token);
      if (student == null) {
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(student);

    } catch (Exception e) {
      log.error("Error: {}", e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/update")
  public ResponseEntity<?> updateStudentInfo(@RequestBody StudentInfoRequest studentInfoRequest) {
    try {
      if (studentInfoRequest.getId() == null || studentInfoRequest.getId().isEmpty()) {
        return ResponseEntity.status(400).body("Student ID is required");
      }
      var student = studentService.updateStudent(studentInfoRequest);
      return ResponseEntity.ok(student);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

}
