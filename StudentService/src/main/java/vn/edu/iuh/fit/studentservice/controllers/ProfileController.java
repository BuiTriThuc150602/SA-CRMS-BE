package vn.edu.iuh.fit.studentservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.studentservice.dto.requests.StudentInfoRequest;
import vn.edu.iuh.fit.studentservice.dto.responses.ApiResponse;
import vn.edu.iuh.fit.studentservice.dto.responses.InformationResponse;
import vn.edu.iuh.fit.studentservice.enums.ErrorCode;
import vn.edu.iuh.fit.studentservice.exceptions.AppException;
import vn.edu.iuh.fit.studentservice.models.Student;
import vn.edu.iuh.fit.studentservice.services.StudentService;

@RestController
@RequestMapping("/students")
@Slf4j
public class ProfileController {

  @Autowired
  private StudentService studentService;

  @GetMapping("/info")
  public ApiResponse<?> getStudentInfo(@RequestHeader("Authorization") String authorization) {
    if (authorization == null) {
      return ApiResponse.<AppException>builder().result(new AppException(ErrorCode.UNAUTHORIZED))
          .build();
    } else if (!authorization.startsWith("Bearer")) {
      return ApiResponse.<AppException>builder().result(new AppException(ErrorCode.UNAUTHORIZED))
          .build();
    }
    String token = authorization.substring(7);
    log.info("Token: {}", token);
    Student student = studentService.getStudentByToken(token);
    return ApiResponse.<InformationResponse>builder().result(
            InformationResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .phone(student.getPhone())
                .address(student.getAddress())
                .placeOfBirth(student.getPlaceOfBirth())
                .dateOfBirth(student.getDateOfBirth().toString())
                .avatar(student.getAvatar())
                .gender(student.getGender())
                .educationalLevel(student.getEducationalLevel())
                .faculty(student.getFaculty())
                .major(student.getMajor())
                .studentCode(student.getStudentCode())
                .studentStatus(student.getStatus())
                .studentStatus(student.getStatus())
                .studentClass(student.getStudentClass())
                .studentCourse(student.getStudentCourse())
                .studentType(student.getStudentType())
                .studentSpecialization(student.getStudentSpecialization())
                .studentGraduationYear(student.getStudentGraduationYear())
                .studentGraduationType(student.getStudentGraduationType())
                .build()).build();


  }

  @PostMapping("/register-student")
  public ApiResponse<?> registerStudent(@RequestBody StudentInfoRequest student) {
    var newStudent = studentService.saveStudent(student);
    return ApiResponse.<Student>builder().result(newStudent).build();
  }

  @PostMapping("/update")
  public ApiResponse<?> updateStudentInfo(@RequestBody StudentInfoRequest studentInfoRequest) {
    if (studentInfoRequest.getId() == null || studentInfoRequest.getId().isEmpty()) {
      return ApiResponse.<AppException>builder().result(new AppException(ErrorCode.UNAUTHORIZED))
          .build();
    }
    var student = studentService.updateStudent(studentInfoRequest);
    return ApiResponse.<Student>builder().result(student).build();
  }

}
