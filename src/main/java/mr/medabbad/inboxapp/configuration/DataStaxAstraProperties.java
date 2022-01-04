package mr.medabbad.inboxapp.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Getter
@Setter
@ConfigurationProperties(prefix = "datastax.astra")
@Configuration
public class DataStaxAstraProperties {
    private File secureConnectBundle;

}
