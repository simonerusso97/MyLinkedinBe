package it.unisalento.mylinkedin.iService;

import java.util.List;

import it.unisalento.mylinkedin.domain.entity.Applicant;
import it.unisalento.mylinkedin.domain.entity.Message;
import it.unisalento.mylinkedin.domain.entity.Offeror;
import it.unisalento.mylinkedin.domain.entity.Regular;
import it.unisalento.mylinkedin.domain.entity.User;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.exceptions.UserAlreadyExist;
import it.unisalento.mylinkedin.exceptions.UserNotFoundException;

public interface IUserService {

	List<Regular> getAllDisabledRegularUser();

	Regular updateRegularUser(Regular regularUser) throws OperationFailedException;

	Regular findById(int id) throws UserNotFoundException;
	
	Offeror saveOfferor(Offeror offeror) throws OperationFailedException;

	Applicant saveApplicant(Applicant applicant) throws OperationFailedException;

	User findByEmailAndPassword(String email, String password) throws UserNotFoundException;

	List<Regular> findAllRegularEnabled();

	Regular save(Regular regular) throws OperationFailedException;

	List<Message> findAllMessages(int id);

	void findByEmail(String lowerCase) throws UserAlreadyExist;

	List<User> findAll();

	Message saveMessage(Message message) throws OperationFailedException;

	List<Message> findMessages(int id, int idUserChat);

	List<Offeror> findByIdCompanyAndVerified(int idCompany, boolean b);

	User findUserById(int id) throws UserNotFoundException;


}
