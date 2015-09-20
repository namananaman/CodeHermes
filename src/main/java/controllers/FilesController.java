package controllers;

import java.io.IOException;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FilesController {
	@RequestMapping("/files")
	public @ResponseBody String FilesView(@RequestParam String username, @RequestParam String repo) throws IOException {
		HttpClient http = HttpClients.createDefault();
		HttpGet get = new HttpGet("https://api.github.com/repos/" + username + "/" + repo
				+ "/git/trees/master?recursive=1");
		HttpResponse response = http.execute(get);
		String responseText = EntityUtils.toString(response.getEntity());
		JSONObject treeData = new JSONObject(responseText);
		JSONArray files = treeData.getJSONArray("tree");
		JSONArray javaFiles = new JSONArray();
		for (int i = 0; i < files.length(); i++) {
			JSONObject file = files.getJSONObject(i); //currently ignoring dirs with no java files
			if (file.getString("path").endsWith(".java")) {
				javaFiles.put(file);
			}
		}
		return javaFiles.toString();
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
