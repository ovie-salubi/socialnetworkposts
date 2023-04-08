package com.akamai.homework.socialnetworkposts.service;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.akamai.homework.socialnetworkposts.exceptions.InvalidPostException;
import com.akamai.homework.socialnetworkposts.exceptions.PostNotFoundException;
import com.akamai.homework.socialnetworkposts.model.SocialNetworkPost;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SocialNetworkPostServiceTest {

	private static final long POST_ID_USED_FOR_SEARCH = 1L;
	private static final String UPDATED_CONTENT = "Updated Content";
	private static final long NON_EXISTENT_POST_ID = 0;

	@Autowired
	private SocialNetworkPostService service;

	@Test
	@Order(1)
	public void createPost() {

		SocialNetworkPost socialNetworkPost = createSocialNetworkPostInstance();

		SocialNetworkPost createdPost = service.createPost(socialNetworkPost);

		Assertions.assertTrue(createdPost.getId() > 0);
	}

	@Test
	@Order(2)
	public void findPost() {

		SocialNetworkPost socialNetworkPost = service.findPost(POST_ID_USED_FOR_SEARCH);

		Assertions.assertEquals(POST_ID_USED_FOR_SEARCH, socialNetworkPost.getId());

	}

	@Test
	@Order(3)
	public void updatePost_whenTargetAndRequestBody_AreSameReference() {

		SocialNetworkPost targePost = getNewPost();

		SocialNetworkPost postInRequestBody = getPostFromExistingReference(targePost);

		postInRequestBody.setContent(UPDATED_CONTENT);
		postInRequestBody.setAuthor("Black");
		postInRequestBody.setViewCount(4789);

		long targetPostId = targePost.getId();

		SocialNetworkPost updatedPost = service.updatePost(targetPostId, postInRequestBody);

		Assertions.assertEquals(targePost.getId(), updatedPost.getId());
		Assertions.assertEquals(postInRequestBody.getId(), updatedPost.getId());
		Assertions.assertEquals(postInRequestBody.getContent(), updatedPost.getContent());
		Assertions.assertEquals(postInRequestBody.getAuthor(), updatedPost.getAuthor());
		Assertions.assertEquals(postInRequestBody.getViewCount(), updatedPost.getViewCount());
	}

	@Test
	@Order(4)
	public void updatePost_whenTargetAndRequestBody_AreNotSameReference_thenUpdateTarget() {

		SocialNetworkPost postInRequestBody = getNewPost();

		postInRequestBody.setContent(UPDATED_CONTENT);
		postInRequestBody.setAuthor("Black");
		postInRequestBody.setViewCount(4789);

		SocialNetworkPost targePost = getNewPost();

		long targetPostId = targePost.getId();

		SocialNetworkPost updatedPost = service.updatePost(targetPostId, postInRequestBody);

		Assertions.assertEquals(targePost.getId(), updatedPost.getId());
		Assertions.assertNotEquals(postInRequestBody.getId(), updatedPost.getId());
		Assertions.assertEquals(postInRequestBody.getContent(), updatedPost.getContent());
		Assertions.assertEquals(postInRequestBody.getAuthor(), updatedPost.getAuthor());
		Assertions.assertEquals(postInRequestBody.getViewCount(), updatedPost.getViewCount());
	}

	@Test
	@Order(5)
	public void updatePost_whenTargetPostDoesNotExist_thenCreatePost() {

		SocialNetworkPost postInRequestBody = getNewPost();

		long targetPostId = NON_EXISTENT_POST_ID;

		SocialNetworkPost createdPost = service.updatePost(targetPostId, postInRequestBody.clone());

		Assertions.assertNotEquals(NON_EXISTENT_POST_ID, createdPost.getId());
		Assertions.assertNotEquals(postInRequestBody.getId(), createdPost.getId());
		Assertions.assertEquals(postInRequestBody.getAuthor(), createdPost.getAuthor());
		Assertions.assertEquals(postInRequestBody.getContent(), createdPost.getContent());
		Assertions.assertEquals(postInRequestBody.getViewCount(), createdPost.getViewCount());
	}

	@Test
	@Order(6)
	public void deletePost() {

		SocialNetworkPost postForDelete = service.findPost(POST_ID_USED_FOR_SEARCH);

		service.deletePost(postForDelete.getId());

		Assertions.assertThrows(PostNotFoundException.class, () -> service.findPost(POST_ID_USED_FOR_SEARCH));
	}

	@Test
	@Order(7)
	public void createPost_whenAuthor_orContent_is_not_specified_thenThrow_InvalidPostException() {

		SocialNetworkPost postWithNoAuthorAndContent = createSocialNetworkPostInstanceWithoutAuthorAndContent();

		Assertions.assertThrows(InvalidPostException.class,
				() -> service.updatePost(POST_ID_USED_FOR_SEARCH, postWithNoAuthorAndContent));
	}

	@Test
	@Order(8)
	public void findPost_whenPostId_doesnot_exist_thenThrow_PostNotFoundException() {

		Assertions.assertThrows(PostNotFoundException.class, () -> service.findPost(NON_EXISTENT_POST_ID));

	}

	@Test
	@Order(9)
	public void updatePost_whenPost_isNull_thenThrow_InvalidPostException() {

		SocialNetworkPost nullObject = null;

		Assertions.assertThrows(InvalidPostException.class,
				() -> service.updatePost(POST_ID_USED_FOR_SEARCH, nullObject));
	}

	@Test
	@Order(10)
	public void deletePost_whenPost_isNotFound_thenThrow_PostNotFoundException() {

		SocialNetworkPost nonExistentPost = createSocialNetworkPostInstanceWithNonExistentPostId();

		Assertions.assertThrows(PostNotFoundException.class, () -> service.deletePost(nonExistentPost.getId()));
	}

	private SocialNetworkPost createSocialNetworkPostInstance() {

		SocialNetworkPost post = new SocialNetworkPost();

		post.setAuthor("Conan Doyle");
		post.setContent("Content Description");
		post.setPostDate(new Date(System.currentTimeMillis()));
		post.setViewCount(100);

		return post;
	}

	private SocialNetworkPost createSocialNetworkPostInstanceWithoutAuthorAndContent() {

		return new SocialNetworkPost("  ", "  ", 0);
	}

	private SocialNetworkPost createSocialNetworkPostInstanceWithNonExistentPostId() {

		SocialNetworkPost post = new SocialNetworkPost("Conan Doyle", "Sample Content", 0);

		post.setId(NON_EXISTENT_POST_ID);

		return post;
	}

	private SocialNetworkPost getPostFromExistingReference(SocialNetworkPost socialNetworkPost) {

		return socialNetworkPost.clone();
	}

	private SocialNetworkPost getNewPost() {

		return service.createPost(createSocialNetworkPostInstance()).clone();
	}

}
