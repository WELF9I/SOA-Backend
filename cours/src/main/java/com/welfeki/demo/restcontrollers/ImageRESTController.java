package com.welfeki.demo.restcontrollers;

import com.welfeki.demo.entities.Cours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.welfeki.demo.entities.Image;
import com.welfeki.demo.service.ImageService;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/image")
@CrossOrigin(origins = "*")
public class ImageRESTController {

    @Autowired
    ImageService imageService;

    // Upload a new image
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Image uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        return imageService.uploadImage(file);
    }

    // Get image details by ID
    @RequestMapping(value = "/get/info/{id}", method = RequestMethod.GET)
    public Image getImageDetails(@PathVariable("id") Long id) throws IOException {
        return imageService.getImageDetails(id);
    }

    // Load image by ID
    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) throws IOException {
        return imageService.getImage(id);
    }

    // Delete an image by ID
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteImage(@PathVariable("id") Long id) {
        imageService.deleteImage(id);
    }

    // Update an existing image
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Image updateImage(@RequestParam("image") MultipartFile file) throws IOException {
        return imageService.uploadImage(file);
    }

    // Upload an image and associate it with a specific Cours by ID
    @PostMapping(value = "/uploadImageCours/{idCours}")
    public Image uploadMultiImages(@RequestParam("image") MultipartFile file,
                                   @PathVariable("idCours") Long idCours) throws IOException {
        return imageService.uploadImageCours(file, idCours);
    }

    // Get all images associated with a specific Cours by ID
    @RequestMapping(value = "/getImagesCours/{idCours}", method = RequestMethod.GET)
    public List<Image> getImagesCours(@PathVariable("idCours") Long idCours) throws IOException {
        return imageService.getImagesParCours(idCours);
    }

    @PostMapping("/uploadFS/{idCours}")
    public Cours uploadImageFS(@RequestParam("image") MultipartFile file,
                               @PathVariable("idCours") Long idCours) throws IOException {
        return imageService.updateCoursImage(file, idCours);
    }
}
