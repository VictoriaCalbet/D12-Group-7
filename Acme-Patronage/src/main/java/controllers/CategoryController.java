
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import domain.Category;

@Controller
@RequestMapping("/category")
public class CategoryController extends AbstractController {

	//Services

	@Autowired
	private CategoryService	categoryService;


	//Constructor

	public CategoryController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Category> categories = new ArrayList<Category>();

		categories = this.categoryService.findAll();

		result = new ModelAndView("category/list");//tiles
		result.addObject("categories", categories);
		result.addObject("requestURI", "category/list.do");//view

		return result;

	}

}
