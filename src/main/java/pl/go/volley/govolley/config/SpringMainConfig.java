package pl.go.volley.govolley.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.go.volley.govolley.protocol.file.FileToDeleteExecutor;

import java.util.concurrent.TimeUnit;

@Configuration
public class SpringMainConfig {

    @Bean
    public FileToDeleteExecutor fileToDeleteExecutor(){
        return new FileToDeleteExecutor(15L, TimeUnit.SECONDS);
    }
}
