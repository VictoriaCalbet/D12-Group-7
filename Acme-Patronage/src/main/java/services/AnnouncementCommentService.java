
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AnnouncementCommentRepository;
import domain.AnnouncementComment;

@Service
@Transactional
public class AnnouncementCommentService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AnnouncementCommentRepository	announcementCommentRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public AnnouncementCommentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// TODO: AnnouncementComment - create
	public AnnouncementComment create() {
		final AnnouncementComment result = null;

		return result;
	}

	public Collection<AnnouncementComment> findAll() {
		Collection<AnnouncementComment> result = null;
		result = this.announcementCommentRepository.findAll();
		return result;
	}

	public AnnouncementComment findOne(final int announcementCommentId) {
		AnnouncementComment result = null;
		result = this.announcementCommentRepository.findOne(announcementCommentId);
		return result;
	}

	public AnnouncementComment save(final AnnouncementComment announcementComment) {
		Assert.notNull(announcementComment);
		AnnouncementComment result = null;
		result = this.announcementCommentRepository.save(announcementComment);
		return result;
	}

	// TODO: AnnouncementComment - saveFromCreate
	public AnnouncementComment saveFromCreate() {
		final AnnouncementComment result = null;

		return result;
	}

	// TODO: AnnouncementComment - saveFromEdit
	public AnnouncementComment saveFromEdit() {
		final AnnouncementComment result = null;

		return result;
	}

	public void flush() {
		this.announcementCommentRepository.flush();
	}

	// Other business methods -------------------------------------------------

}
