package com.microservice.courseservice.service;

import com.microservice.courseservice.dtos.CourseRequest;
import com.microservice.courseservice.dtos.CourseResponse;
import com.microservice.courseservice.dtos.EnrollmentClassResponse;
import com.microservice.courseservice.dtos.EnrollmentResponse;
import com.microservice.courseservice.models.Course;
import com.microservice.courseservice.reponsitories.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseService {

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private WebClient.Builder loadBalancedWebClientBuilder;

  public void addCourse(CourseRequest courseRequest) {
    Course course = new Course();
    course.setId(courseRequest.getId());
    course.setName(courseRequest.getName());
    course.setCredit(courseRequest.getCredit());
    course.setSemester(courseRequest.getSemester());
    course.setFaculty(courseRequest.getFaculty());
    course.setPrerequisiteIds(courseRequest.getPrerequisiteIds());

    courseRepository.save(course);
    log.info("Course with id " + course.getId() + " is saved");
  }

  public List<CourseResponse> getAllCourse() {
    List<Course> courses = courseRepository.findAll();

    List<CourseResponse> courseResponses = courses.stream().map(course -> {
      CourseResponse courseResponse = new CourseResponse();
      courseResponse.setId(course.getId());
      courseResponse.setName(course.getName());
      courseResponse.setCredit(course.getCredit());
      courseResponse.setSemester(course.getSemester());
      courseResponse.setFaculty(course.getFaculty());
      List<String> prerequisiteCourseIds = course.getPrerequisiteIds();
      courseResponse.setPrerequisiteCourseIds(prerequisiteCourseIds);
      return courseResponse;
    }).collect(Collectors.toList());
    return courseResponses;
  }

  public CourseResponse getCoursesById(String courseId) {
    Course course = courseRepository.findCourseById(courseId).get();
    CourseResponse courseResponse = new CourseResponse();
    courseResponse.setId(course.getId());
    courseResponse.setName(course.getName());
    courseResponse.setCredit(course.getCredit());
    courseResponse.setSemester(course.getSemester());
    courseResponse.setFaculty(course.getFaculty());

    List<String> prerequisiteCourseIds = course.getPrerequisiteIds();
    courseResponse.setPrerequisiteCourseIds(prerequisiteCourseIds);
    return courseResponse;
  }


  public List<CourseResponse> getCoursesBySemesterAndFaculty(String semester, String faculty,
      String StudentId) {
    List<Course> courses = courseRepository.findCoursesBySemesterAndFaculty(semester, faculty);
    List<EnrollmentResponse> enrollmentResponses = getEnrollmentsByStudentIdAndSemester(StudentId,
        semester);
    return courses.stream()
        .filter(course -> enrollmentResponses.stream()
            .noneMatch(
                enrollmentResponse -> enrollmentResponse.getCourseId().equals(course.getId())))
        .map(course -> {
          CourseResponse courseResponse = new CourseResponse();
          courseResponse.setId(course.getId());
          courseResponse.setName(course.getName());
          courseResponse.setCredit(course.getCredit());
          courseResponse.setSemester(course.getSemester());
          courseResponse.setFaculty(course.getFaculty());
          courseResponse.setPrerequisiteCourseIds(course.getPrerequisiteIds());
          return courseResponse;
        }).collect(Collectors.toList());
  }


  public Boolean checkDuplicatedCourseInEnrollment(String courseId, String studentId,
      String semester) {
    List<EnrollmentResponse> enrollmentResponseList = getEnrollmentsByStudentIdAndSemester(
        studentId, semester);
    for (EnrollmentResponse enrollmentResponse : enrollmentResponseList) {
      log.info("Course Id: " + enrollmentResponse.getCourseId());
      if (enrollmentResponse.getCourseId().equals(courseId)) {
        log.info("Course Id: " + enrollmentResponse.getCourseId());
        return true;
      }
    }
    return false;
  }


  public List<EnrollmentResponse> getEnrollmentsByStudentIdAndSemester(String studentId,
      String semester) {
    String baseUrl = "http://ENROLLMENTSERVICE/enrollment/search";
    String uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
        .queryParam("studentId", studentId)
        .queryParam("semester", semester)
        .toUriString();
    EnrollmentResponse[] enrollmentResponses = loadBalancedWebClientBuilder.build()
        .get()
        .uri(uri)
        .retrieve()
        .bodyToMono(EnrollmentResponse[].class)
        .block();

    log.info("enrollmentResponses:" + Arrays.toString(enrollmentResponses));  // Log array

    if (enrollmentResponses != null) {
      List<EnrollmentResponse> enrollmentResponseList = Arrays.stream(enrollmentResponses)
          .collect(Collectors.toList());
      log.info(
          "enrollmentResponseList:" + enrollmentResponseList);  // Log danh sách chuyển đổi từ array
      return enrollmentResponseList;
    } else {
      return List.of();  // Trả về danh sách rỗng nếu phản hồi là null
    }
  }

}
