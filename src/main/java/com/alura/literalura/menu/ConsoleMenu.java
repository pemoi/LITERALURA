package com.alura.literalura.menu;

import com.alura.literalura.service.AutorService;
import com.alura.literalura.service.LibroService;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Scanner;

@Component
public class ConsoleMenu {

    private final LibroService libroService;
    private final AutorService autorService;

    public ConsoleMenu(LibroService libroService, AutorService autorService) {
        this.libroService = libroService;
        this.autorService = autorService;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        int option = -1;

        while (option != 0) {
            printMenu();
            System.out.print("Elige una opción: ");
            String in = scanner.nextLine();
            try {
                option = Integer.parseInt(in.trim());
            } catch (NumberFormatException e) {
                option = -1;
            }

            switch (option) {
                case 1 -> {
                    System.out.print("Ingresa el título del libro: ");
                    String titulo = scanner.nextLine().trim();
                    libroService.buscarYGuardarPorTitulo(titulo);
                }
                case 2 -> libroService.listarLibros();
                case 3 -> autorService.listarAutores();
                case 4 -> {
                    System.out.print("Ingresa el año (YYYY): ");
                    String s = scanner.nextLine().trim();
                    try {
                        int year = Integer.parseInt(s);
                        autorService.listarAutoresVivosEn(year);
                    } catch (NumberFormatException e) {
                        System.out.println("Año inválido.");
                    }
                }
                case 5 -> {
                    System.out.print("Ingresa idioma (ES, EN, FR, PT): ");
                    String lang = scanner.nextLine().trim().toUpperCase(Locale.ROOT);
                    libroService.listarLibrosPorIdioma(lang);
                }
                case 6 -> {
                    System.out.print("¿Cuántos deseas ver? N = ");
                    String s = scanner.nextLine().trim();
                    try {
                        int n = Integer.parseInt(s);
                        libroService.listarTopPorDescargas(n);
                    } catch (NumberFormatException e) {
                        System.out.println("Número inválido.");
                    }
                }
                case 7 -> {
                    System.out.print("Ingresa parte del nombre del autor: ");
                    String na = scanner.nextLine().trim();
                    libroService.buscarLibrosPorAutor(na);
                }
                case 0 -> System.out.println("¡Hasta luego!");
                default -> System.out.println("Opción inválida.");
            }
            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("==== LiterAlura ====");
        System.out.println("1 - Buscar libro por título (API Gutendex + guardar)");
        System.out.println("2 - Listar libros registrados");
        System.out.println("3 - Listar autores registrados");
        System.out.println("4 - Listar autores vivos en un año");
        System.out.println("5 - Listar libros por idioma (ES/EN/FR/PT)");
        System.out.println("6 - Top N libros por descargas");
        System.out.println("7 - Buscar libros por autor");
        System.out.println("0 - Salir");
    }
}
