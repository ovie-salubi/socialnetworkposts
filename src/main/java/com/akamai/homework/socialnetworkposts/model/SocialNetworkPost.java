/**
 * 
 */
package com.akamai.homework.socialnetworkposts.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author ovies
 *
 */
@Entity
@Table(name = "social_network_posts")
public class SocialNetworkPost implements Cloneable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "post_date")
	private Date postDate;

	@Column(name = "author")
	private String author;

	@Column(name = "content")
	private String content;

	@Column(name = "view_count")
	private long viewCount;

	/**
	 * 
	 */
	public SocialNetworkPost() {
		super();
	}

	/**
	 * @param postDate
	 * @param author
	 * @param content
	 * @param viewCount
	 */
	public SocialNetworkPost(String author, String content, long viewCount) {
		super();
		this.author = author;
		this.content = content;
		this.viewCount = viewCount;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the postDate
	 */
	public Date getPostDate() {
		return postDate;
	}

	/**
	 * @param postDate the postDate to set
	 */
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		if (author == null) {
			return "";
		}
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		if (content == null) {
			return "";
		}
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the viewCount
	 */
	public long getViewCount() {
		return viewCount;
	}

	/**
	 * @param viewCount the viewCount to set
	 */
	public void setViewCount(long viewCount) {
		this.viewCount = viewCount;
	}

	@Override
	public String toString() {
		return String.format("SocialNetworkPost [id=%s, postDate=%s, author=%s, content=%s, viewCount=%s]", id,
				postDate, author, content, viewCount);
	}
	
	@Override
	public SocialNetworkPost clone() {
		// TODO Auto-generated method stub
		try {
			return (SocialNetworkPost) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
