package srl.paros.spike;

import io.undertow.Undertow;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.api.DeploymentInfo;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

import static io.undertow.Handlers.resource;

public interface Main {
  static void main(String[] args) {
    UndertowJaxrsServer server = new UndertowJaxrsServer();

    ResteasyDeployment deployment = new ResteasyDeployment();
    deployment.setApplicationClass(JwtSpike.class.getName());

    DeploymentInfo deploymentInfo = server.undertowDeployment(deployment, "/");
    deploymentInfo.setClassLoader(Main.class.getClassLoader());
    deploymentInfo.setDeploymentName("Undertow + Resteasy example");
    deploymentInfo.setContextPath("/api");

    server.deploy(deploymentInfo);

    server.addResourcePrefixPath("/", resource(new ClassPathResourceManager(Main.class.getClassLoader()))
      .addWelcomeFiles("index.html"));

    server.start(Undertow.builder()
      .addHttpListener(8080, "localhost"));
  }
}
