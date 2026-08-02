<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>${lecture.title}</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/lectures">&larr; Назад к списку</a>
<h1>${lecture.title}</h1>
<p><i>Тема: ${lecture.topic}</i></p>
<p>${lecture.content}</p>
</body>
</html>