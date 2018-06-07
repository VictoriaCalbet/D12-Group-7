
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.UserAccount;
import utilities.AbstractTest;
import domain.User;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private UserService	userService;


	// Tests ------------------------------------------------------------------

	// The following are fictitious test cases that are intended to check that 
	// JUnit works well in this project.  Just righ-click this class and run 
	// it using JUnit.

	/**
	 * Acme-Patronage: Req 16.1
	 * 
	 * An actor who is not authenticated must be able to:
	 * Register to the system as a user.
	 * 
	 * Positive test1: Correct registration
	 * Negative test2: A user tries to register with a used username
	 * Negative test3: A user tries to register with a invalid email
	 */
	@Test
	public void testSaveFromCreateUser() {
		// User: Full Name, email, bio, telephone, username, password, expected exception.
		final Object[][] testingData = {
			{
				"testUserFullName1", "admin@admin.com", "testBio", "telephone", "testUser1", "testUser1", null
			}, {
				"testUserFullName2", "admin@admin.com", "testBio", "telephone", "admin", "testUser2", IllegalArgumentException.class
			}, {
				"testUserFullName3", "admin", "testBio", "telephone", "testUser3", "testUser3", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testSaveFromCreateCorporationTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	}

	protected void testSaveFromCreateCorporationTemplate(final String name, final String email, final String bio, final String telephone, final String username, final String password, final Class<?> expectedException) {

		Class<?> caught = null;

		try {
			User result;
			UserAccount userAccount;

			result = this.userService.create();

			result.setFullName(name);
			result.setEmail(email);
			result.setBio(bio);
			result.setTelephone(telephone);

			userAccount = result.getUserAccount();
			userAccount.setUsername(username);
			userAccount.setPassword(password);
			result.setUserAccount(userAccount);

			this.userService.saveFromCreate(result);
			this.userService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);

	}
}
