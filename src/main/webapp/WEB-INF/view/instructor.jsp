<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>CodeHermes- Instructor</title>
<link rel="icon" href="/resources/img/favicon.png" />
<link rel="stylesheet" href="/resources/css/main.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
</head>
<body>
	<div class="grid--collapse uk-height-1-1">
		<div class="sidenav">
			<div class="sidebar__top">
				<form>
					<div class="sidenav__search">
						<i class="uk-icon-search"></i> <input type="text" />
					</div>
				</form>
				<br />
				<ul id="directory-list" class="list">
				</ul>
			</div>
		</div>
		<div class="dashboard">
			<div class="dashboard__code">
				<div class="dashboard__actionbar" data-uk-sticky>
					<a href="index.html"> <img class="actionbar__logo" src="/resources/img/logo.svg" alt="Code Hermes" />
					</a> <strong><span id="current-file"></span></strong>
					<div class="actionbar__input">
						Share this file: <input type="text" value="${url}" data-share-link />
					</div>
				</div>
				<ul id="code-list">
				</ul>
			</div>
			<div class="dashboard__comment">
				<!-- <hr class="rainbow" /> -->
				<div class="dashboard__comment__border"></div>
				<div class="container" id="comment-container">
				</div>
			</div>
		</div>
	</div>
	<script src="/resources/js/lib.min.js"></script>
	<script src="/resources/js/main.min.js"></script>
	<script type="text/javascript">
		response = ${files};
		var dirs = $('#directory-list');
		var tree = {};
		for (var i = 0; i < response.length; i++) {
			var parts = response[i].path.split('/');
			if (parts.length === 2) {
				if (tree[parts[0]] === undefined) {
					tree[parts[0]] = [{path: parts[1], url: response[i].url}];
				} else {
					tree[parts[0]].push({path: parts[1], url: response[i].url});
				}
			} else {
				tree[parts[0]] = {path: parts[0], url: response[i].url};
			}
		}
		var tree_keys = Object.keys(tree);
		var readme_url;
		for (var i = 0; i < tree_keys.length; i++) {
			if (!(tree[tree_keys[i]] instanceof Array)) { //we have a file
				dirs.append('<li class="list__caret"><a href="' + i + '">' + tree_keys[i] + '</a></li>')
				if (tree_keys[i].toLowerCase() === 'readme.md') {
					readme_url = tree[tree_keys[i]].url;
				}
			} else {
				var dir_str = '<li class="list__caret list__dir"><a href="' + i + '"> <i class="uk-icon-caret-right"></i>'
				+ tree_keys[i] + '<ul class="list">';
				var dir_contents = tree[tree_keys[i]];
				for (var j = 0; j < dir_contents.length; j++) {
					dir_str += '<li><a href="">' + dir_contents[j].path + '</a></li>';
				}
				dir_str += '</ul></a></li>';
				dirs.append(dir_str);
			}
		}	
		//now time to get the file specified in the url
		var lesson_id = ${lesson_id};
		var file_url;
		if (lesson_id === -1) { //this means that there's no specified lesson
			file_url = readme_url;
			$('#current-file').html("readme.md");
		} else {
			file_url = response[lesson_id].url;
			$('#current-file').html(response[lesson_id].path);
		}
		$.getJSON('/lesson?file_url=' + file_url).then(function(response) {
			var code_list = $('#code-list');
			console.log(response);
			var file_lines = response.newFile.split('\n');
			var append_str = '';
			for (var i = 0; i < file_lines.length; i++) {
				append_str += '<li id="code-line-' + i + '">' +
					'<div class="code__line_number">' + 
					i + '</div> <pre class="code__line">' +
					'<code class="language-java">' + file_lines[i] + '</code></pre>' +
					'<div class="dropdown" data-uk-dropdown="{mode:\'click\'}">' +
					'<div class="code__icon uk-button-dropdown" data-uk-tooltip="{pos:\'right\'}" title="Questions asked">0</div>' +
					'<div class="dropdown__box">' + 
					'<form><textarea type="text" maxlength="140" placeholder="Questions?"></textarea></form>' +
					'</div></div></li>';
			}
			code_list.append(append_str);
			
			var comments = response.blockComments;
			var comment_container = $('#comment-container');
			
			var append_str = '';
			for (var i = 0; i < comments.length; i++) {
				append_str += '<div class="comment" style="top: ' + (response.commentToLineNumbers[comments[i]].start + 1) * 35 + 'px;">' + 
					'<div class="comment__border"></div>' +
					comments[i] +
					'</div>';
			}
			comment_container.append(append_str);
			
			comments = response.inlineComments;
			for (var i = 0; i < comments.length; i++) {
				var line_num = response.inlineCommentToLineNumber[comments[i]];
				var line_li = $('#code-line-' + line_num);
				line_li.append('<div class="code__inline_comment" style="display: inline; right: 30px; position: absolute">' + comments[i] + '</div>');
			}
		});

</script>
</body>
</html>
