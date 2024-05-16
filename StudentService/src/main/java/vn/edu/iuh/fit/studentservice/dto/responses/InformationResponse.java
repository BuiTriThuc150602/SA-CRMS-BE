package vn.edu.iuh.fit.studentservice.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import vn.edu.iuh.fit.studentservice.enums.StudentStatus;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InformationResponse {
  String id;
  String name;
  String email;
  String phone;
  String address;
  String placeOfBirth;
  String dateOfBirth;
  String avatar;
  Boolean gender;
  String educationalLevel;
  String faculty;
  String major;
  String studentCode;
  String studentClass;
  String studentCourse;
  String studentType;
  String studentSpecialization;
  String studentGraduationYear;
  String studentGraduationType;
  StudentStatus studentStatus;

}
