package com.library_system.Library_System.Repository;

import com.library_system.Library_System.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    @Query("SELECT b FROM Book b WHERE b.author = :author")
    public List<Book> findByAuthor(@Param("author")String author);
    @Query("SELECT b FROM Book b WHERE b.title = :title")
    public List<Book> findByTitle(@Param("title")String title);
    @Query("SELECT b FROM Book b WHERE b.title = :title AND b.author = :author")
    public List<Book> findByAuthorAndTitle(@Param("author") String author, @Param("title")String title);

}
