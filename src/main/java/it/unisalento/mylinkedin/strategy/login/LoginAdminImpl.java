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
		
		List<Message> messageList = userService.findAllMessages(admin.getId());
		List<MessageDTO> messageDTOList = new ArrayList<>();
		for (Message message : messageList) {
			MessageDTO messageDTO = new MessageDTO();
			messageDTO.setId(message.getId());
			messageDTO.setDate(message.getDate());
            messageDTO.setText(message.getText());
			UserDTO userDTO = new UserDTO();
			userDTO.setId(message.getReceivingUser().getId());
			userDTO.setName(message.getReceivingUser().getName());
			userDTO.setSurname(message.getReceivingUser().getSurname());
			userDTO.setEmail(message.getReceivingUser().getEmail());
			messageDTO.setReceivingUser(userDTO);
			userDTO = new UserDTO();
			userDTO.setId(message.getSendingUser().getId());
			userDTO.setName(message.getSendingUser().getName());
			userDTO.setSurname(message.getSendingUser().getSurname());
			userDTO.setEmail(message.getSendingUser().getEmail());
			messageDTO.setSendingUser(userDTO);
			
			messageDTOList.add(messageDTO);
		}
		
		adminDTO.setMessageList(messageDTOList);
		
		return adminDTO;		
	}

}
