
package services.forms;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import services.CategoryService;
import domain.Category;
import domain.forms.CategoryForm;

@Service
@Transactional
public class CategoryFormService {

	//Supporting services

	@Autowired
	private CategoryService	categoryService;


	//Constructor

	public CategoryFormService() {

		super();
	}

	public CategoryForm create() {
		CategoryForm result;

		result = new CategoryForm();

		return result;
	}

	public CategoryForm create(final int categoryId) {
		final Category c = this.categoryService.findOne(categoryId);

		final CategoryForm categoryForm = new CategoryForm();

		categoryForm.setId(c.getId());

		categoryForm.setName(c.getName());

		categoryForm.setDescription(c.getDescription());

		return categoryForm;
	}
	public Category saveFromCreate(final CategoryForm categoryForm) {

		Assert.notNull(categoryForm, "message.error.categoryForm.null");
		Assert.notNull(categoryForm.getName(), "message.error.categoryForm.name.null");
		Assert.notNull(categoryForm.getDescription(), "message.error.categoryForm.description.null");

		final Category c = this.categoryService.create();

		c.setId(categoryForm.getId());
		c.setName(categoryForm.getName());
		c.setDescription(categoryForm.getDescription());

		final Category categorynew = this.categoryService.saveFromCreate(c);

		return categorynew;
	}

	public Category saveFromEdit(final CategoryForm categoryForm) {
		Assert.notNull(categoryForm, "message.error.category.null");

		final Category category = this.categoryService.findOne(categoryForm.getId());

		category.setName(categoryForm.getName());
		category.setDescription(categoryForm.getDescription());
		category.setId(categoryForm.getId());

		final Category result = this.categoryService.saveFromEdit(category);

		return result;

	}
}
