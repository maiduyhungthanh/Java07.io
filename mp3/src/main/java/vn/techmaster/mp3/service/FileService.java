package vn.techmaster.mp3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import vn.techmaster.mp3.exception.BadRequestException;
import vn.techmaster.mp3.exception.NotFoundException;
import vn.techmaster.mp3.model.Singer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileService {
    @Autowired private SongSingerService songSingerService;

    //tạo 1 file uploads
    private final Path rootDir = Paths.get("src/main/resources/static/uploads");

    public FileService(){
        createFolder(rootDir.toString());
    }
    public  void createFolder(String path){
        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdirs();
        }
    }

    //định dạng file phần ADD
    public String uploadImages(MultipartFile file){
        // Kiểm tra tên file
        String fileName = file.getOriginalFilename();
        if(fileName == null || fileName.equals("")){
            throw  new BadRequestException("Tên file không hợp lệ");
        }
        // Kiểm tra định dạng
        String fileExtenSion = getFileExtension(fileName);
        if(!checkFileExtension(fileExtenSion)){
            throw  new BadRequestException("Vui lòng chỉ upload file có các định đạng png,jpg,jpeg");
        }
        // Kiểm tra KB file ảnh
        if((double)file.getSize() / 1_000_000L >2){
            throw  new BadRequestException(("File không được vượt quá 2 MB"));
        }
        // Tạo tên file ảnh
        String generateFilename = UUID.randomUUID().toString() +fileName;
        // Link file ảnh
        File serverFile = new File(rootDir.toString()+"/"+ generateFilename);
        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(file.getBytes());
            stream.close();

            String filePath = generateFilename;

            // songSingerService.getSingerById(id).setAvatar(filePath);
            return filePath;
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("Lỗi Khi upload");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //Up Ảnh phẩn Edit
    public String uploadfile(String id, MultipartFile file) {
        Optional<Singer> singerOptional = songSingerService.SingerById(id);
        if(singerOptional.isEmpty()){
            throw new NotFoundException("singer id " +id +" not found");
        }
        // Kiểm tra file
        String fileName = file.getOriginalFilename();
        if(fileName == null || fileName.equals("")){
            throw  new BadRequestException("Tên file không hợp lệ");
        }

        String fileExtenSion = getFileExtension(fileName);
        if(!checkFileExtension(fileExtenSion)){
            throw  new BadRequestException("Vui lòng chỉ upload file có các định đạng png,jpg,jpeg");
        }

        if((double)file.getSize() / 1_000_000L >2){
            throw  new BadRequestException(("File không được vượt quá 2 MB"));
        }

        // Path SingerDir = Paths.get("uploads").resolve(id);
        // createFolder(SingerDir.toString());

        //Tạo Path tương ứng với file Upload lên
        String generateFilename = UUID.randomUUID().toString() +fileName;
        File serverFile = new File(rootDir.toString()+"/"+ generateFilename);

        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(file.getBytes());
            stream.close();

            String filePath = generateFilename;

            singerOptional.get().setAvatar(filePath);
            return filePath;
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("Lỗi Khi upload");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private String getFileExtension(String  fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return fileName.substring(lastIndexOf+1);
    }

    private static boolean checkFileExtension(String fileExtension ){
        List<String> fileExtensions = Arrays.asList("png","jpg","jpeg");
        return fileExtensions.contains(fileExtension);
    }

    public byte[] readFile(String id, String fileName) {
        // Lấy đường dẫn file tương ứng với user_id
        // Path singerPath = rootDir.resolve(id);
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
    public byte[] readImage (String fileName) {
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

