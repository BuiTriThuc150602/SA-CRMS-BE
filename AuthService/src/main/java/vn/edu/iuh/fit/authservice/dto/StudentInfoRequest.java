package vn.edu.iuh.fit.authservice.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class StudentInfoRequest {
  private String id;
  private String name;
  private String email;
  private String placeOfBirth;
  private LocalDate dateOfBirth;
  private Boolean gender;
  private String avatar;
  private String phone;
  private String address;
  private int status;
  private String educationalLevel;
  private String faculty;
  private String major;
  private String studentCode;
  private String studentClass;
  private String studentCourse;
  private String studentType;
  private String studentSpecialization;
  private String studentGraduationYear;
  private String studentGraduationType;

}
