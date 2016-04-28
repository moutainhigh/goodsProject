<html>
<body>
<h2>Hello World!</h2>
<%
request.getRequestDispatcher("/home").forward(request, response);
%>
</body>
</html>
