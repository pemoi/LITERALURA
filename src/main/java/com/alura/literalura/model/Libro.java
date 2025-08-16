package com.alura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros", uniqueConstraints = {
        @UniqueConstraint(name = "uk_libros_gutenberg", columnNames = {"gutenberg_id"})
})
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(length = 5)
    private String idioma; // ISO-639-1

    private Integer descargas;

    @Column(name = "gutenberg_id", nullable = false)
    private Long gutenbergId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Autor autor;

    public Libro() {}

    public Libro(String titulo, String idioma, Integer descargas, Long gutenbergId, Autor autor) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.descargas = descargas;
        this.gutenbergId = gutenbergId;
        this.autor = autor;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public Integer getDescargas() { return descargas; }
    public void setDescargas(Integer descargas) { this.descargas = descargas; }
    public Long getGutenbergId() { return gutenbergId; }
    public void setGutenbergId(Long gutenbergId) { this.gutenbergId = gutenbergId; }
    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + ''' +
                ", idioma='" + idioma + ''' +
                ", descargas=" + descargas +
                ", gutenbergId=" + gutenbergId +
                ", autor=" + (autor != null ? autor.getNombre() : "N/A") +
                '}';
    }
}
