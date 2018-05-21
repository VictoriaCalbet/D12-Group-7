
package domain.forms;

import java.util.Date;

import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Category;

public class ProjectForm {

	private int			id;
	private String		title;
	private String		description;
	private double		economicGoal;
	private double		minimumPatronageAmount;
	private Date		dueDate;
	private boolean		isDraft;
	private Category	category;


	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
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
	public double getEconomicGoal() {
		return this.economicGoal;
	}

	public void setEconomicGoal(final double economicGoal) {
		this.economicGoal = economicGoal;
	}

	@DecimalMin(value = "0")
	public double getMinimumPatronageAmount() {
		return this.minimumPatronageAmount;
	}

	public void setMinimumPatronageAmount(final double minimumPatronageAmount) {
		this.minimumPatronageAmount = minimumPatronageAmount;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(final Date dueDate) {
		this.dueDate = dueDate;
	}

	public boolean getIsDraft() {
		return this.isDraft;
	}

	public void setIsDraft(final boolean isDraft) {
		this.isDraft = isDraft;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

}
