package pl.go.volley.govolley.backup;

import com.smattme.MysqlExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Service
public class BackupService {
    private final MysqlExportService mysqlExportService;
    private static final Logger LOGGER = LoggerFactory.getLogger(BackupService.class);
    private static final long SCHEDULE_INTERVAL_IN_MILLIS = 60 * 1000; // 12h = 12 * 60min * 60s * 1000ms

    public BackupService(MysqlExportService mysqlExportService) {
        this.mysqlExportService = mysqlExportService;
    }

    @Scheduled(fixedRate = SCHEDULE_INTERVAL_IN_MILLIS)
    public void backUp() {
        LOGGER.info("Going to start backup at {}", LocalDateTime.now());
        try {
            long startTime = System.currentTimeMillis();
            mysqlExportService.export();
            long delta = System.currentTimeMillis() - startTime;
            LOGGER.info("Backup successful in {}ms", delta);
        } catch (IOException | SQLException | ClassNotFoundException e) {
            LOGGER.error("Failed doing backup, error message={}", e.getMessage());
        }
    }
}
