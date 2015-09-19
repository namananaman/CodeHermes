package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/showMessage")
public class IndexController {
	public ModelAndView handleMessage() {
		ModelAndView mav = new ModelAndView("showMessage.jsp");
		mav.addObject("message", "Hello World!");
		return mav;
	}
}
