package vn.edu.iuh.fit.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/eureka"
    );

    public static final List<String> internalApiEndpoints = List.of(
            "/api/v1/auth/validate"
    );

    public static final List<String> adminApiEndpoints = List.of(
            "/api/v1/auth/register",
            "/api/v1/auth/add-role",
            "/api/v1/students/test",
            //API EnrollmentClass
            "/api/v1/class/enrollmentclass",
            "/api/v1/class/enrollmentclass/by-id/{enrollmentClassId}",
            //API Course
            "/api/v1/course",
            "/api/v1/course/{courseId}",
            //API Schedule
            "/api/v1/schedule",
            "/api/v1/schedule/by-id/{scheduleId}"
    );


    public static final List<String> studentApiEndpoints = List.of(
            //API Course
            "/api/v1/course",
            "/api/v1/course/{courseId}",
            //API EnrollmentClass
            "/api/v1/class/enrollmentclass/by-course/{courseId}",
            "/api/v1/class/enrollmentclass/by-id/{enrollmentClassId}",
            //API Schedule
            "/api/v1/schedule/by-enrollment-class/{enrollmentClassId}",
            "/api/v1/schedule/by-id/{scheduleId}",
            //API Enrollment
            "/api/v1/enrollment"
    );


    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

    public Predicate<ServerHttpRequest> isInternal = request -> internalApiEndpoints
            .stream()
            .anyMatch(uri -> request.getURI().getPath().contains(uri));

    public Predicate<ServerHttpRequest> adminEndpoints =
            request -> adminApiEndpoints
                    .stream()
                    .anyMatch(uri -> request.getURI().getPath().contains(uri));
    public Predicate<ServerHttpRequest> studentEndpoints =
            request -> studentApiEndpoints
                    .stream()
                    .anyMatch(uri -> request.getURI().getPath().contains(uri));
}

