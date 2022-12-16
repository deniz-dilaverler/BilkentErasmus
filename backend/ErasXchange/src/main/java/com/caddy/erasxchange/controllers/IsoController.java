package com.caddy.erasxchange.controllers;

import com.caddy.erasxchange.models.users.Student;
import com.caddy.erasxchange.services.StorageService;
import com.caddy.erasxchange.services.user.IsoService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@RestController
public class IsoController {

    private final StorageService fileStorageService;
    private final IsoService isoService;

    public IsoController(StorageService fileStorageService, IsoService isoService) {
        this.fileStorageService = fileStorageService;
        this.isoService = isoService;
    }

    @PostMapping("/api/upload")
    @PreAuthorize("hasRole('ISO')")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/download/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @GetMapping("/api/download/{fileName:.+}")
    @PreAuthorize("hasRole('ISO')")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws Exception {
        // Load file as Resource
        ByteArrayResource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
//        String contentType; //request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//
//        // Fallback to the default content type if type could not be determined
//        if(contentType == null) {
//            contentType = "application/octet-stream";
//        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @GetMapping("/api/read/{fileName:.+}")
    public List<Student> readSheet(@PathVariable String fileName, HttpServletRequest request) throws Exception {
        return isoService.readExcel(fileName);
    }

}

@Getter
@Setter
class UploadFileResponse {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }
}
