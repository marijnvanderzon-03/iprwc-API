package iprwcAPI.services;



import iprwcAPI.Models.DBFile;
import iprwcAPI.Repository.DBFileRepository;
import iprwcAPI.pictureManager.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;


@Service
public class FileStorageService {

    @Autowired
    DBFileRepository DBFileRepo;

    public ResponseEntity<Resource> downloadFile(String fileName) throws Exception{
        Optional<DBFile> f = DBFileRepo.findByFileName(fileName);

        if (f.isEmpty()){
            throw new Exception("error downloading file");
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(f.get().getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+f.get().getFileName()+"\"")
                .body(new ByteArrayResource(f.get().getData()));
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        // Check if the file's name contains invalid characters
        if(fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }
        try {
            DBFile f = new DBFile(fileName, file.getBytes(), file.getContentType());

            DBFileRepo.save(f);
            return fileName;
        } catch (IOException e) {
            return "";
        }
    }
}
