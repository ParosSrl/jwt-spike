package srl.paros.spike;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static java.time.LocalDateTime.now;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static srl.paros.spike.JwtSign.Secret;

@Path("jwt")
public final class JwtResource {
  @GET
  @JsonWebToken
  @Produces(TEXT_PLAIN)
  public Response jwt() {
    return Response.ok(
      new EncodedJwtToken(Secret).issue("Paros", "ale", now().plusMinutes(3))
    ).build();
  }

  @POST
  @Path("/login")
  @Produces(TEXT_PLAIN)
  @Consumes(APPLICATION_FORM_URLENCODED)
  public Response login(@FormParam("login") String login, @FormParam("password") String password) {
    final String token = new EncodedJwtToken(Secret).issue("Paros", login, now().plusMinutes(4));
    return Response.ok(token)
      .header(AUTHORIZATION, "Bearer " + token)
      .build();
  }
}