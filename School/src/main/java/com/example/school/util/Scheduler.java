package com.example.school.util;

import java.sql.Date;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.school.repository.TokenRepository;

@Component
public class Scheduler {
//	cron = "0 * 9 * * ?" 86400000L
	@Autowired
	private TokenRepository tokenRepository;
	
	@Transactional
   @Scheduled(fixedDelay = 86400000L)
   public void cronJobSch() {
      System.out.println("Java cron job expression:: Suyog");
      tokenRepository.deleteExpired(new Timestamp(System.currentTimeMillis()));
   }
}
