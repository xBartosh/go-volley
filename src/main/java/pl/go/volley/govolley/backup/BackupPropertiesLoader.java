package pl.go.volley.govolley.backup;

import com.smattme.MysqlExportService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Properties;

@Component
public class BackupPropertiesLoader {

    private String databaseName;
    private String databaseUsername;
    private String databasePassword;
    private String jdbcConnectionString;
    private String emailHost;
    private String emailPort;
    private String emailUsername;
    private String emailPassword;
    private String emailFrom;
    private String emailTo;

    public BackupPropertiesLoader(
            @Value("${backup.db.name}") String databaseName,
            @Value("${spring.datasource.username}") String databaseUsername,
            @Value("${spring.datasource.password}") String databasePassword,
            @Value("${spring.datasource.url}") String jdbcConnectionString,
            @Value("${backup.email.host}") String emailHost,
            @Value("${backup.email.port}") String emailPort,
            @Value("${backup.email.username}") String emailUsername,
            @Value("${backup.email.password}") String emailPassword,
            @Value("${backup.email.from}") String emailFrom,
            @Value("${backup.email.to}") String emailTo) {
        this.databaseName = databaseName;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        this.jdbcConnectionString = jdbcConnectionString;
        this.emailHost = emailHost;
        this.emailPort = emailPort;
        this.emailUsername = emailUsername;
        this.emailPassword = emailPassword;
        this.emailFrom = emailFrom;
        this.emailTo = emailTo;
    }

    public Properties loadProperties() {
        Properties properties = new Properties();
        properties.setProperty(MysqlExportService.DB_NAME, databaseName);
        properties.setProperty(MysqlExportService.DB_USERNAME, databaseUsername);
        properties.setProperty(MysqlExportService.DB_PASSWORD, databasePassword);
        properties.setProperty(MysqlExportService.JDBC_CONNECTION_STRING, jdbcConnectionString);

        properties.setProperty(MysqlExportService.EMAIL_HOST, emailHost);
        properties.setProperty(MysqlExportService.EMAIL_PORT, emailPort);
        properties.setProperty(MysqlExportService.EMAIL_USERNAME, emailUsername);
        properties.setProperty(MysqlExportService.EMAIL_PASSWORD, emailPassword);
        properties.setProperty(MysqlExportService.EMAIL_FROM, emailFrom);
        properties.setProperty(MysqlExportService.EMAIL_TO, emailTo);

        properties.setProperty(MysqlExportService.TEMP_DIR, new File("backup").getPath());

        return properties;
    }
}
