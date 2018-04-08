/*
 * Project Name:BookStore
 * Author:Priyadharshini Viswanathan
 * Date Created : April 6,2018
 */
package com.bookstore.service;

import com.bookstore.bean.Book;

/**
 * The Interface BookList.
 */
public interface BookList {
	
	/**
	 * List books based on search criteria.
	 *
	 * @param searchString the search string
	 * @return the book[]
	 */
	public Book[] list(String searchString);
	
	/**
	 * Adds the book to the bookstore
	 *
	 * @param book the book
	 * @param quantity the quantity
	 * @return true, if successful
	 */
	public boolean add(Book book, int quantity);
	
	/**
	 * check the status of the books in bookstore
	 *
	 * @param books the books
	 * @return the int[]
	 */
	public int[] buy(Book... books);
}
