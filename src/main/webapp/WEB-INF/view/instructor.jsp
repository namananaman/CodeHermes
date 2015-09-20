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
					<script type="text/javascript">
							response = ${files};
							var dirs = $('#directory-list');
							var tree = {};
							for (var i = 0; i < response.length; i++) {
								var parts = response[i].path.split('/');
								if (parts.length > 2) {
									continue;
								} else if (parts.length === 2) {
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
									dirs.append('<li class="list__caret"><a href="">' + tree_keys[i] + '</a></li>')
									if (tree_keys[i].toLowerCase() === 'readme.md') {
										readme_url = tree[tree_keys[i]].url;
									}
								} else {
									var dir_str = '<li class="list__caret"><a href=""> <i class="uk-icon-caret-right"></i>'
									+ tree_keys[i] + '<ul class="list">';
									var dir_contents = tree[tree_keys[i]];
									for (var j = 0; j < dir_contents.length; j++) {
										dir_str += '<li><a href="">' + dir_contents[j] + '</a></li>';
									}
									dir_str += '</ul></a></li>';
									dirs.append(dir_str);
								}
							}	
							//now time to get the file specified in the url
							var lesson_id = ${lesson_id};
							if (lesson_id === -1) { //this means that there's no specified lesson
								$.getJSON('/lesson?file_url=' + readme_url).then(function(response) {
									console.log(response);
								});
							} else {
								$.getJSON('/lesson?file_url=' + tree[tree_keys[lesson_id]].url).then(function(response) {
									console.log(response);									
								});
							}
						
					
					</script>	
					<!-- <li class="list__caret"><a href="">README.md</a></li>
					<li class="list__caret"><a href=""><i class="uk-icon-caret-right"></i> Lesson 1</a></li>
					<li class="list__caret"><a href=""><i class="uk-icon-caret-right"></i> Lesson 2</a></li>
					<li class="list__caret"><a href=""> <i class="uk-icon-caret-right"></i> Lesson 3
							<ul class="list">
								<li><a class="active" href="">filename.py</a></li>
								<li><a href="">example.py</a></li>
							</ul>
					</a></li>
					<li class="list__caret"><a href=""><i class="uk-icon-caret-right"></i> Lesson 4</a></li>
					<li class="list__caret"><a href=""><i class="uk-icon-caret-right"></i> Lesson 5</a></li> -->
				</ul>
			</div>
			<div class="sidebar__bottom">
				<div class="list__head">Functions</div>
				<ul class="list--line">
					<li class="list__item droid-sans"><a href="">foo() {...}</a></li>
				</ul>
			</div>
		</div>
		<div class="dashboard">
			<div class="dashboard__code">
				<div class="dashboard__actionbar" data-uk-sticky>
					<a href="index.html"> <img class="actionbar__logo" src="/resources/img/logo.svg" alt="Code Hermes" />
					</a> <strong>Lesson 3 <i class="uk-icon-angle-right"></i> filename.py
					</strong>
					<div class="actionbar__input">
						Share this file: <input type="text" value="http://codehermes.co/A8DJ24" data-share-link />
						<!-- <div class="actionbar__icon" data-uk-tooltip title="Copy to clipboard"><i class="uk-icon-clipboard"></i></div> -->
					</div>
				</div>
				<ul id="code-list">
					<li>
						<div class="code__line_number">1</div> <pre class="code__line">
							<code class="language-java">package ParserTest;</code>
						</pre>
						<div class="dropdown" data-uk-dropdown="{mode:'click'}">
							<div class="code__icon uk-button-dropdown" data-uk-tooltip="{pos:'right'}" title="Questions asked">0</div>
							<div class="dropdown__box">
								<form>
									<textarea type="text" maxlength="140" placeholder="Questions?"></textarea>
								</form>
							</div>
						</div>
					</li>
					<li>
						<div class="code__line_number">2</div> <pre class="code__line">
							<code class="language-java"></code>
						</pre>
						<div class="dropdown" data-uk-dropdown="{mode:'click'}">
							<div class="code__icon uk-button-dropdown" data-uk-tooltip="{pos:'right'}" title="Questions asked">0</div>
							<div class="dropdown__box">
								<form>
									<textarea type="text" maxlength="140" placeholder="Questions?"></textarea>
								</form>
							</div>
						</div>
					</li>
					<li>
						<div class="code__line_number">3</div> <pre class="code__line">
							<code class="language-java">public class TestClass {</code>
						</pre>
						<div class="dropdown" data-uk-dropdown="{mode:'click'}">
							<div class="code__icon uk-button-dropdown" data-uk-tooltip="{pos:'right'}" title="Questions asked">0</div>
							<div class="dropdown__box">
								<form>
									<textarea type="text" maxlength="140" placeholder="Questions?"></textarea>
								</form>
							</div>
						</div>
					</li>
					<li>
						<div class="code__line_number">4</div> <pre class="code__line">
							<code class="language-java">  public void function1() {</code>
						</pre>
						<div class="dropdown" data-uk-dropdown="{mode:'click'}">
							<div class="code__icon uk-button-dropdown" data-uk-tooltip="{pos:'right'}" title="Questions asked">0</div>
							<div class="dropdown__box">
								<form>
									<textarea type="text" maxlength="140" placeholder="Questions?"></textarea>
								</form>
							</div>
						</div>
					</li>
					<li>
						<div class="code__line_number">5</div> <pre class="code__line">
							<code class="language-java">    int i;</code>
						</pre>
						<div class="dropdown" data-uk-dropdown="{mode:'click'}">
							<div class="code__icon uk-button-dropdown" data-uk-tooltip="{pos:'right'}" title="Questions asked">0</div>
							<div class="dropdown__box">
								<form>
									<textarea type="text" maxlength="140" placeholder="Questions?"></textarea>
								</form>
							</div>
						</div>
						<div class="code__inline_comment">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce vulputate
							odio eu volutpat fringilla. Morbi sit amet maximus orci.</div>
					</li>
					<li>
						<div class="code__line_number">6</div> <pre class="code__line">
							<code class="language-java">  }</code>
						</pre>
						<div class="dropdown" data-uk-dropdown="{mode:'click'}">
							<div class="code__icon uk-button-dropdown" data-uk-tooltip="{pos:'right'}" title="Questions asked">0</div>
							<div class="dropdown__box">
								<form>
									<textarea type="text" maxlength="140" placeholder="Questions?"></textarea>
								</form>
							</div>
						</div>
					</li>
					<li>
						<div class="code__line_number">7</div> <pre class="code__line">
							<code class="language-java"></code>
						</pre>
						<div class="dropdown" data-uk-dropdown="{mode:'click'}">
							<div class="code__icon uk-button-dropdown" data-uk-tooltip="{pos:'right'}" title="Questions asked">0</div>
							<div class="dropdown__box">
								<form>
									<textarea type="text" maxlength="140" placeholder="Questions?"></textarea>
								</form>
							</div>
						</div>
					</li>
					<li class="highlight">
						<div class="code__line_number">8</div> <pre class="code__line">
							<code class="language-java">  public void function2() {</code>
						</pre>
						<div class="dropdown" data-uk-dropdown="{mode:'click'}">
							<div class="code__icon uk-button-dropdown" data-uk-tooltip="{pos:'right'}" title="Questions asked">0</div>
							<div class="dropdown__box">
								<form>
									<textarea type="text" maxlength="140" placeholder="Questions?"></textarea>
								</form>
							</div>
						</div>
					</li>
					<li>
						<div class="code__line_number">9</div> <pre class="code__line">
							<code class="language-java">  }</code>
						</pre>
						<div class="dropdown" data-uk-dropdown="{mode:'click'}">
							<div class="code__icon uk-button-dropdown" data-uk-tooltip="{pos:'right'}" title="Questions asked">0</div>
							<div class="dropdown__box">
								<form>
									<textarea type="text" maxlength="140" placeholder="Questions?"></textarea>
								</form>
							</div>
						</div>
					</li>
					<li>
						<div class="code__line_number">10</div> <pre class="code__line">
							<code class="language-java"></code>
						</pre>
						<div class="dropdown" data-uk-dropdown="{mode:'click'}">
							<div class="code__icon uk-button-dropdown" data-uk-tooltip="{pos:'right'}" title="Questions asked">0</div>
							<div class="dropdown__box">
								<form>
									<textarea type="text" maxlength="140" placeholder="Questions?"></textarea>
								</form>
							</div>
						</div>
					</li>
					<li>
						<div class="code__line_number">11</div> <pre class="code__line">
							<code class="language-java">  public void function() {</code>
						</pre>
						<div class="dropdown" data-uk-dropdown="{mode:'click'}">
							<div class="code__icon uk-button-dropdown" data-uk-tooltip="{pos:'right'}" title="Questions asked">0</div>
							<div class="dropdown__box">
								<form>
									<textarea type="text" maxlength="140" placeholder="Questions?"></textarea>
								</form>
							</div>
						</div>
					</li>
					<li>
						<div class="code__line_number">12</div> <pre class="code__line">
							<code class="language-java">  }</code>
						</pre>
						<div class="dropdown" data-uk-dropdown="{mode:'click'}">
							<div class="code__icon uk-button-dropdown" data-uk-tooltip="{pos:'right'}" title="Questions asked">0</div>
							<div class="dropdown__box">
								<form>
									<textarea type="text" maxlength="140" placeholder="Questions?"></textarea>
								</form>
							</div>
						</div>
					</li>
					<li>
						<div class="code__line_number">13</div> <pre class="code__line">
							<code class="language-java">}</code>
						</pre>
						<div class="dropdown" data-uk-dropdown="{mode:'click'}">
							<div class="code__icon uk-button-dropdown" data-uk-tooltip="{pos:'right'}" title="Questions asked">0</div>
							<div class="dropdown__box">
								<form>
									<textarea type="text" maxlength="140" placeholder="Questions?"></textarea>
								</form>
							</div>
						</div>
					</li>
				</ul>
			</div>
			<div class="dashboard__comment">
				<!-- <hr class="rainbow" /> -->
				<div class="dashboard__comment__border"></div>
				<div class="container">
					<div class="comment" style="top: 70px;">
						<div class="comment__border"></div>
						<!-- <div class="comment__line_number">foo()</div> -->
						Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam at ullamcorper est. Etiam id ipsum a augue
						gravida ultricies sed in erat.
					</div>
					<div class="comment" style="top: 245px;">
						<div class="comment__border"></div>
						<!-- <div class="comment__line_number">bar()</div> -->
						Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam at ullamcorper est. Etiam id ipsum a augue
						gravida ultricies sed in erat.
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="/resources/js/lib.min.js"></script>
	<script src="/resources/js/main.min.js"></script>
</body>
</html>
