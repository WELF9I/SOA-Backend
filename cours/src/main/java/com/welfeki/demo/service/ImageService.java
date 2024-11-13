package com.welfeki.demo.service;

import java.io.IOException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import com.welfeki.demo.entities.Image;
import com.welfeki.demo.entities.Cours;

public interface ImageService {
    Image uploadImage(MultipartFile file) throws IOException;
    Image getImageDetails(Long id) throws IOException;
    ResponseEntity<byte[]> getImage(Long id) throws IOException;
    void deleteImage(Long id);

    // Updated method names and signatures for clarity
    Image uploadImageCours(MultipartFile file, Long idCours) throws IOException;
    Cours updateCoursImage(MultipartFile file, Long idCours) throws IOException;
    List<Image> getImagesParCours(Long idCours);
}
