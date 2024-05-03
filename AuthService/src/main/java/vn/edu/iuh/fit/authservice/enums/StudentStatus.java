package vn.edu.iuh.fit.authservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum StudentStatus {
  ENROLLED(0),
  GRADUATED(1),
  DROPPED_OUT(2),
  ON_HOLD(3);

  private final int value;

  private StudentStatus(int value) {
    this.value = value;
  }


}
