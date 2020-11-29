package tech.shmy.ossserver.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tech.shmy.ossserver.service.impl.TemporaryFileService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class TemporaryFileController {
    @Autowired
    private TemporaryFileService temporaryFileService;

    @PostMapping("upload")
    public List<String> upload(MultipartFile[] files) throws Exception {
        val result = new ArrayList<String>();
        val dateDir = temporaryFileService.createDateDir();
        for (MultipartFile file : files) {
            val originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            String ext = temporaryFileService.getExt(originalFilename);
            val newFile = temporaryFileService.getNewFile(dateDir, ext);
            try {
                file.transferTo(newFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            result.add(temporaryFileService.getRelativePath(newFile));
        }
        return result;
    }
}
