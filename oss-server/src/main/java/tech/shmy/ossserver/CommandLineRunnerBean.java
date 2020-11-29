package tech.shmy.ossserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tech.shmy.ossserver.service.impl.PersistentFileService;
import tech.shmy.ossserver.service.impl.TemporaryFileService;

@Slf4j
@Component
public class CommandLineRunnerBean implements CommandLineRunner {
    @Autowired
    private PersistentFileService persistentFileService;
    @Autowired
    private TemporaryFileService temporaryFileService;

    @Override
    public void run(String... args) throws Exception {
        if (!persistentFileService.setDir()) {
            throw new Exception("Can not create persistent dir");
        }
        if (!temporaryFileService.setDir()) {
            throw new Exception("Can not create temporary dir");
        }
        log.info("CreateRootDir completed");
    }
}
