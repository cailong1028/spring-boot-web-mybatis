<%--页面使用utf-8编码，支持中文--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%--引入基础标签库--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--spring mvc form标签库--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <script src="http://lib.sinaapp.com/js/jquery/1.9.1/jquery-1.9.1.min.js"></script>
    <script>
        function getFamilyInfo(studentName){
            $.ajax({
                contentType: 'application/json;charset=utf-8',
                type: 'get',
                dateType: 'json',
                url: '/form/getFamilyInfo',
                data: {name: studentName},
                success(result){
                    console.log(result);
                }
            });
        }
    </script>
</head>
<body>
    <form id="requestForm" method="post" modelAttribute="student, grade" action="/form/save">
        <form:input path="student.name" onblur="getFamilyInfo(value)"/><br/>
        <form:input path="student.age"/><br/>
        <hr/>
        <form:input path="student.teacher.name"/><br/>
        <form:input path="student.teacher.gender"/><br/>
        <form:checkbox path="student.honer" label="是否是保送生"/>
        <table>
            <thead>
                <tr>
                    <td>课程名</td>
                    <td>学时</td>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${student.courseList}" var="course" varStatus="i">
                    <tr>
                        <td><form:input path="student.courseList[${i.index}].name"/></td>
                        <td><form:input path="student.courseList[${i.index}].period"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <form:input path="grade.headMater"/><br/>
        <form:input path="grade.name"/><br/>
        <input type="submit" value="提交"/>
    </form>
</body>
</html>