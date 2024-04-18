package com.curso.java.bookservice.service;

import com.curso.java.bookservice.client.entities.EBook;
import com.curso.java.bookservice.client.stockservice.Stock;
import com.curso.java.bookservice.client.stockservice.StockUpdate;
import com.curso.java.bookservice.exceptions.NotFoundException;
import com.curso.java.bookservice.exceptions.ValidationException;
import com.curso.java.bookservice.model.Book;
import com.curso.java.bookservice.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final StockService stockService;

    @Autowired
    public BookService(BookRepository bookRepository, StockService stockService) {
        this.bookRepository = bookRepository;
        this.stockService = stockService;
    }

    public List<Book> getBooks() {
        return this.bookRepository.findAll()
                .stream()
                .map(this::toBook)
                .toList();
    }

    public Book find(long bookId) {
        Optional<EBook> bookOpt = this.bookRepository.findById(bookId);
        var book = bookOpt
                .map(eBook -> this.toBook(eBook))
                .orElseThrow(() -> {
                    return new NotFoundException(String.format("Book " + bookId + " not found"));
                });
        return book;
    }

    public Book save(Book book) {
        this.validate(book);
        var stock = new Stock();
        stock.setCurrentQuantity(10);
        var stockResponse = stockService.save(stock);
        if (stockResponse != null) {
            book.setStock(stockResponse);
        }
        
        var eBook = this.toEBook(book);
        var savedEBook = this.bookRepository.save(eBook);
        return this.toBook(savedEBook);
    }

    public Book update(Book book) {
        this.validate(book);
        this.find(book.getId());// Valida existencia
        var eBook = this.toEBook(book);
        EBook savedEBook =  this.bookRepository.save(eBook);
        return this.toBook(savedEBook);
    }

    public void delete(long bookId) {
        this.bookRepository.deleteById(bookId);
    }

    public void updateStock(long bookId, StockUpdate stock) {
        var book = this.find(bookId);

        if (book.getStock() != null) {
            var stockResponse = stockService.update(stock, book.getStock().getId());
            if (stockResponse != null) {
                book.setStock(stockResponse);
            }
            var eBook = toEBook(book);
            this.bookRepository.save(eBook);
        } else {
            System.out.println("Sin Stock");
            var newStock = new Stock();
            newStock.setCurrentQuantity(stock.getIncreaseQuantity());
            var stockResponse = stockService.save(newStock);
            if (stockResponse != null) {
                book.setStock(stockResponse);
            }
            var eBook = toEBook(book);
            this.bookRepository.save(eBook);
        }
    }

    private void validate(Book book) {
        if (book == null) {
            throw new ValidationException("Not book indicated");
        }
        if (book.getTitle() == null || book.getTitle().isBlank()) {
            throw new ValidationException("Title is required");
        }
        if (book.getSummary() == null || book.getSummary().isBlank()) {
            throw new ValidationException("Summary is required");
        }
        if (book.getYear() == null) {
            throw new ValidationException("Year is required");
        }
    }

    private EBook toEBook(Book book) {
        EBook eBook = new EBook();
        eBook.setId(book.getId());
        eBook.setAuthor(book.getAuthor());
        eBook.setTitle(book.getTitle());
        eBook.setSummary(book.getSummary());
        eBook.setYear(book.getYear());
        if (book.getStock() != null) {
            eBook.setStockId(book.getStock().getId());
        }
        return eBook;
    }

    private Book toBook(EBook eBook) {
        Book book = new Book();
        book.setId(eBook.getId());
        book.setAuthor(eBook.getAuthor());
        book.setTitle(eBook.getTitle());
        book.setSummary(eBook.getSummary());
        book.setYear(eBook.getYear());
        if (eBook.getStockId() != null) {
            var stock = this.stockService.find(eBook.getStockId());
            book.setStock(stock);
        }
        return book;
    }

}
