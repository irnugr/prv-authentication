package id.co.microservice.prv_authentication.commons;

import java.util.UUID;

public class RequestIDGenerator {
	
	private static UUID LastUUID = UUID.randomUUID();
	
	/**
	 * method to generate unique id
	 * 
	 * @return unique id
	 */
	public static String getID() {
		
		UUID randomUUID = UUID.randomUUID();
		if (randomUUID.compareTo(LastUUID) == 0) {
			randomUUID = UUID.randomUUID();
		}
		LastUUID = randomUUID;
		
		return randomUUID.toString();
	}
	
	private RequestIDGenerator() {
		// won't be called
	}

}
