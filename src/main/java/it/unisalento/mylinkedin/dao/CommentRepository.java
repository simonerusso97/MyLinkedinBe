package it.unisalento.mylinkedin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.mylinkedin.domain.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	Comment findById(int id);
}