
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import domain.Report;

@Service
@Transactional
public class ReportService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ReportRepository	reportRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public ReportService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// TODO: Report - create
	public Report create() {
		final Report result = null;

		return result;
	}

	public Collection<Report> findAll() {
		Collection<Report> result = null;
		result = this.reportRepository.findAll();
		return result;
	}

	public Report findOne(final int reportId) {
		Report result = null;
		result = this.reportRepository.findOne(reportId);
		return result;
	}

	public Report save(final Report report) {
		Assert.notNull(report);
		Report result = null;
		result = this.reportRepository.save(report);
		return result;
	}

	// TODO: Report - saveFromCreate
	public Report saveFromCreate() {
		final Report result = null;

		return result;
	}

	// TODO: Report - saveFromEdit
	public Report saveFromEdit() {
		final Report result = null;

		return result;
	}

	public void flush() {
		this.reportRepository.flush();
	}

	// Other business methods -------------------------------------------------

}
