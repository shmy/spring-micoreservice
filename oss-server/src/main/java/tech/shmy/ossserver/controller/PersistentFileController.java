package tech.shmy.ossserver.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.shmy.ossserver.service.impl.PersistentFileService;
import tech.shmy.ossserver.service.impl.TemporaryFileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class PersistentFileController {
    @Autowired
    private TemporaryFileService temporaryFileService;
    @Autowired
    private PersistentFileService persistentFileService;
    @PostMapping("persistent")
    public List<Map<String, Object>> save(@RequestBody List<String> files) {
        val result = new ArrayList<Map<String, Object>>();
        for (String filePath : files) {
            val m = new HashMap<String, Object>();
            val fullPath = temporaryFileService.getFullPath(filePath);
            val file = new File(fullPath);
            var isSuccess = false;
            if (file.exists() && file.isFile()) {
                try {
                    val targetPath = Paths.get(persistentFileService.getDir(), filePath);
                    val parentFolder = targetPath.getParent().toFile();
                    if (!parentFolder.exists() && !parentFolder.isDirectory()) {
                        if (!parentFolder.mkdirs()) {
                            throw new IOException("can not create parentFolder: " + parentFolder);
                        }
                    }
                    Files.move(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                    isSuccess = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            m.put("filePath", filePath);
            m.put("isSuccess", isSuccess);
            result.add(m);
        }
        return result;
    }

}
