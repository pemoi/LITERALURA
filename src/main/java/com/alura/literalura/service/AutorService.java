package com.alura.literalura.service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }
        autores.forEach(a -> System.out.printf("- %s (Nac: %s, Def: %s)%n",
                a.getNombre(),
                a.getAnioNacimiento() != null ? a.getAnioNacimiento() : "¿?",
                a.getAnioMuerte() != null ? a.getAnioMuerte() : "¿?"));
    }

    public void listarAutoresVivosEn(int year) {
        List<Autor> autores = autorRepository.findAutoresVivosEn(year);
        if (autores.isEmpty()) {
            System.out.println("No hay autores vivos en el año " + year + " según los registros.");
            return;
        }
        System.out.println("Autores vivos en " + year + ":");
        autores.forEach(a -> System.out.printf("- %s (Nac: %s, Def: %s)%n",
                a.getNombre(),
                a.getAnioNacimiento() != null ? a.getAnioNacimiento() : "¿?",
                a.getAnioMuerte() != null ? a.getAnioMuerte() : "¿?"));
    }
}
