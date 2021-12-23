package it.unisalento.mylinkedin;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.catalina.core.ApplicationContext;
import org.json.simple.JSONArray;
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

import ch.qos.logback.core.Context;
import it.unisalento.mylinkedin.domain.entity.Regular;
import it.unisalento.mylinkedin.domain.entity.ToNotifyPost;
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
		List<ToNotifyPost> toNotifyPostList;
    	List<Integer> idList;
    	List<ToNotifyPost> tempList;
    	Regular regular;
    	Boolean cont = true;
	    
    	while(true) {
    		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");  
	    	LocalDateTime now = LocalDateTime.now();
    		if(cont) {
	    		
    			  
					   //TODO: sistemare orario
		    	if(dtf.format(now).toString().equals("20:52")) {
		    		System.out.println("Notifica di riepilogo");
		    		//Dividere per id User e poi inviare la notifica
		 			toNotifyPostList = new ArrayList<>();
		 			toNotifyPostList.addAll(postService.findAllToNotify());
		 			
		 			idList = new ArrayList<>();

		 			//Salvo l'id di tutti gli utenti a cui deve essere inviata una notifica
		 			for(ToNotifyPost tnp : toNotifyPostList) {
		 				   if(!idList.contains(tnp.getIdUser())) {
		 					   idList.add(tnp.getIdUser());
		 				   }
		 			}
		 			
		 			//Per ogni id salvato invio una notifca per dire che è disponibile un riepilogo
		 			
		 			for(int id : idList) {
		 	 			   tempList = new ArrayList<>();

		 				   for(ToNotifyPost tnp : toNotifyPostList) {
		 					   if(tnp.getIdUser()==(id)) {
		 						   tempList.add(tnp);
		 					   }
		 				   }
		 				   
		  				   regular = userService.findById(id);
		  				   if(!tempList.isEmpty()) {
		  					   sendNotification(tempList, regular.getToken());
		  				   }
		 				   
		 			   }
		 			
		    	}
	    	}
    		cont = false;
	    	if(dtf.format(now).toString().equals("20:53")) {
	    		cont=true;
	    	}
	    }    
	}
	
	private static void sendNotification(List<ToNotifyPost> tnpList, String token) throws FirebaseMessagingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
        //Set pretty printing of json
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String arrayToJson = objectMapper.writeValueAsString(tnpList);


		Notification.Builder builder = Notification.builder();
		  MulticastMessage notMess = MulticastMessage.builder()
				.setNotification(builder.setTitle("Riepilogo")
						.setBody("Alcuni utenti hanno salvato i tuoi post, clicca per saperne di più")
						.build())
				.putData("notification_foregrgound", "true")
				.putData("tnp", arrayToJson)
		        .addToken(token)
		        .build();
		  System.out.println("Sending notification...");
		  BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(notMess);
		  System.out.println(response.getSuccessCount()+ " messages were sent successfully");
	}

}
