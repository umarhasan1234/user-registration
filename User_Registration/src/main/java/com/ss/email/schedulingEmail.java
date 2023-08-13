package com.ss.email;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ss.constant.ConstantsMessage;
import com.ss.entity.UserEntity;
import com.ss.exception.InternalException;
import com.ss.repository.UserRepository;


@Service
public class SchedulingEmail
{
	
		
	
	 @Autowired
     private EmailSend emailSend;
	
	
	@Autowired
	private UserRepository userRepository;
	
	
	private final Logger log = LoggerFactory.getLogger(SchedulingEmail.class);
	
	@Value("${EMAIL_BODY}")
    private	String body; 
	
	@Value("${EMAIL_FOODER}")
    private	String fooder; 
	
	@Scheduled(cron = "* * * */24 * *")
	public void send()
	{
		
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH,-15);
		java.util.Date time = c.getTime();
		java.sql.Date sqlDate = new java.sql.Date(time.getTime());
		Date date = Date.valueOf(new SimpleDateFormat(ConstantsMessage.DATE_FORMAT).format(sqlDate));
		
		try {
			
			List<UserEntity> userList = userRepository.findByPasswordUpdated(date);
			log.info("today password update remander send");
			
		for( UserEntity user :userList)
		{	
	
			emailSend.send(user.getEmail(),body+ " "+ user.getFirstName()+" " + user.getLastName()+" "+fooder);
			
		}	
	}
		catch (Exception e)
		{
			log.info("inside the scheduling email class called");
			log.error(e.getMessage());
		throw new InternalException(ConstantsMessage.INTERNAL_EXCEPTION_MESSAGE);
		}
}
}