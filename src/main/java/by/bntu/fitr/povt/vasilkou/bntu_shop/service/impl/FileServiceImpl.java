package by.bntu.fitr.povt.vasilkou.bntu_shop.service.impl;

import by.bntu.fitr.povt.vasilkou.bntu_shop.service.api.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${upload.path}")
    private String UPLOAD_PATH;

    @Override
    //todo Mackyeb 8
    public String uploadFile(MultipartFile file) throws IOException {
        String resultFilename = "";
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(UPLOAD_PATH);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(UPLOAD_PATH + File.separator + resultFilename));
        }

        return resultFilename;
    }

    @Override
    //todo mackeyb 7
    public boolean deleteFile(String file) throws IOException {
        if (file == null) {
            return false;
        }
        Path path = Paths.get(UPLOAD_PATH + File.separator + file);
        Files.delete(path);
        return true;
    }
}
