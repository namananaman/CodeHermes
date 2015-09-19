package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ParsingController {
	
	public ModelAndView getParsedFile(@RequestParam String file) {
		ModelAndView mav = new ModelAndView();
		
		return mav;
	}
}
