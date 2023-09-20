<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Página de erros</title>
</head>
<body>
	<h1>Entre em contato com o suporte</h1>
	<% out.print(request.getAttribute("msg")); %>
</body>
</html>