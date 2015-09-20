<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" href="/resources/img/favicon.png" />
<link rel="stylesheet" href="/resources/css/main.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<title>CodeHermes</title>
</head>
<body class="login">
	<div>
		<div class="container--login">
			<div class="login__logo"></div>
			<c:choose>
				<c:when test="${repos == null}">
					<button class="btn--github"
						onClick="window.location.href='https://github.com/login/oauth/authorize?client_id=b7ff8fdedd37bf22e7ac&redirect_uri=http://www.codehermes.co/login'">
						<i class="uk-icon-github"></i> Login with Github
					</button>
				</c:when>
				<c:otherwise>
					<div class="list__head">Repositories</div>
					<div id="repo-list" class="container--box">
						<ul class="list--line">
							<c:forEach items="${repos}" var="entry">
								<c:set var="full_name" value="full_name" />
								<li class="list__icon"><a class="repo__anchor" href="/instructors/${entry.getString("full_name")}/"><i
										class="repo__icon uk-icon-book"></i>
										<div class="repo__name">${entry.getString("name")}</div>
										<div class="repo__desc">${entry.getString("description")}</div> </a></li>
							</c:forEach>
						</ul>
					</div>
				</c:otherwise>
			</c:choose>
			${response}
		</div>
	</div>
	<script src="/resources/js/lib.min.js"></script>
	<script src="/resources/js/main.min.js"></script>
</body>
</html>