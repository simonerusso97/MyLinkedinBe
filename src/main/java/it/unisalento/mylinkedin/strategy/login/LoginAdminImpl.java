package it.unisalento.mylinkedin.strategy.login;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.unisalento.mylinkedin.domain.entity.Admin;
import it.unisalento.mylinkedin.domain.entity.Message;
import it.unisalento.mylinkedin.domain.entity.User;
import it.unisalento.mylinkedin.dto.AdminDTO;
import it.unisalento.mylinkedin.dto.MessageDTO;
import it.unisalento.mylinkedin.dto.UserDTO;
import it.unisalento.mylinkedin.iService.IUserService;

public class LoginAdminImpl implements LoginStrategy{
	

	@Override
	public UserDTO login(User user, IUserService userService) {
		AdminDTO adminDTO = new AdminDTO();
		Admin admin = (Admin) user;
		adminDTO.setId(admin.getId());
		adminDTO.setName(admin.getName());
		adminDTO.setSurname(admin.getSurname());
		adminDTO.setEmail(admin.getEmail());
		adminDTO.setBirthDate(admin.getBirthDate());
		adminDTO.setPassword(admin.getPassword());
				
		return adminDTO;		
	}

}
