<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>下载测试界面</title>
</head>
<body>
    <b>直接href方式下载测试:</b><br>
    <a href="download/apache-tomcat-7.0.82-src.zip">apache-tomcat-7.0.82-src.zip</a><br>
    <a href="download/不求上进.txt">不求上进.txt</a><br>
    <a href="download/算法（第四版）.pdf">算法（第四版）.pdf</a><br>
    <a href="download/linux cmd.png">linux cmd.png</a><br>
    <a href="download/test.MOV">test.MOV</a><br>
    
    <b>通过Servlet方式下载测试:</b><br>
    <a href="download?filename=apache-tomcat-7.0.82-src.zip">apache-tomcat-7.0.82-src.zip</a><br>
    <a href="download?filename=不求上进.txt">不求上进.txt</a><br>
    <a href="download?filename=算法（第四版）.pdf">算法（第四版）.pdf</a><br>
    <a href="download?filename=linux cmd.png">linux cmd.png</a><br>
    <a href="download?filename=test.MOV">test.MOV</a><br>
</body>
</html>