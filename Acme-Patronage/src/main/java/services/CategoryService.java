
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Administrator;
import domain.Category;
import domain.Project;

@Service
@Transactional
public class CategoryService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CategoryRepository		categoryRepository;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public CategoryService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Category create() {
		final Category result = new Category();
		final Collection<Project> projects = new ArrayList<Project>();
		result.setProjects(projects);
		return result;
	}
	// DO NOT MODIFY. ANY OTHER SAVE METHOD MUST BE NAMED DIFFERENT.
	public Category save(final Category category) {
		Assert.notNull(category, "message.error.category.null");
		Category result;
		final Administrator principal = this.administratorService.findByPrincipal();

		Assert.isTrue(category.getProjects().isEmpty(), "message.error.category.inUse");
		Assert.isTrue(this.actorService.checkAuthority(principal, "ADMIN"), "message.error.category.save.admin");
		result = this.categoryRepository.save(category);
		return result;
	}

	public Category saveFromCreate(final Category category) {
		Assert.notNull(category, "message.error.category.null");

		final Category result;
		final Collection<Category> categories = this.categoryRepository.findAll();
		Boolean check = true;
		for (final Category c : categories)
			if (category.getName().equals(c.getName()))
				check = false;
		Assert.isTrue(check, "message.error.category.nameAlreadyInDB");
		final Administrator principal = this.administratorService.findByPrincipal();

		Assert.isTrue(this.actorService.checkAuthority(principal, "ADMIN"), "message.error.category.create.admin");

		result = this.save(category);

		return result;
	}
	public Category saveFromEdit(final Category category) {
		Assert.notNull(category, "message.error.category.null");

		Category result;
		final Administrator principal = this.administratorService.findByPrincipal();
		final Collection<Category> categories = this.categoryRepository.findAll();
		Boolean check = true;
		categories.remove(category);
		for (final Category c : categories)
			if (category.getName().equals(c.getName()))
				check = false;
		Assert.isTrue(check, "message.error.category.nameAlreadyInDB");
		Assert.isTrue(this.actorService.checkAuthority(principal, "ADMIN"), "message.error.category.save.admin");
		Assert.isTrue(category.getProjects().isEmpty(), "message.error.category.inUse");
		result = this.save(category);
		return result;
	}
	public void delete(final Category category) {
		Assert.notNull(category, "message.error.category.null");

		final Administrator principal = this.administratorService.findByPrincipal();
		Assert.isTrue(category.getProjects().isEmpty(), "message.error.category.inUse");
		Assert.isTrue(this.actorService.checkAuthority(principal, "ADMIN"), "message.error.category.delete.admin");
		this.categoryRepository.delete(category);
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

	public void flush() {
		this.categoryRepository.flush();
	}
	// Other business methods -------------------------------------------------

}
