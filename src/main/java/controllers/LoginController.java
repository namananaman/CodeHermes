package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	@Value("${github_client_id}")
	private String clientID;
	
	@Value("${github_client_secret}")
	private String clientSecret;
	
	
	@RequestMapping("/login")
	public ModelAndView LoginView(@RequestParam(value="code", required=false) String code) throws IOException {
		if (code == null) {
			return new ModelAndView("login");
		}
		HttpClient http = HttpClients.createDefault();
		HttpPost post = new HttpPost("https://github.com/login/oauth/access_token");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("client_id", clientID));
		nvps.add(new BasicNameValuePair("client_secret", clientSecret));
		nvps.add(new BasicNameValuePair("code", code));
		post.setEntity(new UrlEncodedFormEntity(nvps));
		HttpResponse response = http.execute(post);
		String responseText = EntityUtils.toString(response.getEntity());
		String token = responseText.split("&")[0].split("=")[1];
		
		HttpGet get = new HttpGet("https://api.github.com/user/repos?visibility=public&affiliation=owner");
		get.setHeader("Authorization", "token " + token);
		response = http.execute(get);
		responseText = EntityUtils.toString(response.getEntity());	
		JSONArray json = new JSONArray(responseText);
		Hashtable<String, String> repos = new Hashtable<String, String>();
		for (int i = 0; i < json.length(); i++) {
			JSONObject repo = json.getJSONObject(i);
			repos.put(repo.getString("name"), repo.getString("trees_url").replaceFirst("\\{.*\\}", "") + "/master?recursive=1");
		}
		ModelAndView mav = new ModelAndView("login");
		mav.addObject("repos", repos);
		mav.addObject("token", token);
		return mav;
	}
}
