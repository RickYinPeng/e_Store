<html>
<head>
    <title>student</title>
</head>
<body>
    学生信息：<br/>
    学号：${student.id}&nbsp;&nbsp;&nbsp;&nbsp;
    姓名：${student.name}&nbsp;&nbsp;&nbsp;&nbsp;
    年龄：${student.age}&nbsp;&nbsp;&nbsp;&nbsp;
    家庭住址：${student.address}<br/>

    学生列表：
    <table border="1">
        <tr>
            <th>学号</th>
            <th>姓名</th>
            <th>年龄</th>
            <th>家庭住址</th>
        </tr>
        <#list stuList as stu>
        <tr>
            <td>${stu.id}</td>
            <td>${stu.name}</td>
            <td>${stu.age}</td>
            <td>${stu.address}</td>
        </tr>
        </#list>
    </table>
</body>
</html>