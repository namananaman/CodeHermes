package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	@RequestMapping("/show")
	public ModelAndView handleMessage() {
		ModelAndView mav = new ModelAndView("showMessage");
		mav.addObject("message", "Hello World!");
		return mav;
	}
}
