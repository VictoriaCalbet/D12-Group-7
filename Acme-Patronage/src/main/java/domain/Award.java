
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "moneyGoal")
})
public class Award extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	name;
	private String	description;
	private double	moneyGoal;


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


	// Relationships ----------------------------------------------------------

	private Collection<AwardComment>	awardComments;
	private Project						project;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "award", cascade = CascadeType.REMOVE)
	public Collection<AwardComment> getAwardComments() {
		return this.awardComments;
	}

	public void setAwardComments(final Collection<AwardComment> awardComments) {
		this.awardComments = awardComments;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Project getProject() {
		return this.project;
	}

	public void setProject(final Project project) {
		this.project = project;
	}

}
