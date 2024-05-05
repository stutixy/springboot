package com.stuti.database.Controller;

import com.stuti.database.domain.dto.AuthorDto;
import com.stuti.database.domain.dto.BookDto;
import com.stuti.database.domain.entities.AuthorEntity;
import com.stuti.database.domain.entities.BookEntity;
import com.stuti.database.mappers.Mapper;
import com.stuti.database.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private BookService bookService;

    private Mapper<BookEntity, BookDto> bookMapper;

    public BookController(BookService bookService, Mapper<BookEntity, BookDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto){
        BookEntity bookEntity = bookMapper.mapfrom(bookDto);
        boolean exist = bookService.isExist(isbn);
        BookEntity savedBookEntity = bookService.createBook(bookEntity, isbn);
        if(exist) {
            return new ResponseEntity<>(bookMapper.mapto(savedBookEntity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(bookMapper.mapto(savedBookEntity), HttpStatus.CREATED);
        }
    }

    @GetMapping("/books")
    public List<BookDto> listBooks() {
        List<BookEntity> books = bookService.findAll();
        return books.stream().map(bookMapper::mapto).collect(Collectors.toList());
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<BookDto>  getBook(@PathVariable("isbn") String isbn) {
        Optional<BookEntity> books = bookService.findById(isbn);
        return books.map(bookEntity -> {
            BookDto bookDto = bookMapper.mapto(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> partialUpdate(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto ){
        if(!bookService.isExist(isbn)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookDto.setIsbn(isbn);
        BookEntity bookEntity = bookMapper.mapfrom(bookDto);
        BookEntity savedBookEntity= bookService.partialUpdate(bookEntity);
        return new ResponseEntity<>(bookMapper.mapto(savedBookEntity), HttpStatus.OK);
    }
    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> deleteAuthor(@PathVariable("isbn") String isbn){
        bookService.delete(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
