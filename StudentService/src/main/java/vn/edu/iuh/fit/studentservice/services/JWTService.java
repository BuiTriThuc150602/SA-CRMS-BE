package vn.edu.iuh.fit.studentservice.services;

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
import java.text.ParseException;
import java.util.Date;
import java.util.StringJoiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import vn.edu.iuh.fit.studentservice.enums.ErrorCode;
import vn.edu.iuh.fit.studentservice.exceptions.AppException;

@Service
@Slf4j
public class JWTService {

  @Value("${jwt.secret}")
  private String SECRET;


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

}
