package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombreIgnoreCase(String nombre);

    java.util.List<Autor> findByNombreContainingIgnoreCase(String nombre);

    @Query("""
            SELECT a FROM Autor a
            WHERE a.anioNacimiento IS NOT NULL
              AND a.anioNacimiento <= :year
              AND (a.anioMuerte IS NULL OR a.anioMuerte >= :year)
            ORDER BY a.nombre ASC
           """)
    List<Autor> findAutoresVivosEn(int year);
}
