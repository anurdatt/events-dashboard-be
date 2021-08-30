package com.tcsonline.eventsdashboard.controllers.controlleradvices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tcsonline.eventsdashboard.exceptions.user.InvalidPasswordException;
import com.tcsonline.eventsdashboard.exceptions.user.UserNotFoundException;

@ControllerAdvice("com.tcsonline.eventsdashboard.controllers")
public class UserExceptionController {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> exception(UserNotFoundException e) {
		return new ResponseEntity<Object>("User not found!", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<Object> exception(InvalidPasswordException e) {
		return new ResponseEntity<Object>("Invalid password!", HttpStatus.FORBIDDEN);
	}
}
