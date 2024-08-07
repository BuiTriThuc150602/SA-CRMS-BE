package vn.edu.iuh.fit.studentservice.enums;

import lombok.Getter;

@Getter
public enum StudentStatus {
  ENROLLED(0),
  GRADUATED(1),
  DROPPED_OUT(2),
  ON_HOLD(3);

  private final int value;

  StudentStatus(int value) {
    this.value = value;
  }


}
