package tech.shmy.ossserver;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tech.shmy.ossserver.service.impl.TemporaryFileService;

import java.io.File;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Objects;

@Component
@Slf4j
public class ScheduledTaskBean {
    @Autowired
    private TemporaryFileService temporaryFileService;

    @Scheduled(cron = "0 15 0 * * *")
    public void run() {
        val now = Calendar.getInstance();
        now.add(Calendar.WEEK_OF_MONTH, -2);
        val yearStr = String.valueOf(now.get(Calendar.YEAR));
        val weekStr = String.valueOf(now.get(Calendar.WEEK_OF_MONTH));
        val month = now.get(Calendar.MONTH) + 1;
        val monthStr = month < 10 ? "0" + month : String.valueOf(month);
        File file = Paths.get(temporaryFileService.getDir(), yearStr, monthStr, weekStr).toFile();
        var isSuccess = false;
        val isExists = file.exists();
        if (isExists) {
            isSuccess = deleteDir(file);
        }

        log.info("Delete {}, isExists: {} from: {}", isSuccess, isExists, file.toString());
    }

    private boolean deleteDir(File file) {
        if (file.isDirectory()) {
            for (File f : Objects.requireNonNull(file.listFiles()))
                deleteDir(f);
        }
        return file.delete();
    }
}
