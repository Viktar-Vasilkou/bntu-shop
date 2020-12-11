package by.bntu.fitr.povt.vasilkou.bntu_shop.service.api;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String uploadFile(MultipartFile file) throws IOException;
    String editFile(MultipartFile file);
    boolean deleteFile(String file) throws IOException;
}
