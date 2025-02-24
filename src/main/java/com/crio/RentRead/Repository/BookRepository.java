package com.crio.RentRead.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crio.RentRead.Model.Book;

public interface BookRepository extends JpaRepository<Book , Long>
{
    List<Book> findByGenre(String genre);
    List<Book> findByTitleContaining(String title);
}
