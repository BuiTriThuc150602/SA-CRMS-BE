package vn.edu.iuh.fit.studentservice.services.enums;

import lombok.Getter;

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
