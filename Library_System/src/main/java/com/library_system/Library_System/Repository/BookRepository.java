package com.library_system.Library_System.Repository;

import com.library_system.Library_System.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    public List<Book> findByAuthor(String author);
    public List<Book> findByTitle(String title);
    public List<Book> findByAuthorAndTitle(String author,String title);

}
