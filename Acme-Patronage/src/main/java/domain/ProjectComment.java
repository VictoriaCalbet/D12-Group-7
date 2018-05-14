
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class ProjectComment extends Comment {

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	private Project	project;


	public Project getProject() {
		return this.project;
	}

	public void setProject(final Project project) {
		this.project = project;
	}

}
