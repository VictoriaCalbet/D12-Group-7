package domain.forms;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

import domain.DomainEntity;

@Entity
@Access(AccessType.PROPERTY)
public class CommentForm extends DomainEntity {

	// Attributes -------------------------------------------------------------

		private String		text;
		private Date		creationMoment;
		private Integer		rating;


		@NotBlank
		@SafeHtml(whitelistType = WhiteListType.NONE)
		public String getText() {
			return this.text;
		}

		public void setText(final String text) {
			this.text = text;
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

		@Range(min=1,max=5)
		public Integer getRating() {
			return this.rating;
		}

		public void setRating(final Integer rating) {
			this.rating = rating;
		}

	
}
