package it.unisalento.mylinkedin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;



import it.unisalento.mylinkedin.domain.entity.Attached;
@Repository
public interface AttachedRepository extends JpaRepository<Attached, Integer> {

	List<Attached> findByPostIdAndType(int id, String type);

	List<Attached> findByPostId(int id);

}
