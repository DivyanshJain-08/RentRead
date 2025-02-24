package com.crio.RentRead.Utilty;

import com.crio.RentRead.DTO.BookDTO;
import com.crio.RentRead.Model.Book;

public class BookMapper 
{
     public static Book toEntity(BookDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setGenre(dto.getGenre());
        book.setStatus(dto.getStatus()); 
        return book;
    }

    public static BookDTO toDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setGenre(book.getGenre());
        dto.setStatus(book.getStatus());  
        return dto;
    }    
}
