package srl.paros.spike;

import com.auth0.jwt.algorithms.Algorithm;

import java.util.Base64;

import static com.auth0.jwt.algorithms.Algorithm.HMAC256;

enum  JwtSign {
  Secret("secret");

  private final String secret;

  JwtSign(final String secret) {
    this.secret = secret;
  }

  public Algorithm asBase64() {
    return HMAC256(Base64.getEncoder().encode(secret.getBytes()));
  }

  public Algorithm asPlain() {
    return HMAC256(secret.getBytes());
  }
}
