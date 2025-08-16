package com.alura.literalura;

import com.alura.literalura.menu.ConsoleMenu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

    private final ConsoleMenu menu;

    public LiterAluraApplication(ConsoleMenu menu) {
        this.menu = menu;
    }

    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }

    @Override
    public void run(String... args) {
        menu.start();
    }
}
