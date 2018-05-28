
package controllers.moderator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ReportService;
import controllers.AbstractController;
import domain.Report;
import domain.forms.ReportForm;

@Controller
@RequestMapping("/report/moderator")
public class ReportModeratorController extends AbstractController {

	@Autowired
	private ReportService	reportService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		List<Report> reports;
		reports = new ArrayList<Report>(this.reportService.listUnrevisedReports());
		result = new ModelAndView("report/list");
		result.addObject("reports", reports);
		result.addObject("requestURI", "report/moderator/list.do");
		return result;
	}
	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int reportId) {
		ModelAndView result;
		try {
			this.reportService.rejectReport(reportId);
			result = new ModelAndView("redirect:/report/moderator/list.do");
		} catch (final Throwable oops) {
			String messageError = "report.commit.error";
			if (oops.getMessage().contains("message.error"))
				messageError = oops.getMessage();
			result = this.list();
			result.addObject("message", messageError);
		}
		return result;
	}

	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int reportId) {
		final ModelAndView result;
		ReportForm reportForm;
		reportForm = new ReportForm();
		reportForm.setId(reportId);
		result = this.createEditModelAndView(reportForm);
		return result;
	}
	@RequestMapping(value = "/accept", method = RequestMethod.POST)
	public ModelAndView accept(@Valid final ReportForm reportForm, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(reportForm);
		else
			try {
				this.reportService.acceptReport(reportForm.getId(), reportForm.getReason());
				result = new ModelAndView("redirect:/report/moderator/list.do");
			} catch (final Throwable oops) {
				String messageError = "report.commit.error";
				if (oops.getMessage().contains("message.error"))
					messageError = oops.getMessage();
				result = this.createEditModelAndView(reportForm, messageError);

			}
		return result;
	}

	// Ancillary methods

	public ModelAndView createEditModelAndView(final ReportForm reportForm) {
		ModelAndView result;

		result = this.createEditModelAndView(reportForm, null);

		return result;
	}

	public ModelAndView createEditModelAndView(final ReportForm reportForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("report/accept");
		result.addObject("reportForm", reportForm);
		result.addObject("message", message);
		result.addObject("requestURI", "report/moderator/accept.do?reportId=" + reportForm.getId());

		return result;
	}

}
