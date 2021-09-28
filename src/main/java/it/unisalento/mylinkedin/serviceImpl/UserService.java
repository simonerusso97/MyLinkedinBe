package it.unisalento.mylinkedin.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unisalento.mylinkedin.dao.RegularInterestedInPostRepository;
import it.unisalento.mylinkedin.dao.UserRepository;
import it.unisalento.mylinkedin.domain.entity.Admin;
import it.unisalento.mylinkedin.domain.entity.Applicant;
import it.unisalento.mylinkedin.domain.entity.Offeror;
import it.unisalento.mylinkedin.domain.entity.Regular;
import it.unisalento.mylinkedin.domain.entity.User;
import it.unisalento.mylinkedin.domain.relationship.RegularInterestedInPost;
import it.unisalento.mylinkedin.exceptions.UserNotFoundException;
import it.unisalento.mylinkedin.iService.IUserService;

@Service
public class UserService implements IUserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RegularInterestedInPostRepository riipRepo;

	@Override
	public User findByEmailAndPassword(String email, String pwd) throws UserNotFoundException {
		User user = userRepo.findByEmailAndPassword(email, pwd);
		
		if(user != null) {
			Applicant applicant = userRepo.findApplicantById(user.getId());
			if(applicant != null)
				return applicant;
			Offeror offeror = userRepo.findOfferorById(user.getId());
			if(offeror != null)
				return offeror;
			Admin admin= userRepo.findAdminById(user.getId());
			if(admin != null)
				return admin;
		}
		 throw new UserNotFoundException();
	}

	@Override
	public List<Regular> findRegularByDisabled(boolean disabled) {
		try {
			return userRepo.findByDisabled(true);
		}catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Regular findById(int id) throws UserNotFoundException {
		return (Regular) userRepo.findById(id).orElseThrow(() -> new UserNotFoundException());
	}

	@Override
	public void save(Regular regular) {
		try {
			userRepo.save(regular);
		}catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void findByEmail(String email) {
		try {
			userRepo.findByEmail(email);
		}catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	public List<Offeror> findByCompanyIdAndVerified(int idCompany, boolean ver) {
		try {
			return userRepo.findByCompanyIdAndVerified(idCompany, ver);
		}catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<RegularInterestedInPost> findInterestedPostByUserId(int userId) {
		try {
			return riipRepo.findByRegularId(userId);
		} catch (Exception e) {
			throw e;
		}		
	}

	@Override
	public void updateInterestedList(List<RegularInterestedInPost> updatedList) {
		try {
			riipRepo.saveAll(updatedList);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void removeInterest(List<RegularInterestedInPost> toRemoveList) {
		try {
			riipRepo.deleteAll(toRemoveList);
		} catch (Exception e) {
			throw e;
		}		
	}

	
}
