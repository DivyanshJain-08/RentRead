package com.crio.RentRead.Service.Impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crio.RentRead.DTO.RentBookRequestDTO;
import com.crio.RentRead.DTO.RentBookResponseDTO;
import com.crio.RentRead.DTO.ReturnBookRequestDTO;
import com.crio.RentRead.DTO.ReturnBookResponseDTO;
import com.crio.RentRead.Model.AvailabilityStatus;
import com.crio.RentRead.Model.Book;
import com.crio.RentRead.Model.Rental;
import com.crio.RentRead.Model.User;
import com.crio.RentRead.Repository.BookRepository;
import com.crio.RentRead.Repository.RentalRepository;
import com.crio.RentRead.Repository.UserRepository;
import com.crio.RentRead.Service.RentalService;

@Service
public class RentalServiceImpl implements RentalService
{
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    @Transactional
    public RentBookResponseDTO rentBook(RentBookRequestDTO rentBookRequestDTO, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepository.findById(rentBookRequestDTO.getBookId()).orElseThrow(() -> new RuntimeException("Book not found"));

        // Check if the user has already rented 2 books
        List<Rental> activeRentals = rentalRepository.findByUserAndStatus(user , AvailabilityStatus.RENTED);
        if (activeRentals.size() >= 2) {
            throw new RuntimeException("Cannot rent more than two books at a time.");
        }

        // Check if the book is available to rent
        if (book.getStatus().equals("NOT_AVAILABLE")) {
            throw new RuntimeException("Book is not available for rent.");
        }

        // Create the rental record
        Rental rental = new Rental();
        rental.setUser(user);
        rental.setBook(book);
        rental.setRentalDate("2025-02-23"); // Here, you can use current date logic like LocalDate.now()
        rental.setStatus(AvailabilityStatus.RENTED);

        rentalRepository.save(rental);

        // Mark the book as unavailable
        book.setStatus("NOT_AVAILABLE");
        bookRepository.save(book);

        RentBookResponseDTO responseDTO = new RentBookResponseDTO();
        responseDTO.setRentalId(rental.getId());
        responseDTO.setBookId(book.getId());
        responseDTO.setBookTitle(book.getTitle());
        responseDTO.setRentalDate(rental.getRentalDate());
        responseDTO.setIsReturned(false);

        return responseDTO;
    }

    @Override
    @Transactional
    public ReturnBookResponseDTO returnBook(ReturnBookRequestDTO returnBookRequestDTO, Long userId) {
        Rental rental = rentalRepository.findById(returnBookRequestDTO.getRentalId())
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        if (!rental.getUser().getId().equals(userId)) {
            throw new RuntimeException("You cannot return a book rented by another user.");
        }

        rental.setStatus(AvailabilityStatus.AVAILABLE);
        rentalRepository.save(rental);

        // Mark the book as available
        Book book = rental.getBook();
        book.setStatus("AVAILABLE");
        bookRepository.save(book);

        ReturnBookResponseDTO responseDTO = new ReturnBookResponseDTO();
        responseDTO.setRentalId(rental.getId());
        responseDTO.setBookId(book.getId());
        responseDTO.setBookTitle(book.getTitle());
        responseDTO.setIsReturned(true);
        responseDTO.setReturnDate(LocalDate.now()); // Use current date

        return responseDTO;
    }
}
