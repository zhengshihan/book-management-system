package com.bookstore.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bookstore.exception.BookNotFoundException;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import com.bookstore.service.BookService;

@Service
public class BookServiceImpl implements BookService {

    Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);
	
    @Autowired
    private BookRepository bookRepository;
    
	@Override
	public List<Book> getAllBooksByAuthor(String author) {
		// TODO Auto-generated method stub
		logger.trace("Entered get AllBooksByAuthor method");
		
		List<Book> books = bookRepository.findByAuthor(author);
		
		return books;
	}
//	@Override
//	public List<Book> getAllBooks() {
//		// TODO Auto-generated method stub
//		logger.trace("Entered get AllBooks method");
//		
//		List<Book> books = bookRepository.findAll();
//		
//		return books;
//	}
	@Override
	public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
	@Override
	public Book getBook(String author, long id) {
		// TODO Auto-generated method stub
		return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
	}

	@Override
	public void deleteBook(String author, long id) {
		// TODO Auto-generated method stub
		Optional<Book> book = bookRepository.findById(id);
		if(book.isPresent()) {
			bookRepository.deleteById(id);
		}else {
			throw new BookNotFoundException(id);
		}
		
	}

	@Override
	public Book updateBook(String author, long id, Book book) {
		// TODO Auto-generated method stub
		Book bookUpdated = bookRepository.save(book);
		
		return bookUpdated;
	}

	@Override
	public Book createBook(String author, Book book) {
		// TODO Auto-generated method stub
		Book createdBook = bookRepository.save(book);
		return createdBook;
	}

	@Override
	public List<Book> searchBookByAuthorAndTitle(String author, String title) {
		// TODO Auto-generated method stub
		List<Book> books = bookRepository.findByAuthorAndTitle(author, title);
		return books;
	}
	@Override
	public List<Book> getBooksByAuthorAfterYear(String author, int year) {
        return bookRepository.findByAuthorAndYearGreaterThan(author, year);
    }

}
