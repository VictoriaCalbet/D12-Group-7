
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Patronage;
import domain.Project;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PatronageServiceTest extends AbstractTest {

	// The SUT (Service Under Test) -------------------------------------------

	@Autowired
	private PatronageService	patronageService;

	@Autowired
	private ProjectService		projectService;


	// Tests ------------------------------------------------------------------
	/**
	 * Acme-Patronage: 4. Users can patronage projects. Every fund must store the amount of money, the
	 * date of the fund and a valid credit card with an expiration date after the project's
	 * due date. If the project is cancelled, these will be cancelled too.
	 * 
	 * Tests-Patronage a project
	 * Positive test1: A user patronize a project
	 * Negative test2: A user tries to patronize a project, but it has been cancelled
	 * Negative test3: A user tries to patronize a project, but the creditCard expirationDate is before the project's dueDate
	 * Negative test4: A user patronize a project, but he is the creator of the project
	 * Negative test5: A user patronize a project, but the amount is lower than minimum amount
	 * Negative test6: A user patronize a project, but it's draft
	 * Negative test7: A user patronize a project, but the credit card is invalid
	 * Negative test8: An admin tries to patronize a project.
	 * Negative test9: A corporation tries to patronize a project
	 * Negative test10: A unauthenticated user tries to patronize a project
	 * 
	 */
	@Test
	public void testPatronizeProjectDriver() {
		final Project p1 = this.projectService.findOne(this.getEntityId("project1"));
		final Project p4 = this.projectService.findOne(this.getEntityId("project4"));
		final Project p5 = this.projectService.findOne(this.getEntityId("project5"));

		//p1 belongs to user1, minimumAmount is 5.0,economicGoal = 5050. Due Date = 02/12/2019
		//p4 is cancelled
		final Object testingData[][] = {

			/** userPrincipal, project, amount, brandName,holderName, cardNumber, card ExpirationMonth, card ExpirationYear, cvv, exception */
			{
				"user2", p1, 50.0, "VISA", "JUAN GAMEZ", "4245342397520134", 2, 2021, 961, null
			}, {
				"user2", p4, 50.0, "VISA", "JUAN GAMEZ", "4123052576787753", 11, 2023, 169, IllegalArgumentException.class
			}, {
				"user2", p1, 50.0, "VISA", "JUAN GAMEZ", "4123052576787753", 01, 2019, 169, IllegalArgumentException.class
			}, {
				"user1", p1, 50.0, "VISA", "JUAN GAMEZ", "4123052576787753", 11, 2023, 169, IllegalArgumentException.class
			}, {
				"user2", p1, 2.0, "VISA", "JUAN GAMEZ", "4123052576787753", 11, 2023, 169, IllegalArgumentException.class
			}, {
				"user2", p5, 50.0, "VISA", "JUAN GAMEZ", "4123052576787753", 11, 2023, 169, IllegalArgumentException.class
			}, {
				"user2", p1, 50.0, "VISA", "JUAN GAMEZ", "414444444", 11, 2023, 169, ConstraintViolationException.class
			}, {
				"admin", p1, 50.0, "VISA", "JUAN GAMEZ", "4123052576787753", 11, 2023, 169, IllegalArgumentException.class
			}, {
				"corporation1", p1, 50.0, "VISA", "JUAN GAMEZ", "4123052576787753", 11, 2023, 169, IllegalArgumentException.class
			}, {
				null, p1, 50.0, "VISA", "JUAN GAMEZ", "4123052576787753", 11, 2023, 169, IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.testPatronageProjectTemplate((String) testingData[i][0], (Project) testingData[i][1], (Double) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Integer) testingData[i][6],
				(Integer) testingData[i][7], (Integer) testingData[i][8], (Class<?>) testingData[i][9]);
	}
	protected void testPatronageProjectTemplate(final String username, final Project project, final Double amount, final String brandName, final String holderName, final String cardNumber, final Integer month, final Integer year, final Integer cvv,
		final Class<?> expectedException) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);

			final Patronage p = this.patronageService.create(project.getId());
			final CreditCard creditCard = new CreditCard();
			creditCard.setBrandName(brandName);
			creditCard.setHolderName(holderName);
			creditCard.setNumber(cardNumber);
			creditCard.setExpirationMonth(month);
			creditCard.setExpirationYear(year);
			creditCard.setCvv(cvv);
			p.setAmount(amount);
			p.setCreditCard(creditCard);

			this.patronageService.saveFromCreate(p);
			this.unauthenticate();
			this.patronageService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
		}

		this.checkExceptions(expectedException, caught);
	}

}
