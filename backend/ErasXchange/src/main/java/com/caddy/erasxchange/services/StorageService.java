package com.caddy.erasxchange.services;

import com.caddy.erasxchange.config.FileStorageProperties;
import com.caddy.erasxchange.models.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageService {
    private final Path fileStorageLocation;
    private final ApplicationStateService applicationStateService;


    @Autowired
    public StorageService(FileStorageProperties fileStorageProperties, ApplicationStateService applicationStateService) throws IOException {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        this.applicationStateService = applicationStateService;

        Files.createDirectories(this.fileStorageLocation);
    }

    public String storeFile(MultipartFile file) throws IOException {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Copy file to the target location (Replacing existing file with the same name)
        Path targetLocation = fileStorageLocation.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        applicationStateService.setErasmusAppState(Department.CS, PlacementStatus.FILE_UPLOADED);
        return fileName;
    }

    public ByteArrayResource loadFileAsResource(String fileName) throws Exception {
        try {
            Path filePath = fileStorageLocation.resolve(fileName).normalize().toAbsolutePath();
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(filePath));
            if(resource.exists()) {
                return resource;
            } else {
                throw new Exception("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new Exception("File not found " + fileName, ex);
        }
    }
}
