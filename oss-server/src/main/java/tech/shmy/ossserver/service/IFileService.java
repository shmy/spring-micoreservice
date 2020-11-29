package tech.shmy.ossserver.service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import lombok.val;
import lombok.var;

import java.io.File;
import java.nio.file.Paths;
import java.util.Calendar;

public interface IFileService {
    boolean setDir();

    String getDir();

    default boolean delete(File file) {
        return file.delete();
    }

    default String getExt(String filename) {
        String ext = "";
        val lastIndex = filename.lastIndexOf(".");
        if (lastIndex != -1) {
            ext = filename.substring(lastIndex + 1).toLowerCase();
        }
        return ext;
    }

    default String getRelativePath(File file) {
        return file.toString().replace(getDir(), "");
    }
    default String getFullPath(String relativePath) {
        return getDir() + relativePath;
    }

    default File getNewFile(String dateDir, String ext) {
        val filename = NanoIdUtils.randomNanoId();
        if (!ext.isEmpty()) {
            ext = "." + ext;
        }
        return Paths.get(dateDir, filename + ext).toFile();
    }

    default String createDateDir() throws Exception {
        val now = Calendar.getInstance();
        val yearStr = String.valueOf(now.get(Calendar.YEAR));
        val weekStr = String.valueOf(now.get(Calendar.WEEK_OF_MONTH));
        val month = now.get(Calendar.MONTH) + 1;
        val monthStr = month < 10 ? "0" + month : String.valueOf(month);
        val dirname = Paths.get(getDir(), yearStr, monthStr, weekStr);
        File folder = dirname.toFile();
        if (!folder.exists() && !folder.isDirectory()) {
            if (!folder.mkdirs()) {
                throw new Exception("dir create failed");
            }
        }
        return folder.toString();
    }
}
