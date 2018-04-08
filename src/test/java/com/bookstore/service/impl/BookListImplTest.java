package com.bookstore.service.impl;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bookstore.bean.Book;
import com.bookstore.service.BookList;
import com.bookstore.util.BookStoreUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:config/service-app-config.xml"})
public class BookListImplTest {
	@Autowired
	BookList bookListImpl;
	
	@Before
	public void loadInitialData(){
		BookStoreUtil.readFile();
	}
	
	@Test
	public void testList(){
		Book[] list = bookListImpl.list(null);
		Assert.assertNotNull(list);
		Assert.assertNotEquals(0, list.length);
	}
	
	@Test
	public void testAdd(){
		int quantity = 2;
		Book book =new Book();
		book.setAuthor("testAuthor");
		book.setTitle("testTitle");
		book.setPrice(new BigDecimal("789"));
		boolean status = bookListImpl.add(book, quantity);
		Assert.assertNotEquals(false, status);
	}
	
	@Test
	public void testBuy(){
		Book[] books = new Book[3];
		Book book =new Book();
		book.setAuthor("testAuthor");
		book.setTitle("testTitle");
		book.setPrice(new BigDecimal("789"));
		
		Book book1 =new Book();
		book1.setAuthor("Rich Bloke");
		book1.setTitle("Desired");
		book1.setPrice(new BigDecimal("564.50"));
		
		Book book2 =new Book();
		book2.setAuthor("First Author");
		book2.setTitle("Generic Title");
		book2.setPrice(new BigDecimal("185.50"));
		
		books[0] = book;
		books[1] = book1;
		books[2] = book2;
		
		int status[] = bookListImpl.buy(books);
		Assert.assertNotNull(status);
		Assert.assertNotEquals(0, books.length);
		Assert.assertNotEquals(2, books[0]);
		Assert.assertNotEquals(1, books[1]);
		Assert.assertNotEquals(0, books[2]);
	}
}
