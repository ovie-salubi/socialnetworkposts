/**
 * 
 */
package com.akamai.homework.socialnetworkposts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akamai.homework.socialnetworkposts.model.SocialNetworkPost;

/**
 * @author ovies
 *
 */
@Repository
public interface SocialNetworkPostRepository extends JpaRepository<SocialNetworkPost, Long> {

	List<SocialNetworkPost> findTop10ByOrderByViewCountDesc();

}
