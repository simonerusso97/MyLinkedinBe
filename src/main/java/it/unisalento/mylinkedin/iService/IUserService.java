package it.unisalento.mylinkedin.iService;

import java.util.List;

import it.unisalento.mylinkedin.domain.entity.Message;
import it.unisalento.mylinkedin.domain.entity.Offeror;
import it.unisalento.mylinkedin.domain.entity.Regular;
import it.unisalento.mylinkedin.domain.entity.User;
import it.unisalento.mylinkedin.domain.relationship.RegularInterestedInPost;
import it.unisalento.mylinkedin.exceptions.UserNotFoundException;

public interface IUserService {

	User findByEmailAndPassword(String email, String pwd) throws UserNotFoundException;

	List<Regular> findRegularByDisabled(boolean disabled);

	Regular findById(int id) throws UserNotFoundException;

	Regular save(Regular regular);

	Regular findByEmail(String email);

	List<Offeror> findByCompanyIdAndVerified(int idCompany, boolean ver);

	List<RegularInterestedInPost> findInterestedPostByUserId(int userId);

	List<RegularInterestedInPost> updateInterestedList(List<RegularInterestedInPost> updatedList);

	void removeInterest(List<RegularInterestedInPost> toRemoveList);

	List<Message> findMessageByUserId(int idUser);

	User findUserById(int id) throws UserNotFoundException;

	List<User> findAllUser();

	Message saveMessage(Message message);

}
