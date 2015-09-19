package controllers;

import java.io.IOException;
import java.util.Hashtable;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FilesController {
	@RequestMapping("/files")
	public ModelAndView FilesView(@RequestParam String repo_url) throws IOException {
		String[] params = repo_url.split("=");
		String token = params[params.length - 1];
		HttpClient http = HttpClients.createDefault();
		HttpGet get = new HttpGet(repo_url);
		HttpResponse response = http.execute(get);
		String responseText = EntityUtils.toString(response.getEntity());	
		JSONObject treeData = new JSONObject(responseText);
		JSONArray files = treeData.getJSONArray("tree");
		Hashtable<String, String> fileURLs = new Hashtable<String, String>();
		for (int i = 0; i < files.length(); i++) {
			JSONObject file = files.getJSONObject(i);
			fileURLs.put(file.getString("path"), file.getString("url") + "&access_id=" + token);
		}
		
		ModelAndView mav = new ModelAndView("files");
		mav.addObject("files", fileURLs);
		return mav;
	}
	
	@RequestMapping("/lesson")
	public ModelAndView LessonView(@RequestParam String file_url) throws IOException {
		HttpClient http = HttpClients.createDefault();
		HttpGet get = new HttpGet(file_url);
		get.setHeader("Accept", "application/vnd.github.V3.raw");
		HttpResponse response = http.execute(get);
		String responseText = EntityUtils.toString(response.getEntity());
		ModelAndView mav = new ModelAndView("lesson");
		mav.addObject("file", responseText);
		return mav;
	}
}
