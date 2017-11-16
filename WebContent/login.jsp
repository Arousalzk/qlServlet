<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="no-js">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆/注册</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- CSS -->
<link rel="stylesheet" href="assets/css/reset.css">
<link rel="stylesheet" href="assets/css/supersized.css">
<link rel="stylesheet" href="assets/css/style.css">

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->


<script type="text/javascript">
    function changeImg(obj){
        obj.src="/qlServlet/checkImg?time="+new Date().getTime();
    }

    
</script>

</head>

<body>

	<div class="page-container">
		<h1>登陆</h1>
		&nbsp;&nbsp;

		<h6 id="errormsg">&emsp;&emsp;<%=request.getAttribute("logininfo")==null?"":request.getAttribute("logininfo") %></h6>
		<form action="/qlServlet/login" method="post" >
			<input type="text" name="username" class="username" placeholder="用户名">
			<input type="password" name="userpassword" class="password"
				placeholder="密码">
			<!--  
			<input type="text" class="checkCode" name="checkCode"
                            placeholder=<%=session.getAttribute("checkword") %>>
            -->
            <input type="text" class="checkcode" name="checkcode"
                 placeholder="请输入验证码">
            <img src="/qlServlet/checkImg" onclick="changeImg(this)"/>
			<button type="submit">登陆</button>
			<button type="submit" onclick="this.form.action='register'">注册</button>
			<div class="error">
				<span>+</span>
			</div>
		</form>
		<!--  
            <div class="connect">
                <p>Or connect with:</p>
                <p>
                    <a class="facebook" href=""></a>
                    <a class="twitter" href=""></a>
                </p>
            </div>
            -->
	</div>

	<!-- Javascript -->
	<script src="assets/js/jquery-1.8.2.min.js"></script>
	<script src="assets/js/supersized.3.2.7.min.js"></script>
	<script src="assets/js/supersized-init.js"></script>
	<script src="assets/js/scripts.js"></script>

</body>

</html>

