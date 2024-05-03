package vn.edu.iuh.fit.studentservice.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor

/*
This class for test api
* */
public class Student {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private  long id;
  private String name;
  private String email;


  public Student(String name, String email) {
    this.name = name;
    this.email = email;
  }


}
