/*
 * Project Name:BookStore
 * Author:Priyadharshini Viswanathan
 * Date Created : April 6,2018
 */
package com.bookstore.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bookstore.bean.Book;
import com.bookstore.bean.BookQuantityStatus;
import com.bookstore.service.BookList;
import com.bookstore.util.BookStoreUtil;

/**
 * The Class BookListImpl.
 */
@Service
public class BookListImpl implements BookList{
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(BookListImpl.class);
	
	/**
	 * List books based on search criteria.
	 *
	 * @param searchString the search string
	 * @return the book[]
	 */
	public Book[] list(String searchString) {
		logger.info("listing books start");
		List<Book> searchedBooks = new ArrayList<Book>();
		try{
			List<Book> booksAvailable = BookStoreUtil.getBooks();
			if(this.isNullOrEmpty(searchString)){
				return listToArray(booksAvailable);
			}
			searchString = searchString.trim();
			for (Book book : booksAvailable) {
				if(searchString.equalsIgnoreCase(book.getAuthor()) || searchString.equalsIgnoreCase(book.getTitle())){
					searchedBooks.add(book);
				}
			}
			
		}catch (Exception e) {
			logger.error("Exception occured while listing books ",e);
		}
		logger.info("listing books end");
		return listToArray(searchedBooks);
	}

	/**
	 * Adds the book to the bookstore
	 *
	 * @param book the book
	 * @param quantity the quantity
	 * @return true, if successful
	 */
	public boolean add(Book book, int quantity) {
		logger.info("adding books start");
		try{
			if(null != book){
				BookStoreUtil.getBooks().add(book);
				BookStoreUtil.getBookQuantity().put(book, quantity);
				return true;
			}
		}catch (Exception e) {
			logger.error("Exception occured while adding books ",e);
		}
		logger.info("adding books end");
		return false;
	}

	/**
	 * check the status of the books in bookstore
	 *
	 * @param books the books
	 * @return the int[]
	 */
	public int[] buy(Book... books) {
		logger.info("buying books start");
		int[] bookStatus = null;
		try{
			bookStatus = new int[books.length];
			for (int count = 0; count < books.length; count++) {
				bookStatus[count] = BookQuantityStatus.DOES_NOT_EXIST.getValue();
				for (Map.Entry<Book, Integer> entry :BookStoreUtil.getBookQuantity().entrySet()) {
					if(this.isSameBook(books[count], entry.getKey())){
						if(0 < entry.getValue()){
							bookStatus[count] = BookQuantityStatus.OK.getValue();
						}else{
							bookStatus[count] = BookQuantityStatus.NOT_IN_STOCK.getValue();
						}
						break;
					}
				}
			}
		}catch (Exception e) {
			logger.error("Exception occured while buying books ",e);
		}
		logger.info("buying books end");
		return bookStatus;
	}
	
	/**
	 * Checks if is null or empty.
	 *
	 * @param value the value
	 * @return true, if is null or empty
	 */
	private boolean isNullOrEmpty(String value){
        if (null != value && !value.trim().isEmpty()){
              return Boolean.FALSE;
        }else{
              return Boolean.TRUE;
        }
	}
	
	/**
	 * Convert List to array.
	 *
	 * @param books the books
	 * @return the book[]
	 */
	private Book[] listToArray(List<Book> books) {
		if(books.isEmpty()){
			return null;
		}else{
			return books.toArray(new Book[books.size()]);
		}
	}
	
	/**
	 * Checks if is same book.
	 *
	 * @param book1 the book 1
	 * @param book2 the book 2
	 * @return true, if is same book
	 */
	private boolean isSameBook(Book book1,Book book2){
		if(null == book1 || null == book2){
			return false;
		}
		return book1.getAuthor().equalsIgnoreCase(book2.getAuthor()) &&
			   book1.getTitle().equalsIgnoreCase(book2.getTitle())	&&
			   0 == book1.getPrice().compareTo(book2.getPrice());
 	}
}
