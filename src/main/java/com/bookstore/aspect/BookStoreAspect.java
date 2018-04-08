/*
 * Project Name:BookStore
 * Author:Priyadharshini Viswanathan
 * Date Created : April 6,2018
 */
package com.bookstore.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bookstore.util.BookStoreUtil;

/**
 * The Class BookStoreAspect.
 */
@Component
@Aspect
public class BookStoreAspect {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(BookStoreAspect.class);
	
	/**
	 * Load initial data.
	 */
	@Before("execution(* com.bookstore.runner.BookStoreTester.testBookListUtils(..))")
	public void loadInitialData(){
		logger.info("loadInitialData in BookStoreAspect start");
		BookStoreUtil.readFile();
		logger.info("loadInitialData in BookStoreAspect end");
	}
	
}	

