package vn.edu.iuh.fit.studentservice.controllers;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.studentservice.models.Student;

/*
 * This is a test controller to test the API
 * */
@RestController
@RequestMapping("/students")
public class TestController {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

  @RequestMapping("/test")
  public ResponseEntity<List<Student>> hello() {
    LOGGER.info("Test API");
    List<Student> students = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Student student = new Student(i, "Nguyen Van " + i, "email" + i + "@gmail.com");
      students.add(student);
    }
    return ResponseEntity.ok(students);
  }

}
