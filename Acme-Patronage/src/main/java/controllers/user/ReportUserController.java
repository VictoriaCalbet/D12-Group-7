
package controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ReportService;
import controllers.AbstractController;
import domain.Report;

@Controller
@RequestMapping("/report/user")
public class ReportUserController extends AbstractController {

	@Autowired
	private ReportService	reportService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createReport() {
		final ModelAndView result;
		result = null;
		return result;
	}

	// Ancillary methods

	public ModelAndView createEditModelAndView(final Report report) {
		ModelAndView result;

		result = this.createEditModelAndView(report, null);

		return result;
	}

	public ModelAndView createEditModelAndView(final Report report, final String message) {
		ModelAndView result;

		result = new ModelAndView("report/create");
		result.addObject("report", report);
		result.addObject("message", message);
		result.addObject("requestURI", "report/user/create.do");

		return result;
	}
}
