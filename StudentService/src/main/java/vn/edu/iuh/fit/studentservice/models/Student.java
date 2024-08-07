package vn.edu.iuh.fit.studentservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import vn.edu.iuh.fit.studentservice.enums.StudentStatus;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "students")
public class Student {
  @Id
  private  String id;
  @Column(name = "user_name")
  private String name;
  @Column(name = "user_email")
  private String email;
  @NonNull
  @Column(name = "place_of_birth")
  private String placeOfBirth;
  @NonNull
  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;
  @NonNull
  @Column(name = "gender")
  private Boolean gender;
  @NonNull
  @Column(name = "avatar")
  private String avatar;
  @NonNull
  private String phone;
  @NonNull
  private String address;
  @NonNull
  @Column(name = "status")
  private StudentStatus status;

  @NonNull
  @Column(name = "educational_level")
  private String educationalLevel;

  @NonNull
  private String faculty;

  @NonNull
  private String major;

  @NonNull
  @Column(name = "student_code")
  private String studentCode;

  @NonNull
  @Column(name = "student_class")
  private String studentClass;

  @NonNull
  @Column(name = "student_course")
  private String studentCourse;

  @NonNull
  @Column(name = "student_type")
  private String studentType;

  @NonNull
  @Column(name = "student_specialization")
  private String studentSpecialization;

  @NonNull
  @Column(name = "student_graduation_year")
  private String studentGraduationYear;

  @NonNull
  @Column(name = "student_graduation_type")
  private String studentGraduationType;

}

