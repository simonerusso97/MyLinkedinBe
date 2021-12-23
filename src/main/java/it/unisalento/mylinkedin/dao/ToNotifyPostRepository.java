package it.unisalento.mylinkedin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.mylinkedin.domain.entity.Structure;
import it.unisalento.mylinkedin.domain.entity.ToNotifyPost;

@Repository
public interface ToNotifyPostRepository extends JpaRepository<ToNotifyPost, Integer>{

	
}
