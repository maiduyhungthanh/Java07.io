package vn.techmaster.mp3.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import vn.techmaster.mp3.exception.BadRequestException;

@Service
public class FileMp3Service {
 

    // tạo 1 file chứa nhạc
    private final Path rootDir = Paths.get("src/main/resources/static/music");

    public FileMp3Service() {
        createFolder(rootDir.toString());
    }

    public void createFolder(String path) {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    // đinh dạng file mp3 phần add Song
    public String addMp3(MultipartFile file) {
        // Kiểm tra tên file
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.equals("")) {
            throw new BadRequestException("Tên file không hợp lệ");
        }
        // Kiểm tra định dạng
        String fileExtenSion = getFileExtension(fileName);
        if (!checkFileExtension(fileExtenSion)) {
            throw new BadRequestException("Vui lòng chỉ upload file có các định đạng mp3");
        }
     // Kiểm tra KB file ảnh
     if((double)file.getSize() / 100_000_000L >200){
        throw  new BadRequestException(("File không được vượt quá 200 MB"));
    }
        // Tạo Path tương ứng với file Upload lên
        String generateFilename = UUID.randomUUID().toString() + fileName;
        File serverFile = new File(rootDir.toString() + "/" + generateFilename);

        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(file.getBytes());
            stream.close();

            String filePath = generateFilename;

            // songOptional.get().setAvatar(filePath);
            return filePath;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Lỗi Khi upload");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // lấy định dạng file sau dấu chấm
    private String getFileExtension(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return fileName.substring(lastIndexOf + 1);
    }

    // check đúng định dạng ảnh không
    private static boolean checkFileExtension(String fileExtension) {
        List<String> fileExtensions = Arrays.asList("mp3");
        return fileExtensions.contains(fileExtension);
    }

    public byte[] readMp3(String fileName) {
        // Lấy đường dẫn file tương ứng với user_id
        Path singerPath = rootDir;

        // Kiểm tra đường dẫn file có tồn tại hay không
        if (!Files.exists(singerPath)) {
            throw new RuntimeException("Không thể đọc file : " + fileName);
        }

        try {
            // Lấy đường dẫn file tương ứng với user_id và file_name
            Path file = singerPath.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return StreamUtils.copyToByteArray(resource.getInputStream());
            } else {
                throw new RuntimeException("Không thể đọc file: " + fileName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Không thể đọc file : " + fileName);
        }
    }
}
