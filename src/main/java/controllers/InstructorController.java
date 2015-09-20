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
		JSONArray javaFiles = new JSONArray();
		for (int i = 0; i < files.length(); i++) {
			JSONObject file = files.getJSONObject(i); //currently ignoring dirs with no java files
			if ((file.getString("path").endsWith(".java") && file.getString("path").split("/").length < 3) || file.getString("path").equalsIgnoreCase("README.MD")) {
				javaFiles.put(file);
			}
		}
		ModelAndView mav = new ModelAndView("instructor");
		mav.addObject("files", javaFiles);
		mav.addObject("lesson_id", lessonID);
		return mav;
	}

}
