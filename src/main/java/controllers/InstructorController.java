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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InstructorController {

	@RequestMapping("/instructors/{username}/{repo}")
	public @ResponseBody ModelAndView instructorCourseView(@PathVariable("username") String username,
			@PathVariable("repo") String repo) throws IOException {
		return instructorLessonView(username, repo, -1);
	}

	@RequestMapping("/instructors/{username}/{repo}/{lesson_id}")
	public @ResponseBody ModelAndView instructorLessonView(@PathVariable("username") String username,
			@PathVariable("repo") String repo, @PathVariable("lesson_id") int lessonID) throws IOException {
		HttpClient http = HttpClients.createDefault();
		HttpGet get = new HttpGet("https://api.github.com/repos/" + username + "/" + repo + "/git/trees/master?recursive=1");
		HttpResponse response = http.execute(get);
		String responseText = EntityUtils.toString(response.getEntity());
		JSONObject treeData = new JSONObject(responseText);
		JSONArray files = treeData.getJSONArray("tree");
		Hashtable<String, String> fileURLs = new Hashtable<String, String>();
		for (int i = 0; i < files.length(); i++) {
			JSONObject file = files.getJSONObject(i);
			fileURLs.put(file.getString("path"), file.getString("url"));
		}

		ModelAndView mav = new ModelAndView("instructor");
		mav.addObject("files", fileURLs);
		return mav;
	}

}
