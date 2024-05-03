package vn.edu.iuh.fit.authservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.authservice.models.Role;
import vn.edu.iuh.fit.authservice.models.UserCredential;
import vn.edu.iuh.fit.authservice.models.User_Role;

@Service
@Slf4j
public class JWTService {

  @Value("${jwt.secret}")
  private String SECRET;


  //  private static final String SECRET = "6a6176612d6172636869746563747572652d6d6963726f7365727669636573";
  private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;


  public boolean validateToken(String token) {
    try {
      Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token);

      return true;
    } catch (
        UnsupportedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (JwtException e) {
      log.error("JWT token is expired or invalid: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }

  public String generateToken(UserCredential userCredential, List<User_Role> roles) {
    List<String> roles_name = roles.stream().map(User_Role::getRole).map(Role::getName)
        .toList();
    Map<String, Object> claims = Map.of("userId", userCredential.getId(), "username",
        userCredential.getName(), "roles", roles_name);
    return createToken(claims);
  }

  public Claims getClaims(String token) {
    try {
      return Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();
    } catch (Exception e) {
      log.error("Error when get claims from token: {}", e.getMessage());
      throw new RuntimeException(e);
    }
  }

  private String createToken(Map<String, Object> claims) {
    try {
      return Jwts.builder()
          .claims(claims)
          .issuedAt(new Date(System.currentTimeMillis()))
          .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
          .signWith(getSignKey(), SignatureAlgorithm.HS256)
          .compact();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private SecretKey getSignKey() {
    byte[] keyBytes = SECRET.getBytes();
    return Keys.hmacShaKeyFor(keyBytes);
  }

}
