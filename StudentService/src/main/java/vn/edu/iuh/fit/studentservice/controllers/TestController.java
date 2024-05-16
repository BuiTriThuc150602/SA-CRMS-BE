package vn.edu.iuh.fit.studentservice.controllers;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.studentservice.dto.responses.ApiResponse;
import vn.edu.iuh.fit.studentservice.models.Student;
import vn.edu.iuh.fit.studentservice.repositories.StudentRepository;

/*
 * This is a test controller to test the API
 * */
@RestController
@RequestMapping("/students")
public class TestController {

  @Autowired
  private StudentRepository studentRepository;

  private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

  @RequestMapping("/test")
  public ApiResponse<?> testApi() {
    LOGGER.info("Test API");
    var students = studentRepository.findAll();
    return ApiResponse.<List<Student>>builder().result(new ArrayList<>(students)).build();
  }

}
