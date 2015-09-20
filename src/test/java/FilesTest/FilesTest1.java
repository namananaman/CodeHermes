package FilesTest;

import java.io.IOException;

import org.junit.Test;

import controllers.FilesController;

public class FilesTest1 {

	@Test
	public void test() throws IOException {
		FilesController obj = new FilesController();
		String result = obj
				.LessonView("https://api.github.com/repos/namananaman/CodeHermesTest/git/blobs/943710ed4ee40717a83e3ce16eedf38136b7d205");
		System.out.println(result);
	}

}
