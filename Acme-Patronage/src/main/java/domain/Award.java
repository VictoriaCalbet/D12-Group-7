
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Award extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	name;
	private String	description;
	private Double	moneyGoal;


	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Double getMoneyGoal() {
		return this.moneyGoal;
	}

	public void setMoneyGoal(final Double moneyGoal) {
		this.moneyGoal = moneyGoal;
	}


	// Relationships ----------------------------------------------------------

	private AwardComment	awardComment;
	private Project			project;


	public AwardComment getAwardComment() {
		return this.awardComment;
	}

	public void setAwardComment(final AwardComment awardComment) {
		this.awardComment = awardComment;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(final Project project) {
		this.project = project;
	}

}
