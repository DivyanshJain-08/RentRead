package com.crio.RentRead.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentBookResponseDTO 
{
    private Long rentalId;
    private Long bookId;
    private String bookTitle;
    private String rentalDate;
    private Boolean isReturned;    
}
