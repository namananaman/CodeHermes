package controllers;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FilesController {
	@RequestMapping("/files")
	public ModelAndView FilesView(@RequestParam String repo_url) throws IOException {
		HttpClient http = HttpClients.createDefault();
		HttpGet get = new HttpGet(repo_url);
		HttpResponse response = http.execute(get);
		String responseText = EntityUtils.toString(response.getEntity());	
		
		ModelAndView mav = new ModelAndView("files");
		mav.addObject("text", responseText);
		return mav;
	}
}
