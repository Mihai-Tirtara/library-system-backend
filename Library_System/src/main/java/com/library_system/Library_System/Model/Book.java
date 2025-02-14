package com.library_system.Library_System.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;


@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Author is required")
    @Size(min = 1, max = 255, message = "Author name must be between 1 and 255 characters")
    @Column(nullable = false)
    private String author;
    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "^(?:ISBN(?:-13)?:? )?(?=[0-9]{13}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)97[89][- ]?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9]$",
            message = "Invalid ISBN format. Must be a valid ISBN-13 format")
    @Column(nullable = false)
    private String isbn;

    @NotNull(message = "Publication year is required")
    @Min(value = 1440, message = "Publication year must be after 1440 (invention of printing press)")
    @Max(value = 2025, message = "Publication year cannot be more than the current year")
    @Column(nullable = false)
    private Integer publicationYear;

    @Column
    @Size(max = 2000, message = "Description must be less than 2000 characters")
    private String description;

    // Default constructor
    public Book() {}

    // Constructor with fields
    public Book(String title, String author, String isbn, Integer publicationYear, String description) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}