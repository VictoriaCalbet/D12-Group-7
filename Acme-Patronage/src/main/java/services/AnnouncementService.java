
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AnnouncementRepository;
import domain.Announcement;

@Service
@Transactional
public class AnnouncementService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AnnouncementRepository	announcementRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public AnnouncementService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// TODO: Announcement - create
	public Announcement create() {
		final Announcement result = null;

		return result;
	}

	public Collection<Announcement> findAll() {
		Collection<Announcement> result = null;
		result = this.announcementRepository.findAll();
		return result;
	}

	public Announcement findOne(final int announcementId) {
		Announcement result = null;
		result = this.announcementRepository.findOne(announcementId);
		return result;
	}

	public Announcement save(final Announcement announcement) {
		Assert.notNull(announcement);
		Announcement result = null;
		result = this.announcementRepository.save(announcement);
		return result;
	}

	// TODO: AnnouncementComment - saveFromCreate
	public Announcement saveFromCreate() {
		final Announcement result = null;

		return result;
	}

	// TODO: AnnouncementComment - saveFromEdit
	public Announcement saveFromEdit() {
		final Announcement result = null;

		return result;
	}

	public void flush() {
		this.announcementRepository.flush();
	}

	// Other business methods -------------------------------------------------

}
