<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>TEST TITLE</title>
</head>
<body>
<center>
    <h1>TEST HEADING</h1>
</center>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>TEST CAPTION</h2></caption>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>SureName</th>
            <th>Nationality</th>
            <th>DOB</th>
<%--            <th>PPS Number</th>--%>
            <th>Address</th>
<%--            <th>Phone</th>--%>
            <th>Email</th>
            <th>UserName</th>
        </tr>
        <c:forEach var="user" items="${listUsers}">
            <tr>
                <td><c:out value="${user.id}" /></td>
                <td><c:out value="${user.name}" /></td>
                <td><c:out value="${user.surname}" /></td>
                <td><c:out value="${user.nationality}" /></td>
                <td><c:out value="${user.dob}" /></td>
<%--                <td><c:out value="${user.pps_number}" /></td>--%>
                <td><c:out value="${user.address}" /></td>
<%--                <td><c:out value="${user.phone_number}" /></td>--%>
                <td><c:out value="${user.email}" /></td>
                <td><c:out value="${user.username}" /></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>