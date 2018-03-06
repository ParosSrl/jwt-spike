package srl.paros.spike;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public final class JwtSpike extends Application {
  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> classes = new HashSet<>();
    classes.add(JwtResource.class);
    classes.add(JwtPresence.class);
    return classes;
  }
}