package cz.tmobile.conf;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.springframework.stereotype.Component;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Component
@OpenAPIDefinition(info = @Info(title = "JIS Glate - repas photo service", version = "1.0"))
@ApplicationPath("/configuration-service/")
public class JaxrsApplication extends Application {
}
