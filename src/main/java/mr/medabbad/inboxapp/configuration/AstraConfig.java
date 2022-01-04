package mr.medabbad.inboxapp.configuration;

import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
public class AstraConfig {

    @Bean
    public CqlSessionBuilderCustomizer cqlSessionBuilderCustomizer(DataStaxAstraProperties dataStaxAstraProperties) {
        Path bundlePath = dataStaxAstraProperties.getSecureConnectBundle().toPath();
        return builder -> builder.withCloudSecureConnectBundle(bundlePath);
    }
}
