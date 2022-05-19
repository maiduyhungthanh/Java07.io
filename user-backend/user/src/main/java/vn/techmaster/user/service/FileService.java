package vn.techmaster.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.techmaster.user.exception.BadRequestException;
import vn.techmaster.user.exception.NotFoundException;
import vn.techmaster.user.model.User;
import vn.techmaster.user.response.FileReturn;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileService {
    private final Path rootDir = Paths.get("uploads");
    @Autowired private UserService userService;

    public FileService(){
        createFolder(rootDir.toString());
    }
    public  void createFolder(String path){
        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdirs();
        }
    }

    public String uploadfile(int id, MultipartFile file) {
        Optional<User> userOptional = userService.findById(id);
        if(userOptional.isEmpty()){
            throw new NotFoundException("user id " +id +" not found");
        }

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

        Path UserDir = Paths.get("uploads").resolve(String.valueOf(id));
        createFolder(UserDir.toString());

        //Tạo Path tương ứng với file Upload lên
        String generateFilename = UUID.randomUUID().toString() +fileName;
        File serverFile = new File(UserDir.toString()+"/"+ generateFilename);

        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(file.getBytes());
            stream.close();

            String filePath = "/files/"+id+"/"+generateFilename;

            userOptional.get().setAvatar(filePath);
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

    public byte[] readFile(int id, String fileName) {
        // Lấy đường dẫn file tương ứng với user_id
        Path userPath = rootDir.resolve(String.valueOf(id));

        // Kiểm tra đường dẫn file có tồn tại hay không
        if (!Files.exists(userPath)) {
            throw new RuntimeException("Không thể đọc file : " + fileName);
        }

        try {
            // Lấy đường dẫn file tương ứng với user_id và file_name
            Path file = userPath.resolve(fileName);
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

    public FileReturn getFiles(int id, int page) {
        final int numberImageofPage = 10;
        // Lấy đường dẫn file tương ứng với user_id
        Path userPath = rootDir.resolve(String.valueOf(id));

        // Kiểm tra đường dẫn file có tồn tại hay không
        if (!Files.exists(userPath)) {
            throw new RuntimeException("Không thể lấy danh sách file");
        }

        //Lấy danh sách file
        List<File> files = Arrays.asList(userPath.toFile().listFiles());

        //Công thức tính page
        //List<File> filesReturn = files.subList((page -1)*numberImageofPage,(page*numberImageofPage)-1);
        List<File> filesReturn =files.stream().skip((page -1)*numberImageofPage).limit(numberImageofPage).collect(Collectors.toList());
        int totalPage = (int)Math.ceil((double) files.size()/numberImageofPage);

      List<String> filesPath = filesReturn.stream().map(file -> "/files/"+id+"/"+file.getName()).toList();
       return new FileReturn(filesPath,totalPage);
    }
}

