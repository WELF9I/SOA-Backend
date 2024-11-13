package com.welfeki.demo.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.welfeki.demo.entities.Image;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByCours_Id(Long idCours);
}