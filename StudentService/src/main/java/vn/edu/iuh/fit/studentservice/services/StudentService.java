package vn.edu.iuh.fit.studentservice.services;

import java.util.Arrays;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponseException;
import vn.edu.iuh.fit.studentservice.dto.Messages.UserCreationMessage;
import vn.edu.iuh.fit.studentservice.dto.requests.StudentInfoRequest;
import vn.edu.iuh.fit.studentservice.enums.ErrorCode;
import vn.edu.iuh.fit.studentservice.enums.StudentStatus;
import vn.edu.iuh.fit.studentservice.exceptions.AppException;
import vn.edu.iuh.fit.studentservice.models.Student;
import vn.edu.iuh.fit.studentservice.repositories.StudentRepository;

@Service
@Slf4j
public class StudentService {

  @Autowired
  private StudentRepository studentRepository;
  @Autowired
  private JWTService jwtService;
  @Autowired
  private StreamBridge streamBridge;

  public Student saveStudent(StudentInfoRequest student) {
    if (student == null) {
      throw new AppException(ErrorCode.INVALID_REQUEST);
    }

    Student newStudent = new Student();

    if (student.getId() != null) {
      newStudent.setId(student.getId());
    }
    if (student.getName() != null) {
      newStudent.setName(student.getName());
    }
    if (student.getEmail() != null) {
      newStudent.setEmail(student.getEmail());
    }
    if (student.getPlaceOfBirth() != null) {
      newStudent.setPlaceOfBirth(student.getPlaceOfBirth());
    }
    if (student.getDateOfBirth() != null) {
      newStudent.setDateOfBirth(student.getDateOfBirth());
    }
    if (student.getGender() != null) {
      newStudent.setGender(student.getGender());
    }
    if (student.getAvatar() != null) {
      newStudent.setAvatar(student.getAvatar());
    }
    if (student.getPhone() != null) {
      newStudent.setPhone(student.getPhone());
    }
    if (student.getAddress() != null) {
      newStudent.setAddress(student.getAddress());
    }
    var studentStatus = student.getStatus();
    var invalidStatus = Arrays.stream(StudentStatus.values())
        .noneMatch(status -> status.getValue() == studentStatus);
    if (invalidStatus) {
      newStudent.setStatus(StudentStatus.ENROLLED);
    } else {
      newStudent.setStatus(StudentStatus.values()[studentStatus]);
    }
    if (student.getEducationalLevel() != null) {
      newStudent.setEducationalLevel(student.getEducationalLevel());
    }
    if (student.getFaculty() != null) {
      newStudent.setFaculty(student.getFaculty());
    }
    if (student.getMajor() != null) {
      newStudent.setMajor(student.getMajor());
    }
    if (student.getStudentCode() != null) {
      newStudent.setStudentCode(student.getStudentCode());
    }
    if (student.getStudentClass() != null) {
      newStudent.setStudentClass(student.getStudentClass());
    }
    if (student.getStudentCourse() != null) {
      newStudent.setStudentCourse(student.getStudentCourse());
    }
    if (student.getStudentType() != null) {
      newStudent.setStudentType(student.getStudentType());
    }
    if (student.getStudentSpecialization() != null) {
      newStudent.setStudentSpecialization(student.getStudentSpecialization());
    }
    if (student.getStudentGraduationYear() != null) {
      newStudent.setStudentGraduationYear(student.getStudentGraduationYear());
    }
    if (student.getStudentGraduationType() != null) {
      newStudent.setStudentGraduationType(student.getStudentGraduationType());
    }

    var result = studentRepository.save(newStudent);
    var newUser = new UserCreationMessage(
        result.getId(),
        result.getName(),
        result.getEmail(),
        result.getId(),
        Set.of("student")
    );
    streamBridge.send("output-out-0", newUser);

    return newStudent;
  }

  public Student getStudentByToken(String token) {
    try {
      var signedJWT = jwtService.validateToken(token);
      var claims = signedJWT.getJWTClaimsSet();
      var id = claims.getSubject();
      return studentRepository.findById(id).
          orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    } catch (Exception e) {
      log.error("Error: {}", e.getMessage());
      throw new AppException(ErrorCode.USER_NOT_EXISTED);
    }

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


