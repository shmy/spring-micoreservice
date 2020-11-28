package tech.shmy.ossserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tech.shmy.ossserver.service.FileService;

@Slf4j
@Component
public class CommandLineRunnerBean implements CommandLineRunner {
    @Autowired
    private FileService fileService;
    @Override
    public void run(String... args) throws Exception {
        fileService.createRootDir();
        log.info("CreateRootDir completed");
    }
}
