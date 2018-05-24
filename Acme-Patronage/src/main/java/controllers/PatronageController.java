/*
 * AboutUsController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.PatronageService;
import services.UserService;
import domain.Actor;
import domain.Patronage;
import domain.User;

@Controller
@RequestMapping("/patronage")
public class PatronageController extends AbstractController {

	@Autowired
	private PatronageService	patronageService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private UserService			userService;


	// Constructors -----------------------------------------------------------

	public PatronageController() {
		super();
	}

	//Listing 

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Patronage> patronages = new ArrayList<Patronage>();
		patronages = this.patronageService.findAll();
		User principal = null;
		if (this.actorService.checkLogin()) {
			final Actor a = this.actorService.findByPrincipal();
			if (this.actorService.checkAuthority(a, "USER"))
				principal = this.userService.findByPrincipal();
		}

		result = new ModelAndView("patronage/list");
		result.addObject("principal", principal);
		result.addObject("patronages", patronages);
		result.addObject("requestURI", "patronage/list.do");

		return result;
	}

}
