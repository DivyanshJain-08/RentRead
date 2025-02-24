package com.crio.RentRead.Service;

import java.util.List;

import com.crio.RentRead.DTO.BookDTO;
import com.crio.RentRead.Model.Book;

public interface BookService 
{
    Book addBook(BookDTO bookDTO);
    List<Book> getAllBooks();
    Book getBookById(Long bookId);
    Book updateBook(Long bookId, BookDTO bookDTO);
    void deleteBook(Long bookId);
}
