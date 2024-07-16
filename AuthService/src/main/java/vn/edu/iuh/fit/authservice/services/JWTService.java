package vn.edu.iuh.fit.authservice.services;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import vn.edu.iuh.fit.authservice.enums.ErrorCode;
import vn.edu.iuh.fit.authservice.exceptions.AppException;
import vn.edu.iuh.fit.authservice.models.UserCredential;

@Service
@Slf4j
public class JWTService {

  @Value("${jwt.secret}")
  private String SECRET;
  private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;


  public SignedJWT validateToken(String token) throws JOSEException, ParseException {
    JWSVerifier verifier = new MACVerifier(SECRET.getBytes());
    SignedJWT signedJWT = SignedJWT.parse(token);
    Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
    if (expirationTime.before(new Date())) {
      throw new AppException(ErrorCode.TOKEN_EXPIRED);
    }
    if (!signedJWT.verify(verifier)) {
      throw new AppException(ErrorCode.UNAUTHENTICATED);
    }
    return signedJWT;

  }

  public String generateToken(UserCredential userCredential) {
    JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
        .subject(userCredential.getId())
        .claim(("roles"), buildScope(userCredential))
        .issuer("auth-service")
        .issueTime(new Date())
        .expirationTime(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .build();

    Payload payload = new Payload(claimsSet.toJSONObject());
    JWSObject jwsObject = new JWSObject(header, payload);

    try {
      jwsObject.sign(new MACSigner(SECRET.getBytes()));
      return jwsObject.serialize();
    } catch (JOSEException e) {
      log.error("Cannot create token", e);
      throw new RuntimeException(e);
    }
  }

  private String buildScope(UserCredential user) {
    StringJoiner joiner = new StringJoiner(",");
    if (!CollectionUtils.isEmpty(user.getRoles())) {
      user.getRoles().forEach(role -> joiner.add(role.getName()));
    }
    return joiner.toString();
  }

}
