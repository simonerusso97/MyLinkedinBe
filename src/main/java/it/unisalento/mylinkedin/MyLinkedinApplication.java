package it.unisalento.mylinkedin;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;

import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.domain.entity.Regular;
import it.unisalento.mylinkedin.domain.relationship.PostRequireSkill;
import it.unisalento.mylinkedin.domain.relationship.RegularInterestedInPost;
import it.unisalento.mylinkedin.dto.JsonDocumentDTO;
import it.unisalento.mylinkedin.dto.PostDTO;
import it.unisalento.mylinkedin.dto.RegularDTO;
import it.unisalento.mylinkedin.dto.SkillDTO;
import it.unisalento.mylinkedin.dto.ToNotifyPostDTO;
import it.unisalento.mylinkedin.exceptions.UserNotFoundException;
import it.unisalento.mylinkedin.iService.IPostService;
import it.unisalento.mylinkedin.iService.IUserService;

@SpringBootApplication
public class MyLinkedinApplication implements ApplicationRunner{

	@Autowired
	IPostService postService;

	@Autowired
	IUserService userService;

	public static void main(String[] args) throws IOException, UserNotFoundException, FirebaseMessagingException {
		SpringApplication.run(MyLinkedinApplication.class, args);

	}


	@Override
	public void run(ApplicationArguments args) throws UserNotFoundException, FirebaseMessagingException, Exception {
		System.out.println("run method");
		FileInputStream serviceAccount = new FileInputStream("./mylinked-5bda1-firebase-adminsdk-8zex4-d92186d152.json");
		FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setProjectId("mylinked-5bda1")
				.build();
		FirebaseApp.initializeApp(options);
		List<RegularInterestedInPost> toNotifyPostList;
		List<Regular> userList;
		List<ToNotifyPostDTO> notificationList;
		List<Post> postList;
		Regular regular;
		boolean cont = true;

		while(true) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
			LocalDateTime now = LocalDateTime.now();
			if(cont) {


				//TODO: sistemare orario
				if(dtf.format(now).toString().equals("17:45")) {
					System.out.println("Notifica di riepilogo");
					//Dividere per id User e poi inviare la notifica
					toNotifyPostList = new ArrayList<>();
					toNotifyPostList.addAll(postService.finAllInterestedNotNotified());



					userList = new ArrayList<>();
					postList = new ArrayList<>();

					//Salvo l'id di tutti gli utenti a cui deve essere inviata una notifica
					for(RegularInterestedInPost tnp : toNotifyPostList) {
						if(!userList.contains(tnp.getPost().getCreatedBy())) {
							userList.add(tnp.getPost().getCreatedBy());
						}
						
					}
					
					for(RegularInterestedInPost tnp : toNotifyPostList) {
						if(!postList.contains(tnp.getPost())) {
							postList.add(tnp.getPost());
						}
					}
					
					
					//Per ogni id salvato invio una notifca per dire che è disponibile un riepilogo

					ToNotifyPostDTO tnpDTO;
					RegularDTO regularDTO;
					PostDTO postDTO;


					for(Regular user : userList) {
						notificationList = new ArrayList<>();

						for(Post post : postList) {
							if(post.getCreatedBy().getId()==(user.getId())) {
								
								tnpDTO = new ToNotifyPostDTO();
								
								postDTO = new PostDTO();
								postDTO.setHide(post.isHide());
								postDTO.setId(post.getId());
								postDTO.setName(post.getName());
								postDTO.setPubblicationDate(post.getPubblicationDate());

								JSONParser parser = new JSONParser();
								Reader reader = new FileReader(post.getJsonDocument().getName());
								JSONObject jsonObject = (JSONObject) parser.parse(reader);

								Set<String> keys = jsonObject.keySet();

								List<JsonDocumentDTO> jsonDocumentDTOList = new ArrayList<>();
								for (String key : keys) {
									JsonDocumentDTO jsonDocumentDTO = new JsonDocumentDTO();
									jsonDocumentDTO.setNameAttribute(key);
									jsonDocumentDTO.setValue((String) jsonObject.get(key));

									jsonDocumentDTOList.add(jsonDocumentDTO);
								}

								postDTO.setJsonDocument(jsonDocumentDTOList);
								
								tnpDTO.setPost(postDTO);
								notificationList.add(tnpDTO);
							}
						}
						
						for(ToNotifyPostDTO tnp: notificationList) {
							for(RegularInterestedInPost riipToNotify : toNotifyPostList) {
								if(riipToNotify.getPost().getId()==(tnp.getPost().getId())) {
									regularDTO = new RegularDTO();
									regularDTO.setName(riipToNotify.getRegular().getName());
									regularDTO.setSurname(riipToNotify.getRegular().getSurname());
									regularDTO.setAddress(riipToNotify.getRegular().getAddress());
									regularDTO.setBanned(riipToNotify.getRegular().isBanned());
									regularDTO.setBirthDate(riipToNotify.getRegular().getBirthDate());
									regularDTO.setDegree(riipToNotify.getRegular().getDegree());
									regularDTO.setDisabled(riipToNotify.getRegular().isDisabled());
									regularDTO.setEmail(riipToNotify.getRegular().getEmail());
									regularDTO.setId(riipToNotify.getRegular().getId());
									tnp.getUsers().add(regularDTO);
								}
							}
						}

						regular = userService.findById(user.getId());
						if(!notificationList.isEmpty()) {
							sendNotification(notificationList, regular.getToken());
						}

					}
					//Aggiorno il db per indicare che sono stati notificati
					for(RegularInterestedInPost tnp:  toNotifyPostList) {
						tnp.setNotified(true);
					}
					cont = false;

					//postService.updateInterestedPost(toNotifyPostList);

				}
			}
			if(dtf.format(now).toString().equals("13:39")) {
				cont=true;
			}
		}
	}

	private static void sendNotification(List<ToNotifyPostDTO> tnpList, String token) throws FirebaseMessagingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = objectMapper.writeValueAsString(tnpList);


		Notification.Builder builder = Notification.builder();
		MulticastMessage notMess = MulticastMessage.builder()
				.setNotification(builder.setTitle("Riepilogo")
						.setBody("Alcuni utenti hanno salvato i tuoi post, clicca per saperne di più")
						.build())
				.putData("notification_foreground", "true")
				.putData("tnp", arrayToJson)
				.addToken(token)
				.build();
		System.out.println("Sending notification...");
		BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(notMess);
		System.out.println(response.getSuccessCount()+ " messages were sent successfully");
	}

}
