package tech.shmy.ossserver.service.impl;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import tech.shmy.ossserver.service.IFileService;

import java.io.File;
import java.nio.file.Paths;

@Service
public class TemporaryFileService implements IFileService {
    public static final String DIR_NAME = "temp";
    @Autowired
    private Environment env;

    @Override
    public boolean setDir() {
        val dir = new File(getDir());
        if (!dir.exists() && !dir.isDirectory()) {
            return dir.mkdirs();
        }
        return true;
    }

    @Override
    public String getDir() {
        val rootPath = env.getProperty("UPLOAD_PATH");
        assert rootPath != null;
        val dir = Paths.get(rootPath, DIR_NAME).toString();
        return dir.endsWith("/") ? dir : dir + "/";
    }

}
