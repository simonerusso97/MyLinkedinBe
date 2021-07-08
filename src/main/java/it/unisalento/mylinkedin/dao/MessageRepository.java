package it.unisalento.mylinkedin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.mylinkedin.domain.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{

	List<Message> findBySendingUserIdOrReceivingUserId(int idSending, int idReceiving);

	List<Message> findBySendingUserIdAndReceivingUserId(int id, int idUserChat);	
	
}
