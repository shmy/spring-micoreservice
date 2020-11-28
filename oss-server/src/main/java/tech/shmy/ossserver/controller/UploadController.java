package tech.shmy.ossserver.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tech.shmy.ossserver.service.FileService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class UploadController {
    @Autowired
    private FileService fileService;

    @PostMapping("upload")
    public List<String> upload(MultipartFile[] files) {
        val result = new ArrayList<String>();
        val uploadDir = fileService.getUploadDir();
        for (MultipartFile file : files) {
            val originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            var ext = fileService.getExt(originalFilename);
            val newFile = fileService.getFile(uploadDir, ext);
            try {
                file.transferTo(newFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            result.add(fileService.getRelativePath(newFile));
        }
        return result;
    }
    @GetMapping("/check")
    public FileService.FileCheckInfo check(@RequestParam() String path) throws Exception {
        return fileService.getFileInfoByPath(path);
    }
}
