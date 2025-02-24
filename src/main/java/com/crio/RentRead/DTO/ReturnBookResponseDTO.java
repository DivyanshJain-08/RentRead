package com.crio.RentRead.DTO;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnBookResponseDTO 
{
    private Long rentalId;
    private Long bookId;
    private String bookTitle;
    private Boolean isReturned;
    private LocalDate returnDate;    
}
