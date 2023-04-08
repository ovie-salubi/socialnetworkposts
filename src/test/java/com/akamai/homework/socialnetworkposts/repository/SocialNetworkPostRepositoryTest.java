/**
 * 
 */
package com.akamai.homework.socialnetworkposts.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.akamai.homework.socialnetworkposts.model.SocialNetworkPost;

/**
 * @author ovies
 *
 */
@DataJpaTest
public class SocialNetworkPostRepositoryTest {

	private static final int HIGHEST_NUMBER_OF_VIEWS_WITHIN_TOP_TEN = 100;
	private static final int LOWEST_NUMBER_OF_VIEWS_WITHIN_TOP_TEN = 91;
	private static final int TOTAL_NUMBER_OF_POSTS_TO_CREATE = 30;
	private static final int EXPECTED_RESULTS_COUNT_FOR_TOP_TEN_VIEWS = 10;

	@Autowired
	private SocialNetworkPostRepository repository;

	@BeforeEach
	void doBeforeTest() {

		repository.deleteAll();

		List<SocialNetworkPost> posts = createMultipleSocialNetworkPosts(TOTAL_NUMBER_OF_POSTS_TO_CREATE,
				HIGHEST_NUMBER_OF_VIEWS_WITHIN_TOP_TEN);

		repository.saveAll(posts);
	}

	@Test
	public void retrievePostWithTopTenViewCounts() {

		List<SocialNetworkPost> topTenViewCountList = repository.findTop10ByOrderByViewCountDesc();

		Assertions.assertEquals(EXPECTED_RESULTS_COUNT_FOR_TOP_TEN_VIEWS, topTenViewCountList.size());

		int topTenTopIndex = 0;
		SocialNetworkPost postWithHighestViewsWithinTopTen = topTenViewCountList.get(topTenTopIndex);

		Assertions.assertEquals(HIGHEST_NUMBER_OF_VIEWS_WITHIN_TOP_TEN,
				postWithHighestViewsWithinTopTen.getViewCount());

		int topTenBottomIndex = topTenViewCountList.size() - 1;
		SocialNetworkPost postWithLowestViewsWithinTopTen = topTenViewCountList.get(topTenBottomIndex);

		Assertions.assertEquals(LOWEST_NUMBER_OF_VIEWS_WITHIN_TOP_TEN, postWithLowestViewsWithinTopTen.getViewCount());

	}

	private List<SocialNetworkPost> createMultipleSocialNetworkPosts(int numberOfInstances, long maximumViewCount) {

		List<SocialNetworkPost> postList = new ArrayList<>();

		SocialNetworkPost socialNetworkPost = null;

		for (int i = 0; i < numberOfInstances; i++) {
			socialNetworkPost = new SocialNetworkPost();

			socialNetworkPost.setAuthor("John Doe");
			socialNetworkPost.setContent("Content Description");
			socialNetworkPost.setPostDate(new Date(System.currentTimeMillis()));
			socialNetworkPost.setViewCount(maximumViewCount - i);

			postList.add(socialNetworkPost);

		}

		return postList;
	}

}
