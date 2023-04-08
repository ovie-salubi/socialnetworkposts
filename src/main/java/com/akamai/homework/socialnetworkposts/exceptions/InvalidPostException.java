/**
 * 
 */
package com.akamai.homework.socialnetworkposts.exceptions;

/**
 * @author ovies
 *
 */
public class InvalidPostException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public InvalidPostException() {
	}

	/**
	 * @param message
	 */
	public InvalidPostException(String message) {
		super(message);
	}

}
