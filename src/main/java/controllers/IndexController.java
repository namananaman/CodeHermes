package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import AWSTest.AWSTest1;

@Controller
public class IndexController {
	@RequestMapping("/show")
	public ModelAndView handleMessage() {
		ModelAndView mav = new ModelAndView("showMessage");
		AWSTest1 obj = new AWSTest1();
		obj.test();
		mav.addObject("message", "Success");
		return mav;
	}
}
