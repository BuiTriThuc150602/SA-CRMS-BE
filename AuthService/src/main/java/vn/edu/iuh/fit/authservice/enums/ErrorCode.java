package vn.edu.iuh.fit.authservice.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(2999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(2001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(2002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(2003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(2004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(2005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(2006, "Unauthenticated, Invalid id or password", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(2007, "You do not have permission", HttpStatus.FORBIDDEN),
    TOKEN_EXPIRED(2008, "Token expired", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN(2009, "Invalid token", HttpStatus.UNAUTHORIZED),
    INVALID_DOB(2010, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST(2011, "Request not match any thing, please check it", HttpStatus.NOT_FOUND),
    INVALID_OLD_PASSWORD(2012, "invalid old password", HttpStatus.BAD_REQUEST),
    ;
    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
    ErrorCode(int code, String message, HttpStatusCode statusCode) {
      this.code = code;
      this.message = message;
      this.statusCode = statusCode;
    }
}
