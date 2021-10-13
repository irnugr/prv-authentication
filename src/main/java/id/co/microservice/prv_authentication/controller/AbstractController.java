package id.co.microservice.prv_authentication.controller;

import java.sql.Timestamp;

import id.co.microservice.commons.response.ResponseMessage;
import id.co.microservice.prv_authentication.commons.RequestIDGenerator;

public class AbstractController {
	
	protected <T> ResponseMessage<T> buildResponse(final String requestIdWeb, final T result,
			int httpStatus, String statusCode, String desc) {
		final String requestId = (requestIdWeb == null) ? RequestIDGenerator.getID() : requestIdWeb;
		final ResponseMessage<T> msg = new ResponseMessage<>(requestId, new Timestamp(System.currentTimeMillis()));
		msg.buildResponseMessage(result, httpStatus, statusCode, desc);
		return msg;
	}

}
