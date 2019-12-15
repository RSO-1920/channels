package si.fri.rso.api.v1;

import com.kumuluz.ee.discovery.annotations.RegisterService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@RegisterService(value = "rso1920-channels")
@ApplicationPath("/v1")
@OpenAPIDefinition(info = @Info(title = "Channel REST API", version = "v1", contact = @Contact(), license = @License(),
        description = "Service for managing channels and users on channels"), servers = @Server(url ="http://localhost:8080/v1"))
public class ChannelsApplication extends Application {
}
