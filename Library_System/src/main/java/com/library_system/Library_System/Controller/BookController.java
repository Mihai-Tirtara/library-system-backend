package com.library_system.Library_System.Controller;

import com.library_system.Library_System.Model.Book;
import com.library_system.Library_System.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book)
    {
        Book newBook = bookService.createNewBook(book);
        return ResponseEntity.ok(newBook);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks()
    {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Book>> getBookById(@PathVariable long id )
    {
        Optional<Book> book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam(required = false) String title, @RequestParam(required = false) String author )
    {
        List<Book> books = bookService.searchBooks(title,author);
        return ResponseEntity.ok(books);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Book updatedBook = bookService.updateBook(bookDetails,id);
        return ResponseEntity.ok(updatedBook);
    }

    // Delete a book
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(Map.of("deleted", true));
    }

    @GetMapping("/{id}/ai-insights")
    public ResponseEntity<Map<String, String>> getAiInsights(@PathVariable Long id) {
        String insights = bookService.generateAiInsights(id);
        return ResponseEntity.ok(Map.of("insights", insights));
    }





}
