/**
 * 
 */
package com.akamai.homework.socialnetworkposts.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akamai.homework.socialnetworkposts.exceptions.InvalidPostException;
import com.akamai.homework.socialnetworkposts.exceptions.PostNotFoundException;
import com.akamai.homework.socialnetworkposts.model.SocialNetworkPost;
import com.akamai.homework.socialnetworkposts.repository.SocialNetworkPostRepository;

/**
 * @author ovies
 *
 */
@Service
public class SocialNetworkPostService {
	
	@Autowired
	private SocialNetworkPostRepository repository;

	public SocialNetworkPost createPost(SocialNetworkPost socialNetworkPost) {
		
		validatePost(socialNetworkPost);
		
		return repository.save(socialNetworkPost);
	}

	public SocialNetworkPost updatePost(long targetPostId, SocialNetworkPost socialNetworkPost) {
		
		validatePost(socialNetworkPost);
		
		Optional<SocialNetworkPost> targetPost = repository.findById(targetPostId);
		
		if(targetPost.isEmpty()) {
			
			socialNetworkPost.setId(targetPostId);

			return repository.save(socialNetworkPost);
		}
		
		SocialNetworkPost postForUpdate = targetPost.get();
		
		postForUpdate.setAuthor(socialNetworkPost.getAuthor());
		postForUpdate.setContent(socialNetworkPost.getContent());
		postForUpdate.setViewCount(socialNetworkPost.getViewCount());
		
		return repository.save(postForUpdate);
	}

	public SocialNetworkPost findPost(long postId) {
		
		Optional<SocialNetworkPost> searchedPost = repository.findById(postId);
		
		if(searchedPost.isEmpty()) {
			throw new PostNotFoundException("Post Not Found");
		}
		
		return searchedPost.get();
	}

	public void deletePost(long postId) {
		
		SocialNetworkPost postForDelete = findPost(postId);
		
		repository.delete(postForDelete);
	}

	public List<SocialNetworkPost> findTopTenPostsByViewCount() {
		
		return repository.findTop10ByOrderByViewCountDesc();
	}

	private void validatePost(SocialNetworkPost socialNetworkPost) {
		
		if(socialNetworkPost == null || socialNetworkPost.getAuthor().trim().isEmpty()
				|| socialNetworkPost.getContent().trim().isEmpty()) {
			
			throw new InvalidPostException("Invalid Post. Author and/or Content are not specified");
			
		}
		
		if(socialNetworkPost.getPostDate() == null) {			
			socialNetworkPost.setPostDate(new Date(System.currentTimeMillis()));
		}
	}

}
