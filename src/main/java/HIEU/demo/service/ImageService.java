package HIEU.demo.service;

import HIEU.demo.exception.AppException;
import HIEU.demo.model.Image;
import HIEU.demo.model.Project;
import HIEU.demo.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    @Transactional
    public List<Image> saveImages(List<MultipartFile> images, Project project) throws IOException {
        List<Image> imageList = new ArrayList<>();
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        for (MultipartFile file : images) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            file.transferTo(filePath.toFile());
            Image image = Image.builder()
                    .url("/uploads/" + fileName)
                    .project(project)
                    .build();
            imageList.add(image);
        }
        return imageRepository.saveAll(imageList);
    }
    @Transactional
    public void deleteImagesByProject(Project project) {
        List<Image> images = imageRepository.findByProject(project);
        for (Image image : images) {
            try {
                String fileName = image.getUrl().replace("/uploads/", "");
                Path filePath = Paths.get(UPLOAD_DIR, fileName);
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                throw new AppException("Failed to delete image: " + image.getUrl());
            }
        }
        imageRepository.deleteAll(images);
    }
}




