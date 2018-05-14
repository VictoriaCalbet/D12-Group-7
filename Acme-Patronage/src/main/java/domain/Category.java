
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	name;
	private String	description;


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


	// Relationships ----------------------------------------------------------

	private Collection<Project>	projects;


	public Collection<Project> getProjects() {
		return this.projects;
	}

	public void setProjects(final Collection<Project> projects) {
		this.projects = projects;
	}
}
