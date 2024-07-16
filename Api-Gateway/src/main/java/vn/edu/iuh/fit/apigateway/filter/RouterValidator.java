package vn.edu.iuh.fit.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;
import java.util.function.Predicate;

@Component
public class RouterValidator {

  public static final List<Pattern> openApiEndpoints = List.of(
      Pattern.compile("/api/v1/auth/login"),
      Pattern.compile("/api/v1/auth/change-password"),
      Pattern.compile("/api/v1/students/register-student"),
      Pattern.compile("/eureka")
  );

  public static final List<Pattern> internalApiEndpoints = List.of(
          Pattern.compile("/api/v1/auth/.*")
//          Pattern.compile("/api/v1/students/.*")
  );

  public static final List<Pattern> adminApiEndpoints = List.of(
          Pattern.compile("/api/v1/auth/register"),
          Pattern.compile("/api/v1/auth/add-role"),
          Pattern.compile("/api/v1/students/test"),
          // API EnrollmentClass
          Pattern.compile("/api/v1/class/enrollmentclass"),
          // API Course
          Pattern.compile("/api/v1/course"),
          // API Schedule
          Pattern.compile("/api/v1/schedule")
  );

  public static final List<Pattern> studentApiEndpoints = List.of(
          // Student endpoints
          Pattern.compile("/api/v1/students/info"),
          Pattern.compile("/api/v1/students/reset-password"),
          // API Course
          Pattern.compile("/api/v1/course"),
          Pattern.compile("/api/v1/course/by-courseId"),
          Pattern.compile("/api/v1/course/search"),
          Pattern.compile("/api/v1/course/check-duplicated-course-enrollment"),
          // API EnrollmentClass
          Pattern.compile("/api/v1/class/enrollmentclass/by-course"),
          Pattern.compile("/api/v1/class/enrollmentclass/by-id"),
          Pattern.compile("/api/v1/class/enrollmentclass/update-current-student"),
          // API Schedule
          Pattern.compile("/api/v1/schedule/by-enrollment-class"),
          Pattern.compile("/api/v1/schedule/by-id"),
          Pattern.compile("/api/v1/schedule/check-duplicated-schedule"),
          Pattern.compile("/api/v1/schedule/by-enrollment"),
          // API Enrollment
          Pattern.compile("/api/v1/enrollment"),
          Pattern.compile("/api/v1/enrollment/search")
  );

  private Predicate<ServerHttpRequest> createPredicate(List<Pattern> patterns) {
    return request -> patterns.stream()
            .anyMatch(pattern -> pattern.matcher(request.getURI().getPath()).matches());
  }

  public final Predicate<ServerHttpRequest> isSecured = createPredicate(openApiEndpoints).negate();
  public final Predicate<ServerHttpRequest> isInternal = createPredicate(internalApiEndpoints);
  public final Predicate<ServerHttpRequest> adminEndpoints = createPredicate(adminApiEndpoints);
  public final Predicate<ServerHttpRequest> studentEndpoints = createPredicate(studentApiEndpoints);
}
