package com.welfeki.demo.service;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.welfeki.demo.entities.Image;
import com.welfeki.demo.entities.Cours;
import com.welfeki.demo.repos.ImageRepository;
import com.welfeki.demo.repos.CoursRepository;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    CoursRepository coursRepository;

    @Override
    public Image uploadImage(MultipartFile file) throws IOException {
        return imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(file.getBytes())
                .build());
    }

    @Override
    public Image getImageDetails(Long id) throws IOException {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));
    }

    @Override
    public ResponseEntity<byte[]> getImage(Long id) throws IOException {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(image.getType()))
                .body(image.getImage());
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Image uploadImageCours(MultipartFile file, Long idCours) throws IOException {
        Cours cours = coursRepository.findById(idCours)
                .orElseThrow(() -> new RuntimeException("Cours not found"));

        Image image = Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(file.getBytes())
                .cours(cours)
                .build();

        return imageRepository.save(image);
    }

    @Override
    @Transactional
    public Cours updateCoursImage(MultipartFile file, Long idCours) throws IOException {
        Cours cours = coursRepository.findById(idCours)
                .orElseThrow(() -> new RuntimeException("Cours not found"));

        // Delete old image if it exists
        if (cours.getImage() != null) {
            Long oldImageId = cours.getImage().getIdImage();
            cours.setImage(null);
            coursRepository.save(cours);  // Detach old image
            imageRepository.deleteById(oldImageId);
        }

        // Create and save new image
        Image newImage = Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(file.getBytes())
                .cours(cours)
                .build();

        newImage = imageRepository.save(newImage);

        // Update cours with new image
        cours.setImage(newImage);
        return coursRepository.save(cours);
    }

    @Override
    public List<Image> getImagesParCours(Long idCours) {
        return imageRepository.findByCours_Id(idCours);
    }
}