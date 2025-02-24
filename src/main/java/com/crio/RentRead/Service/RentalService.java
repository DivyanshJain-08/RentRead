package com.crio.RentRead.Service;

import com.crio.RentRead.DTO.RentBookRequestDTO;
import com.crio.RentRead.DTO.RentBookResponseDTO;
import com.crio.RentRead.DTO.ReturnBookRequestDTO;
import com.crio.RentRead.DTO.ReturnBookResponseDTO;

public interface RentalService 
{
    RentBookResponseDTO rentBook(RentBookRequestDTO rentBookRequestDTO, Long userId);
    ReturnBookResponseDTO returnBook(ReturnBookRequestDTO returnBookRequestDTO, Long userId); 
}
