<%--
  Created by IntelliJ IDEA.
  User: dimao
  Date: 14.04.2024
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>All Employees</title>
</head>
<body>
    <h3>Information for all employees</h3>
    <br>
    <br>
    <security:authorize access="hasRole('HR')">
        <input type="button" value="Salary" onclick="window.location.href = 'hr_info'">
        <br>
        <p>Only for HT staff</p>
    </security:authorize>
    <br>
    <br>
    <security:authorize access="hasRole('MANAGER')">
        <input type="button" value="Performance" onclick="window.location.href = 'manager_info'">
        <br>
        <p>Only for Manager staff</p>
    </security:authorize>
</body>
</html>
