package com.crio.RentRead.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crio.RentRead.DTO.RentBookRequestDTO;
import com.crio.RentRead.DTO.RentBookResponseDTO;
import com.crio.RentRead.DTO.ReturnBookRequestDTO;
import com.crio.RentRead.DTO.ReturnBookResponseDTO;
import com.crio.RentRead.Service.RentalService;

@RestController
@RequestMapping("/books")
public class RentalController 
{
    
    private final RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/{bookId}/rent")
    public ResponseEntity<RentBookResponseDTO> rentBook(@PathVariable Long bookId, 
                                                        @RequestBody RentBookRequestDTO rentBookRequestDTO, @RequestParam Long userId) 
    {
        rentBookRequestDTO.setBookId(bookId);
        RentBookResponseDTO responseDTO = rentalService.rentBook(rentBookRequestDTO, userId);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/{bookId}/return")
    public ResponseEntity<ReturnBookResponseDTO> returnBook(@PathVariable Long bookId, 
                                                            @RequestBody ReturnBookRequestDTO returnBookRequestDTO, @RequestParam Long userId) 
    {
        ReturnBookResponseDTO responseDTO = rentalService.returnBook(returnBookRequestDTO, userId);
        return ResponseEntity.ok(responseDTO);
    }
}
