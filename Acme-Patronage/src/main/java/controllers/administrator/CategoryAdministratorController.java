
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.forms.CategoryFormService;
import controllers.AbstractController;
import domain.Category;
import domain.forms.CategoryForm;

@Controller
@RequestMapping("/category/administrator")
public class CategoryAdministratorController extends AbstractController {

	//Services

	@Autowired
	private CategoryFormService	categoryFormService;

	@Autowired
	private CategoryService		categoryService;


	//Constructor

	public CategoryAdministratorController() {
		super();
	}

	//Creating 
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CategoryForm categoryForm;

		categoryForm = this.categoryFormService.create();

		result = this.createEditModelAndView(categoryForm);
		return result;

	}

	//Editing
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId) {

		Assert.notNull(categoryId);

		final ModelAndView result;
		final CategoryForm categoryForm;
		categoryForm = this.categoryFormService.create(categoryId);

		result = this.createEditModelAndView(categoryForm);

		return result;
	}
	//Saving 
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CategoryForm categoryForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(categoryForm);
		else
			try {
				if (categoryForm.getId() > 0)
					this.categoryFormService.saveFromEdit(categoryForm);

				else
					this.categoryFormService.saveFromCreate(categoryForm);
				result = new ModelAndView("redirect:/category/list.do");

			} catch (final Throwable oops) {
				String messageError = "category.commit.error";
				if (oops.getMessage().contains("message.error"))
					messageError = oops.getMessage();
				result = this.createEditModelAndView(categoryForm, messageError);

			}

		return result;
	}
	//Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int categoryId) {
		ModelAndView result;
		final Category category = this.categoryService.findOne(categoryId);
		try {
			this.categoryService.delete(category);
			result = new ModelAndView("redirect:/category/list.do");
		} catch (final Throwable oops) {
			String messageError = "category.delete.error";
			if (oops.getMessage().contains("message.error"))
				messageError = oops.getMessage();
			result = new ModelAndView("redirect:/category/list.do");
			result.addObject("messageError", messageError);
		}
		return result;
	}
	//Ancillary methods

	protected ModelAndView createEditModelAndView(final CategoryForm categoryForm) {
		ModelAndView result;

		result = this.createEditModelAndView(categoryForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CategoryForm categoryForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("category/administrator/edit");

		result.addObject("categoryForm", categoryForm);
		result.addObject("message", message);
		result.addObject("requestURI", "category/administrator/edit.do");
		return result;
	}
}
