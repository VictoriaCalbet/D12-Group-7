
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	bannerURL;
	private String	infoLink;


	public String getBannerURL() {
		return this.bannerURL;
	}

	public void setBannerURL(final String bannerURL) {
		this.bannerURL = bannerURL;
	}

	public String getInfoLink() {
		return this.infoLink;
	}

	public void setInfoLink(final String infoLink) {
		this.infoLink = infoLink;
	}


	// Relationships ----------------------------------------------------------

	private Project		project;
	private Corporation	corporation;


	public Project getProject() {
		return this.project;
	}

	public void setProject(final Project project) {
		this.project = project;
	}

	public Corporation getCorporation() {
		return this.corporation;
	}

	public void setCorporation(final Corporation corporation) {
		this.corporation = corporation;
	}

}
