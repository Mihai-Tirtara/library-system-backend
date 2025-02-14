package com.library_system.Library_System.Service;

import com.library_system.Library_System.Model.Book;

import java.util.List;
import java.util.Optional;

public interface BookServiceInterface {

     Book createNewBook(Book book);
     List<Book> getAllBooks();
    Optional<Book> getBookById(long id );
    Book updateBook(Book updatedBook, long  id );
    void deleteBook(long id);
    List<Book> searchBooks(String title, String author);
    String generateAiInsights(Long bookId);

}
