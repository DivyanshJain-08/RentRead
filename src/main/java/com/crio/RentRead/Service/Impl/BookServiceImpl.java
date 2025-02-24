package com.crio.RentRead.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crio.RentRead.DTO.BookDTO;
import com.crio.RentRead.Exception.ResourceNotFoundException;
import com.crio.RentRead.Model.Book;
import com.crio.RentRead.Repository.BookRepository;
import com.crio.RentRead.Service.BookService;
import com.crio.RentRead.Utilty.BookMapper;

@Service
public class BookServiceImpl implements BookService
{
     private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) 
    {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book addBook(BookDTO bookDTO) 
    {
        Book book = BookMapper.toEntity(bookDTO);
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() 
    {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long bookId) 
    {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));
    }

    @Override
    public Book updateBook(Long bookId, BookDTO bookDTO) 
    {
        Book book = getBookById(bookId);
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setGenre(bookDTO.getGenre());
        book.setStatus(bookDTO.getStatus());

        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long bookId) 
    {
        bookRepository.deleteById(bookId);
    }   
}
