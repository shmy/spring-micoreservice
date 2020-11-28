package tech.shmy.ossserver.service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import lombok.Data;
import lombok.val;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;
import java.util.Calendar;

@Service
public class FileService {
    @Autowired
    private Environment env;

    public String getUploadDir() {
        val rootPath = getRootDir();
        val now = Calendar.getInstance();
        val yearStr = String.valueOf(now.get(Calendar.YEAR));
        val weekStr = String.valueOf(now.get(Calendar.WEEK_OF_MONTH));
        val month = now.get(Calendar.MONTH) + 1;
        val monthStr = month < 10 ? "0" + month : String.valueOf(month);
        val dirname = Paths.get(rootPath, yearStr, monthStr, weekStr);
        File folder = dirname.toFile();
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
        return folder.toString();
    }

    public String getRootDir() {
        val rootPath = env.getProperty("UPLOAD_PATH");
        assert rootPath != null;
        return rootPath.endsWith("/") ? rootPath : rootPath + "/";
    }

    public File getFile(String uploadDir, String ext) {
        val filename = NanoIdUtils.randomNanoId();
        if (!ext.isEmpty()) {
            ext = "." + ext;
        }
        return Paths.get(uploadDir, filename + ext).toFile();
    }

    public String getRelativePath(File file) {
        val rootPath = getRootDir();
        return file.toString().replace(rootPath, "");
    }

    public String getExt(String filename) {
        var ext = "";
        val lastIndex = filename.lastIndexOf(".");
        if (lastIndex != -1) {
            ext = filename.substring(lastIndex + 1).toLowerCase();
        }
        return ext;
    }

    public FileCheckInfo getFileInfoByPath(String path) throws Exception {
        val dirname = Paths.get(getRootDir(), path);
        File file = dirname.toFile();
        if (!file.exists() || !file.isFile()) {
            throw new Exception("file is not exists");
        }
        val fileCheckInfo = new FileCheckInfo();
        fileCheckInfo.setSize(file.length());
        fileCheckInfo.setFilename(file.getName());
        fileCheckInfo.setExt(getExt(fileCheckInfo.getFilename()));

        return fileCheckInfo;
    }

    @Data
    public static class FileCheckInfo {
        private long size;
        private String filename;
        private String ext;
    }
}
