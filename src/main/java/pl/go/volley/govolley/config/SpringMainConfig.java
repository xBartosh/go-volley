package pl.go.volley.govolley.config;

import com.smattme.MysqlExportService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.go.volley.govolley.backup.BackupPropertiesLoader;
import pl.go.volley.govolley.protocol.file.FileToDeleteExecutor;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Configuration
public class SpringMainConfig {

    @Bean
    public FileToDeleteExecutor fileToDeleteExecutor(){
        return new FileToDeleteExecutor(15L, TimeUnit.SECONDS);
    }

    @Bean
    public MysqlExportService mysqlExportService(BackupPropertiesLoader backupPropertiesLoader) {
        Properties backupProperties = backupPropertiesLoader.loadProperties();
        return new MysqlExportService(backupProperties);
    }

}
