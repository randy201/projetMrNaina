<%@page import="etu1989.model.Emp" %>
<%
    Emp employe = (Emp)request.getAttribute("randy");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <%= employe.getNom() %>
    <%= employe.getFichier().getFilename() %>
</body>
</html>