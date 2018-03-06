package srl.paros.spike;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.ofInstant;
import static java.time.ZoneId.systemDefault;
import static javax.ws.rs.Priorities.AUTHENTICATION;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@Provider
@JsonWebToken
@Priority(AUTHENTICATION)
public final class JwtPresence implements ContainerRequestFilter {
  @Override
  public void filter(ContainerRequestContext request) {
    final String authorization = request.getHeaderString(AUTHORIZATION);

    // Extract the token from the HTTP Authorization header
    final String token = authorization.substring("Bearer".length()).trim();

    DecodedJWT jwt = JWT.decode(token);

    final LocalDateTime expiresAt = ofInstant(jwt.getExpiresAt().toInstant(), systemDefault());
    final LocalDateTime refreshExpire = ofInstant(jwt.getClaim("refreshExpire").asDate().toInstant(), systemDefault());

    if (now().isAfter(expiresAt) && now().isBefore(refreshExpire)) {
      System.out.println("Genero nuovo token");
      final String newJwt = new JwtToken("Paros", jwt.getSubject(), now().plusMinutes(2)).asEncoded(JwtSign.Secret);

      request.getHeaders().putSingle(AUTHORIZATION, "Bearer " + newJwt);
    } else {
      System.out.println("Il token è ancora valido");
      System.out.println(jwt.getClaim("refreshToken").asString());
      System.out.println(jwt.getClaim("refreshExpire").asDate());
    }
  }
}
