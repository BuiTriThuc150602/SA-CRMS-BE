package vn.edu.iuh.fit.studentservice.services;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponseException;
import vn.edu.iuh.fit.studentservice.dto.requests.StudentInfoRequest;
import vn.edu.iuh.fit.studentservice.services.enums.StudentStatus;
import vn.edu.iuh.fit.studentservice.models.Student;
import vn.edu.iuh.fit.studentservice.repositories.StudentRepository;

@Service
@Slf4j
public class StudentService {

  @Autowired
  private StudentRepository studentRepository;

  public Student getStudentByToken(String id) {
    return studentRepository.findById(id).orElse(null);
  }

  @Transactional
  public Student updateStudent(StudentInfoRequest studentInfoRequest) {
    var student = studentRepository.findById(studentInfoRequest.getId()).orElse(null);
    if (student == null) {
      throw new ErrorResponseException(HttpStatusCode.valueOf(404));
    }
    if (studentInfoRequest.getName() != null && !studentInfoRequest.getName().isEmpty()) {
      student.setName(studentInfoRequest.getName());
    }
    if (studentInfoRequest.getEmail() != null && !studentInfoRequest.getEmail().isEmpty()) {
      student.setEmail(studentInfoRequest.getEmail());
    }
    if (studentInfoRequest.getPlaceOfBirth() != null && !studentInfoRequest.getPlaceOfBirth()
        .isEmpty()) {
      student.setPlaceOfBirth(studentInfoRequest.getPlaceOfBirth());
    }
    if (studentInfoRequest.getDateOfBirth() != null) {
      student.setDateOfBirth(studentInfoRequest.getDateOfBirth());
    }
    if (studentInfoRequest.getGender() != null) {
      student.setGender(studentInfoRequest.getGender());
    }
    if (studentInfoRequest.getAvatar() != null && !studentInfoRequest.getAvatar().isEmpty()) {
      student.setAvatar(studentInfoRequest.getAvatar());
    }
    if (studentInfoRequest.getPhone() != null && !studentInfoRequest.getPhone().isEmpty()) {
      student.setPhone(studentInfoRequest.getPhone());
    }
    if (studentInfoRequest.getAddress() != null && !studentInfoRequest.getAddress().isEmpty()) {
      student.setAddress(studentInfoRequest.getAddress());
    }
    if (Arrays.stream(StudentStatus.values())
        .anyMatch(status -> status.getValue() == studentInfoRequest.getStatus())) {
      student.setStatus(StudentStatus.values()[studentInfoRequest.getStatus()]);
    }
    if (studentInfoRequest.getEducationalLevel() != null
        && !studentInfoRequest.getEducationalLevel().isEmpty()) {
      student.setEducationalLevel(studentInfoRequest.getEducationalLevel());
    }
    if (studentInfoRequest.getFaculty() != null && !studentInfoRequest.getFaculty().isEmpty()) {
      student.setFaculty(studentInfoRequest.getFaculty());
    }
    if (studentInfoRequest.getMajor() != null && !studentInfoRequest.getMajor().isEmpty()) {
      student.setMajor(studentInfoRequest.getMajor());
    }
    if (studentInfoRequest.getStudentCode() != null && !studentInfoRequest.getStudentCode()
        .isEmpty()) {
      student.setStudentCode(studentInfoRequest.getStudentCode());
    }
    if (studentInfoRequest.getStudentClass() != null && !studentInfoRequest.getStudentClass()
        .isEmpty()) {
      student.setStudentClass(studentInfoRequest.getStudentClass());
    }
    if (studentInfoRequest.getStudentCourse() != null && !studentInfoRequest.getStudentCourse()
        .isEmpty()) {
      student.setStudentCourse(studentInfoRequest.getStudentCourse());
    }
    if (studentInfoRequest.getStudentType() != null && !studentInfoRequest.getStudentType()
        .isEmpty()) {
      student.setStudentType(studentInfoRequest.getStudentType());
    }
    if (studentInfoRequest.getStudentSpecialization() != null
        && !studentInfoRequest.getStudentSpecialization().isEmpty()) {
      student.setStudentSpecialization(studentInfoRequest.getStudentSpecialization());
    }
    if (studentInfoRequest.getStudentGraduationYear() != null
        && !studentInfoRequest.getStudentGraduationYear().isEmpty()) {
      student.setStudentGraduationYear(studentInfoRequest.getStudentGraduationYear());
    }
    if (studentInfoRequest.getStudentGraduationType() != null
        && !studentInfoRequest.getStudentGraduationType().isEmpty()) {
      student.setStudentGraduationType(studentInfoRequest.getStudentGraduationType());
    }
    return studentRepository.save(student);
  }
}


