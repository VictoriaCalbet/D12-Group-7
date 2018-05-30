
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "dueDate"), @Index(columnList = "economicGoal"), @Index(columnList = "isCancelled"), @Index(columnList = "isDraft"), @Index(columnList = "creationMoment"), @Index(columnList = "title"), @Index(columnList = "description")
})
public class Project extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	title;
	private String	description;
	private Date	creationMoment;
	private double	economicGoal;
	private double	minimumPatronageAmount;
	private Date	dueDate;
	private boolean	isDraft;
	private boolean	isCancelled;


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

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCreationMoment() {
		return this.creationMoment;
	}

	public void setCreationMoment(final Date creationMoment) {
		this.creationMoment = creationMoment;
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

	public boolean getIsCancelled() {
		return this.isCancelled;
	}

	public void setIsCancelled(final boolean isCancelled) {
		this.isCancelled = isCancelled;
	}


	// Relationships ----------------------------------------------------------

	private User						creator;
	private Collection<Announcement>	announcements;
	private Collection<Report>			reports;
	private Collection<Patronage>		patronages;
	private Collection<ProjectComment>	projectComments;
	private Collection<Sponsorship>		sponsorships;
	private Category					category;
	private Collection<Award>			awards;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public User getCreator() {
		return this.creator;
	}

	public void setCreator(final User creator) {
		this.creator = creator;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
	public Collection<Announcement> getAnnouncements() {
		return this.announcements;
	}

	public void setAnnouncements(final Collection<Announcement> announcements) {
		this.announcements = announcements;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
	public Collection<Report> getReports() {
		return this.reports;
	}

	public void setReports(final Collection<Report> reports) {
		this.reports = reports;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
	public Collection<Patronage> getPatronages() {
		return this.patronages;
	}

	public void setPatronages(final Collection<Patronage> patronages) {
		this.patronages = patronages;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
	public Collection<ProjectComment> getProjectComments() {
		return this.projectComments;
	}

	public void setProjectComments(final Collection<ProjectComment> projectComments) {
		this.projectComments = projectComments;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
	public Collection<Sponsorship> getSponsorships() {
		return this.sponsorships;
	}

	public void setSponsorships(final Collection<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
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

	@NotNull
	@Valid
	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
	public Collection<Award> getAwards() {
		return this.awards;
	}

	public void setAwards(final Collection<Award> awards) {
		this.awards = awards;
	}

}
