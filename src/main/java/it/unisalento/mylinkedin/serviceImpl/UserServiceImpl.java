package it.unisalento.mylinkedin.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unisalento.mylinkedin.dao.MessageRepository;
import it.unisalento.mylinkedin.dao.UserRepository;
import it.unisalento.mylinkedin.domain.entity.Admin;
import it.unisalento.mylinkedin.domain.entity.Applicant;
import it.unisalento.mylinkedin.domain.entity.Message;
import it.unisalento.mylinkedin.domain.entity.Offeror;
import it.unisalento.mylinkedin.domain.entity.Regular;
import it.unisalento.mylinkedin.domain.entity.User;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.exceptions.UserAlreadyExist;
import it.unisalento.mylinkedin.exceptions.UserNotFoundException;
import it.unisalento.mylinkedin.iService.IUserService;

@Service
public class UserServiceImpl implements IUserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MessageRepository messageRepository;


	@Override
	public List<Regular> getAllDisabledRegularUser() {
		return userRepository.findByDisabled(true);
	}

	@Override
	@Transactional(rollbackOn = OperationFailedException.class)
	public Regular updateRegularUser(Regular regularUser) throws OperationFailedException {
		try {
			return userRepository.save(regularUser);
		} catch (Exception e) {
			throw new OperationFailedException();
		}
		
	}

	@Override
	public Regular findById(int id) throws UserNotFoundException{
		return (Regular) userRepository.findById(id).orElseThrow(()->new UserNotFoundException());
	}


	@Override
	public Offeror saveOfferor(Offeror offeror) throws OperationFailedException {
		try {
			return userRepository.save(offeror);
		} catch (Exception e) {
			throw new OperationFailedException();
			}
	}

	@Override
	public Applicant saveApplicant(Applicant applicant) throws OperationFailedException{
		try {
			return userRepository.save(applicant);
		} catch (Exception e) {
			throw new OperationFailedException();
			}
	}
	
	@Override
	@Transactional
	public User findByEmailAndPassword(String email, String password) throws UserNotFoundException{
		User user = userRepository.findByEmailAndPassword(email, password);
		if(user != null) {
			Applicant applicant = userRepository.findApplicantById(user.getId());
			if(applicant != null)
				return applicant;
			Offeror offeror = userRepository.findOfferorById(user.getId());
			if(offeror != null)
				return offeror;
			Admin admin= userRepository.findAdminById(user.getId());
			if(admin != null)
				return admin;
		}
		 throw new UserNotFoundException();
		
	}
	
	@Override
	@Transactional
	public List<Regular> findAllRegularEnabled(){
		List<User> userList = userRepository.findAll();
		List<Regular> regularList = new ArrayList<>();
		for (User user : userList) {
			if(user.getClass() != Admin.class ) {
				if (!((Regular) user).isDisabled())
					regularList.add((Regular) user);
			}
		}
		return regularList;
	}

	@Override
	public Regular save(Regular regular) throws OperationFailedException {
		try {
			return userRepository.save(regular);
		} catch (Exception e) {
			throw new OperationFailedException();
			}
	}

	@Override
	public List<Message> findAllMessages(int id) {
		return messageRepository.findBySendingUserIdOrReceivingUserId(id, id);
	}

	@Override
	public void findByEmail(String email) throws UserAlreadyExist {
		 if(userRepository.findByEmail(email) != null) {
			 throw new UserAlreadyExist();
		 }
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Message saveMessage(Message message) throws OperationFailedException {
		try {
			return messageRepository.save(message);
		} catch (Exception e) {
			throw new OperationFailedException();
			}
	}

	@Override
	public List<Message> findMessages(int id, int idUserChat) {
		List<Message>  messList = messageRepository.findBySendingUserIdAndReceivingUserId(id, idUserChat);
		messList.addAll(messageRepository.findBySendingUserIdAndReceivingUserId(idUserChat, id));
		return messList;
	}

	@Override
	public List<Offeror> findByIdCompanyAndVerified(int idCompany, boolean verified) {
		return userRepository.findByCompanyIdAndVerified(idCompany, verified);
	}

	@Override
	public User findUserById(int id) throws UserNotFoundException {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
	}
}
