
package domain.forms;

import javax.validation.constraints.DecimalMin;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

public class AwardForm {

	// Attributes -------------------------------------------------------------

	private int		id;
	private String	name;
	private String	description;
	private double	moneyGoal;
	private int		projectId;
	private double	minimumPatronageAmount;
	private int		userId;


	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@DecimalMin(value = "0")
	public double getMoneyGoal() {
		return this.moneyGoal;
	}

	public void setMoneyGoal(final double moneyGoal) {
		this.moneyGoal = moneyGoal;
	}

	public int getProjectId() {
		return this.projectId;
	}

	public void setProjectId(final int projectId) {
		this.projectId = projectId;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(final int userId) {
		this.userId = userId;
	}

	@DecimalMin(value = "0")
	public double getMinimumPatronageAmount() {
		return this.minimumPatronageAmount;
	}

	public void setMinimumPatronageAmount(final double minimumPatronageAmount) {
		this.minimumPatronageAmount = minimumPatronageAmount;
	}

}
