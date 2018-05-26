
package controllers.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ProjectService;
import services.ReportService;
import controllers.AbstractController;
import domain.Report;

@Controller
@RequestMapping("/report/user")
public class ReportUserController extends AbstractController {

	@Autowired
	private ReportService	reportService;
	@Autowired
	private ProjectService	projectService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createReport() {
		ModelAndView result;
		Report report;
		report = this.reportService.create();
		result = this.createEditModelAndView(report);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createReport(@Valid final Report report, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(report);
		else
			try {
				this.reportService.saveFromCreate(report);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				String messageError = "report.commit.error";
				if (oops.getMessage().contains("message.error"))
					messageError = oops.getMessage();
				result = this.createEditModelAndView(report, messageError);
			}

		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final Report report) {
		ModelAndView result;

		result = this.createEditModelAndView(report, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Report report, final String message) {
		ModelAndView result;

		result = new ModelAndView("report/create");
		result.addObject("report", report);
		result.addObject("message", message);
		result.addObject("requestURI", "report/user/create.do");
		result.addObject("projects", this.projectService.findProjectFutureDueDate());

		return result;
	}
}
