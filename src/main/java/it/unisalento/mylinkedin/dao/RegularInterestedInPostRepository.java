package it.unisalento.mylinkedin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.domain.relationship.RegularInterestedInPost;

@Repository
public interface RegularInterestedInPostRepository extends JpaRepository<RegularInterestedInPost, Integer> {

	List<RegularInterestedInPost> findByRegularId(int userId);
	

}
