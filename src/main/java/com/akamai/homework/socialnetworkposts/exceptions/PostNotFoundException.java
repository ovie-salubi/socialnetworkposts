/**
 * 
 */
package com.akamai.homework.socialnetworkposts.exceptions;

/**
 * @author ovies
 *
 */
public class PostNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public PostNotFoundException() {
	}

	/**
	 * @param message
	 */
	public PostNotFoundException(String message) {
		super(message);
	}

}
