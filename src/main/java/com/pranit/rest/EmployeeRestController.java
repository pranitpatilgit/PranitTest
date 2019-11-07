package com.pranit.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeRestController.class);

	@RequestMapping(method = RequestMethod.GET, value = "/{id}", produces="application/json")
	public @ResponseBody String searchEmployee(@PathVariable String id){
		logger.debug("Searching employee with id - {}.", id);
		return "{ employeeId : "+id+"\n name : 'Pranit'}";
	}
}
