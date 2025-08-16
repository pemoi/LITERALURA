package com.alura.literalura.service;

import com.alura.literalura.api.GutendexClient;
import com.alura.literalura.dto.GutendexAuthor;
import com.alura.literalura.dto.GutendexBook;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class LibroService {

    private final GutendexClient gutendexClient;
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public LibroService(GutendexClient gutendexClient, LibroRepository libroRepository, AutorRepository autorRepository) {
        this.gutendexClient = gutendexClient;
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void buscarYGuardarPorTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            System.out.println("Debes ingresar un título.");
            return;
        }
        List<GutendexBook> results = gutendexClient.searchByTitle(titulo);
        if (results.isEmpty()) {
            System.out.println("Libro no encontrado en la API.");
            return;
        }

        GutendexBook book = results.get(0);

        if (book.getId() == null) {
            System.out.println("El resultado de la API no tiene ID de Gutenberg; no se puede registrar de forma confiable.");
            return;
        }
        if (libroRepository.existsByGutenbergId(book.getId())) {
            System.out.println("El libro ya existe en la base de datos (duplicado evitado).");
            return;
        }

        Autor autor = null;
        if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
            GutendexAuthor a = book.getAuthors().get(0);
            String nombre = a.getName();
            Integer nac = a.getBirthYear();
            Integer def = a.getDeathYear();

            autor = autorRepository.findByNombreIgnoreCase(nombre).orElseGet(() -> {
                Autor nuevo = new Autor(nombre, nac, def);
                return autorRepository.save(nuevo);
            });
        } else {
            autor = autorRepository.findByNombreIgnoreCase("Desconocido").orElseGet(() ->
                    autorRepository.save(new Autor("Desconocido", null, null)));
        }

        String idioma = "ND";
        if (book.getLanguages() != null && !book.getLanguages().isEmpty()) {
            idioma = book.getLanguages().get(0).toUpperCase(Locale.ROOT);
        }
        Integer descargas = book.getDownloadCount() != null ? book.getDownloadCount() : 0;

        Libro libro = new Libro(book.getTitle(), idioma, descargas, book.getId(), autor);
        libroRepository.save(libro);
        System.out.println("Guardado: " + libro);
    }

    public void listarLibros() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }
        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo, String.CASE_INSENSITIVE_ORDER))
                .forEach(l -> System.out.printf("- %s | Autor: %s | Idioma: %s | Descargas: %d%n",
                        l.getTitulo(), l.getAutor().getNombre(), l.getIdioma(), Optional.ofNullable(l.getDescargas()).orElse(0)));
    
    public void listarTopPorDescargas(int n) {
        if (n <= 0) {
            System.out.println("Indica un número positivo.");
            return;
        }
        java.util.List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }
        System.out.println("Top " + n + " por descargas:");
        libros.stream()
                .sorted(java.util.Comparator.comparing((Libro l) -> java.util.Optional.ofNullable(l.getDescargas()).orElse(0)).reversed())
                .limit(n)
                .forEach(l -> System.out.printf("- %s | Autor: %s | Idioma: %s | Descargas: %d%n",
                        l.getTitulo(), l.getAutor().getNombre(), l.getIdioma(), java.util.Optional.ofNullable(l.getDescargas()).orElse(0)));
    }

    public void buscarLibrosPorAutor(String nombreAutor) {
        if (nombreAutor == null || nombreAutor.isBlank()) {
            System.out.println("Debes indicar el nombre del autor.");
            return;
        }
        java.util.List<Libro> libros = libroRepository.findByAutor_NombreContainingIgnoreCaseOrderByTituloAsc(nombreAutor.trim());
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros para el autor que contiene: "" + nombreAutor + "".");
            return;
        }
        System.out.println("Libros cuyo autor contiene "" + nombreAutor + "":");
        libros.forEach(l -> System.out.printf("- %s | Autor: %s | Idioma: %s | Descargas: %d%n",
                l.getTitulo(), l.getAutor().getNombre(), l.getIdioma(), java.util.Optional.ofNullable(l.getDescargas()).orElse(0)));
    }
}

    public void listarLibrosPorIdioma(String idioma) {
        if (idioma == null || idioma.isBlank()) {
            System.out.println("Debes indicar un idioma (ES/EN/FR/PT).");
            return;
        }
        List<Libro> libros = libroRepository.findByIdiomaIgnoreCaseOrderByTituloAsc(idioma);
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en idioma " + idioma + ".");
            return;
        }
        libros.forEach(l -> System.out.printf("- %s | Autor: %s | Idioma: %s | Descargas: %d%n",
                l.getTitulo(), l.getAutor().getNombre(), l.getIdioma(), Optional.ofNullable(l.getDescargas()).orElse(0)));
    }
}
