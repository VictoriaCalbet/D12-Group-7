
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest {

	// The SUT (Service Under Test) -------------------------------------------

	@Autowired
	private CategoryService	categoryService;


	// Tests ------------------------------------------------------------------
	/**
	 * Acme-Patronage: Requirement 19 An actor who is authenticated as an administrator must be able to:
	 * 19.1. Manage the categories, thus create, edit and delete a category. If a
	 * category is in use by any project it cannot be edited, only deleted. This
	 * removes the category from the project.
	 * 
	 * Tests-Create a category
	 * Positive test1: An admin creates a category, with a name and a description
	 * Negative test2: An admin creates a category, with a null name and a description
	 * Negative test3: An admin creates a category, with a name and a null description
	 * Negative test4: An admin tries to create a category with a name already in use.
	 * Negative test5: A corporation tries to create a category
	 * Negative test6: A unauthenticated user tries to create a category
	 * 
	 */
	@Test
	public void testCreateCategoryDriver() {

		final Object testingData[][] = {

			/** userPrincipal, name,description,exception */
			{
				"admin", "Name of the category11", "Description", null
			}, {
				"admin", "Nameofcategory", null, ConstraintViolationException.class
			}, {
				"user2", "Name233", "Description2", ConstraintViolationException.class
			}, {
				"admin", "Art", "Description", ConstraintViolationException.class
			}, {
				"Corporation1", "Name3", "Description", ConstraintViolationException.class
			}, {
				null, "Name4", "Description", ConstraintViolationException.class

			}

		};

		for (int i = 0; i < testingData.length; i++) {
			this.setUp();
			this.testCreateCategoryTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
			this.tearDown();
		}
	}
	protected void testCreateCategoryTemplate(final String username, final String name, final String description, final Class<?> expectedException) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);

			final Category c = this.categoryService.create();

			c.setName(name);
			c.setDescription(description);

			this.categoryService.saveFromCreate(c);
			this.unauthenticate();
			this.categoryService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}

	/**
	 * Acme-Patronage: Requirement 19.1
	 * 
	 * Tests- Save a category
	 * 
	 * Positive test1: An admin modifies a category that does not belong to a project
	 * Negative test2: An admin modifies a category that belongs to a project
	 * Negative test3: An admin modifies a category,but the name is already in use
	 * Negative test4: A user tries to modify a category.
	 * Negative test5: A unauthenticated user tries to modify a category
	 * Negative test6: A corporation tries to modify a category
	 * 
	 */

	@Test
	public void testSaveCategoryDriver() {

		final Category c1 = this.categoryService.findOne(this.getEntityId("category1"));
		//c1 belongs to a project(not editable)

		final Category c2 = this.categoryService.create();
		c2.setDescription("Description2");
		c2.setName("Name2");

		final Object testingData[][] = {

			/** userPrincipal, category,title,description,exception */
			{
				"admin", c2, "Name2 of the testing category", "Description", null
			}, {
				"admin", c1, "Name1", "description", IllegalArgumentException.class
			}, {
				"admin", c2, "Art", "description", IllegalArgumentException.class
			}, {
				"user2", c2, "Name2", "Description", IllegalArgumentException.class
			}, {
				null, c2, "Name6", "Description", IllegalArgumentException.class
			}, {
				"Corporation1", c2, "Name3", "Description", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++) {
			this.setUp();
			this.testSaveCategoryTemplate((String) testingData[i][0], (Category) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
			this.tearDown();
		}
	}
	protected void testSaveCategoryTemplate(final String username, final Category category, final String name, final String description, final Class<?> expectedException) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);

			category.setName(name);
			category.setDescription(description);

			this.categoryService.saveFromEdit(category);
			this.unauthenticate();
			this.categoryService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}

	/**
	 * Acme-Patronage: Requirement 19.1
	 * 
	 * Tests- Delete a category
	 * 
	 * Positive test1: An admin deletes a category that does not belong to a project
	 * Negative test2: An admin deletes a category that belongs to a project
	 * Negative test3: An admin creates a category, with a name already in use
	 * Negative test4: A user tries to delete a category.
	 * Negative test5: A unauthenticated user tries to delete a category
	 * Negative test6: A corporation tries to delete a category
	 * 
	 */
	@Test
	public void testDeleteCategoryDriver() {

		final Category c1 = this.categoryService.findOne(this.getEntityId("category1"));
		//c1 belongs to a project(not editable nor deletable)
		final Category c2 = this.categoryService.create();
		c2.setDescription("Description2");
		c2.setName("Name2");

		final Object testingData[][] = {

			/** userPrincipal, category,exception */
			{
				"admin", c2, null
			}, {
				"admin", c1, IllegalArgumentException.class
			}, {
				"user2", c2, IllegalArgumentException.class
			}, {
				null, c2, IllegalArgumentException.class
			}, {
				"Corporation1", c2, IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++) {
			this.setUp();
			this.testDeleteCategoryTemplate((String) testingData[i][0], (Category) testingData[i][1], (Class<?>) testingData[i][2]);
			this.tearDown();
		}
	}
	protected void testDeleteCategoryTemplate(final String username, final Category category, final Class<?> expectedException) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);

			this.categoryService.delete(category);
			this.unauthenticate();
			this.categoryService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}
}
