/*
 * Project Name:BookStore
 * Author:Priyadharshini Viswanathan
 * Date Created : April 6,2018
 */
package com.bookstore.bean;

/**
 * The Enum BookQuantityStatus.
 */
public enum BookQuantityStatus {
	
	/** The ok. */
	OK(0), 
 /** The not in stock. */
 NOT_IN_STOCK(1), 
 /** The does not exist. */
 DOES_NOT_EXIST(2);
	
	/** The value. */
	private int value;

	/**
	 * Instantiates a new book quantity status.
	 *
	 * @param value the value
	 */
	private BookQuantityStatus(int value) {
		this.value = value;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
}
