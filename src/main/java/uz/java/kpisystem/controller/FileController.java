package uz.java.kpisystem.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.java.kpisystem.service.FileService;
import uz.java.kpisystem.service.MinioService;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;
    private final MinioService minioService;

    public FileController(FileService fileService, MinioService minioService) {
        this.fileService = fileService;
        this.minioService = minioService;
    }

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestBody MultipartFile file) {
        return ResponseEntity.ok(fileService.uploadFile(file));
    }

    @GetMapping("/presigned-url")
    public ResponseEntity<String> getPresignedUrl(@RequestParam String objectName) {
        String url = minioService.generatePresignedUrl(objectName);
        return ResponseEntity.ok(url);
    }
}
