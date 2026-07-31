<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Библиотека лекций</title>
</head>
<body>
<h1>Список лекций</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Название</th>
        <th>Тема</th>
    </tr>
    <c:forEach var="lecture" items="${lectures}">
        <tr>
            <td>${lecture.id}</td>
            <td>${lecture.title}</td>
            <td>${lecture.topic}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>