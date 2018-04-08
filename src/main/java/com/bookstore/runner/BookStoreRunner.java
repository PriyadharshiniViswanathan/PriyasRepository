/*
 * Project Name:BookStore
 * Author:Priyadharshini Viswanathan
 * Date Created : April 6,2018
 */
package com.bookstore.runner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The Class BookStoreRunner.
 */
public class BookStoreRunner{
	
	/** The application context. */
	private static ApplicationContext applicationContext; 
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args){
		applicationContext = new ClassPathXmlApplicationContext("config/service-app-config.xml");
		BookStoreTester bookStoreTester = (BookStoreTester) applicationContext.getBean("bookStoreTester");
		bookStoreTester.testBookListUtils(args);
    }
}
