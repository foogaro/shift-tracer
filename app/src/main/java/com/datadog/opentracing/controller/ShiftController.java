package com.datadog.opentracing.controller;

import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
public class ShiftController {

	@Autowired
	private Tracer tracer;

	private final Logger logger = LoggerFactory.getLogger(ShiftController.class);

	@ResponseBody
	@RequestMapping(value ="/giveMeFive", method = RequestMethod.GET)
	public ResponseEntity<Integer>  giveMeFive () {
		Integer result = 5;
		try {
			tracer.activeSpan().setTag("GIVE_ME_FIVE", result);
		} catch (Exception e) {
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value ="/repeat/{this}", method = RequestMethod.GET)
	public ResponseEntity<String>  repeat (@PathVariable("this") String repeat) {
		String result = repeat;
		try {
			tracer.activeSpan().setTag("REPEAT", result);
		} catch (Exception e) {
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ExceptionHandler
	public ResponseEntity<Object> exception(Exception exception) throws Exception {
		logger.debug("ExceptionHandler - " + exception.toString());
		tracer.activeSpan().log(Collections.singletonMap("EXCEPTION", exception.toString()));
		throw exception;
	}

}
