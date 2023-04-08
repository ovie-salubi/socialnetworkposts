/**
 * 
 */
package com.akamai.homework.socialnetworkposts.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.akamai.homework.socialnetworkposts.model.SocialNetworkPost;
import com.akamai.homework.socialnetworkposts.service.SocialNetworkPostService;

/**
 * @author ovies
 *
 */
@RestController
@RequestMapping("/posts")
public class SocialNetworkPostController {

	@Autowired
	private SocialNetworkPostService service;

	@PostMapping
	public ResponseEntity<Void> createPost(@RequestBody SocialNetworkPost socialNetworkPost) {

		SocialNetworkPost post = service.createPost(socialNetworkPost);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();

		return ResponseEntity.created(uri).build();

	}

	@PutMapping("/{postId}")
	public ResponseEntity<SocialNetworkPost> updatePost(@PathVariable long postId,
			@RequestBody SocialNetworkPost socialNetworkPost) {
		
		SocialNetworkPost post = service.updatePost(postId, socialNetworkPost);

		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@DeleteMapping("/{postId}")
	public ResponseEntity<Void> deleteTodo(@PathVariable long postId) {

		service.deletePost(postId);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{postId}")
	public ResponseEntity<SocialNetworkPost> findPost(@PathVariable long postId) {

		SocialNetworkPost post = service.findPost(postId);

		return ResponseEntity.ok(post);
	}

	@GetMapping("/highestviews")
	public List<SocialNetworkPost> findTopTenPostsByViewCount() {

		return service.findTopTenPostsByViewCount();
	}

}
