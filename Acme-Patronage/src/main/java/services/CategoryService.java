
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Category;

@Service
@Transactional
public class CategoryService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CategoryRepository	categoryRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public CategoryService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// TODO: Category - create
	public Category create() {
		final Category result = null;

		return result;
	}

	public Collection<Category> findAll() {
		Collection<Category> result = null;
		result = this.categoryRepository.findAll();
		return result;
	}

	public Category findOne(final int categoryId) {
		Category result = null;
		result = this.categoryRepository.findOne(categoryId);
		return result;
	}

	public Category save(final Category category) {
		Assert.notNull(category);
		Category result = null;
		result = this.categoryRepository.save(category);
		return result;
	}

	// TODO: Category - saveFromCreate
	public Category saveFromCreate() {
		final Category result = null;

		return result;
	}

	// TODO: Category - saveFromEdit
	public Category saveFromEdit() {
		final Category result = null;

		return result;
	}

	public void flush() {
		this.categoryRepository.flush();
	}

	// Other business methods -------------------------------------------------

}
