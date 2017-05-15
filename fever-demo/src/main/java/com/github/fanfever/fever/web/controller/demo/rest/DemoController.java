package com.github.fanfever.fever.web.controller.demo.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
@RestController
@RequestMapping("/")
public class DemoController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	String hello() {
		// HttpHeaders httpHeaders = new HttpHeaders();
		// httpHeaders.setLocation(ServletUriComponentsBuilder
		// .fromCurrentRequest().path("/{id}")
		// .buildAndExpand(result.getId()).toUri());
		// return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
		return "hello";
	}
}
