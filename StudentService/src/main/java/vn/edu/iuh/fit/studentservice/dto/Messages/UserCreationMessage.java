package vn.edu.iuh.fit.studentservice.dto.Messages;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@JsonSerialize
public class UserCreationMessage {
  String id;
  String name;
  String email;
  Set<String> roles;
}
