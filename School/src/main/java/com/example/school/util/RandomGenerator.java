package com.example.school.util;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomGenerator {
		public static int getRandomInt() {
			SecureRandom secureRandom= new SecureRandom();	
				return secureRandom.nextInt();
			}
		public static String getRandomString(int size) {
			SecureRandom secureRandom= new SecureRandom();	
			byte[] bytes=new byte[size];
			secureRandom.nextBytes(bytes);
				String randomString=new String(bytes);
				randomString=Base64.getEncoder().encodeToString(bytes).substring(0,size);
				return randomString;
			}
}
