package srl.paros.spike;

import com.auth0.jwt.JWT;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static java.time.ZoneId.systemDefault;

final class EncodedJwtToken {
  private final JwtSign sign;

  EncodedJwtToken(final JwtSign sign) {
    this.sign = sign;
  }

  public String issue(final String issuer, final String principal, final LocalDateTime refreshExp) {
    return JWT.create()
      .withIssuer(issuer)
      .withIssuedAt(Date.from(now().atZone(systemDefault()).toInstant()))
      .withExpiresAt(Date.from(now().plusMinutes(1).atZone(systemDefault()).toInstant()))
      .withSubject(principal)
      .withClaim("refreshToken", Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes()))
      .withClaim("refreshExpire", Date.from(refreshExp.atZone(systemDefault()).toInstant()))
      .sign(sign.asBase64());
  }
}
