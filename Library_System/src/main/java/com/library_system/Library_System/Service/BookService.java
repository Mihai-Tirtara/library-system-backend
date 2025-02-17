package com.library_system.Library_System.Service;

import com.library_system.Library_System.Exceptions.ResourceNotFoundException;
import com.library_system.Library_System.Model.Book;
import com.library_system.Library_System.Repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BookService implements BookServiceInterface {
    private final BookRepository bookRepository;
    private final AiService aiService;

   @Autowired
    public BookService(BookRepository bookRepository, AiService aiService) {
        this.bookRepository = bookRepository;
        this.aiService = aiService;
    }

    @Override
    public Book createNewBook(Book book)
    {
        return bookRepository.save(book);
    }
    @Override
    public List<Book> getAllBooks()
    {
        return bookRepository.findAll();
    }
    @Override
    public Book getBookById(long id )
    {
        return bookRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Book not found with id:" + id));
    }
    @Override
    public Book updateBook(Book updatedBook, long  id )
    {
        Book existingBook = bookRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Book not Found"));

        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setPublicationYear(updatedBook.getPublicationYear());
        existingBook.setDescription(updatedBook.getDescription());

        return bookRepository.save(existingBook);
    }
    @Override
    public void deleteBook(long id){
        bookRepository.deleteById(id);
    }
    @Override
    public List<Book> searchBooks(String title, String author)
    {
        if(author != null && title != null)
        {
            System.out.println(title);
            System.out.println(bookRepository.findByAuthorAndTitle(author,title));
            return bookRepository.findByAuthorAndTitle(author,title);

        }
        else if(author != null)
        {
            System.out.println(bookRepository.findByAuthor(author));
            return bookRepository.findByAuthor(author);
        }
        else if(title !=null)
        {
            return bookRepository.findByTitle(title);
        }
        return getAllBooks();
    }

    @Override
    public String generateAiInsights(Long id){
        Book book = bookRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Book not found"));

        return  aiService.generateInsights(book);
    }


}
