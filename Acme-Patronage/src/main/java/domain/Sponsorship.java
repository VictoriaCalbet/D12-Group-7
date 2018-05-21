
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	bannerURL;
	private String	pageURL;


	@NotBlank
	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBannerURL() {
		return this.bannerURL;
	}

	public void setBannerURL(final String bannerURL) {
		this.bannerURL = bannerURL;
	}

	@NotBlank
	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPageURL() {
		return this.pageURL;
	}

	public void setPageURL(final String pageURL) {
		this.pageURL = pageURL;
	}


	// Relationships ----------------------------------------------------------

	private Project		project;
	private Corporation	corporation;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Project getProject() {
		return this.project;
	}

	public void setProject(final Project project) {
		this.project = project;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Corporation getCorporation() {
		return this.corporation;
	}

	public void setCorporation(final Corporation corporation) {
		this.corporation = corporation;
	}

}
