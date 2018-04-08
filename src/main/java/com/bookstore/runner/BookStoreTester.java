/*
 * Project Name:BookStore
 * Author:Priyadharshini Viswanathan
 * Date Created : April 6,2018
 */
package com.bookstore.runner;

import java.util.Arrays;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bookstore.bean.Book;
import com.bookstore.service.BookList;
import com.bookstore.util.BookStoreUtil;

/**
 * The Class BookStoreTester.
 */
@Component
public class BookStoreTester {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(BookStoreTester.class);
	
	/** The book list impl. */
	@Autowired
	private BookList bookListImpl;
	
	/** The Constant BOOK. */
	private static final String BOOK ="book";
	
	/** The Constant QUANTITY. */
	private static final String QUANTITY ="quantity";
	
	/**
	 * Test book list utils.
	 *
	 * @param args the args
	 */
	public void testBookListUtils(String[] args){
		if(args[0].equalsIgnoreCase("list")){
			this.listBooks(args[1]);
		}else if(args[0].equalsIgnoreCase("add")){
			this.addBooks(args[1]);
		}else if(args[0].equalsIgnoreCase("buy")){
			this.buyBooks(args[1]);
		}
	}
	
	/**
	 * test List books.
	 *
	 * @param args the args
	 */
	private void listBooks(String args) {
		Book[] booksAvailable = bookListImpl.list(args);
    	logger.info("Available Books "+Arrays.asList(booksAvailable).toString());
	}
	
	/**
	 * test Add books.
	 *
	 * @param args the args
	 */
	private void addBooks(String args){
		try{
			Map<String,Object> spiltData = BookStoreUtil.convertStringToBean(args);
			Book book = (Book)spiltData.get(BOOK);
			if(bookListImpl.add(book, (Integer) spiltData.get(QUANTITY))){
				logger.info("Books added to the bookstore");
			}
		}catch (Exception e) {
			logger.error("Exception occured while reading input data ",e);
		}
		
	}
	
	/**
	 * test Buy books.
	 *
	 * @param args the args
	 */
	private void buyBooks(String args){
		try{
			String[] inputData = args.split("#");
			Book[] books = new Book[inputData.length];
			for(int count = 0;count < inputData.length;count ++){
				Map<String,Object> spiltData = BookStoreUtil.convertStringToBean(inputData[count]);
				books[count] = (Book)spiltData.get(BOOK);
			}
			int[] status = bookListImpl.buy(books);
			logger.info("status ",status);
		}catch (Exception e) {
			logger.error("Exception occured while reading input data ",e);
		}
	}
	
}
