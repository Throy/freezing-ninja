<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%
    Object content = request.getAttribute("content");
	String header = (String)request.getAttribute("Header");
 %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>@ViewBag.Title</title>
    <link href="/GeoRedUy.BackEnd/media/styles/Site.css" rel="stylesheet" type="text/css" />
    <link href="/GeoRedUy.BackEnd/media/styles/forms.css" rel="stylesheet" type="text/css" />
    <link href="/GeoRedUy.BackEnd/media/styles/menu.css" rel="stylesheet" type="text/css" />

    <script src="/GeoRedUy.BackEnd/scripts/jquery-1.5.1.min.js" type="text/javascript"></script>
    
    <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
    <meta http-equiv="Content-language" content="es" />
	
	<%=header %>
    <!--<link href="../../Content/images/ibeticon.png" rel="icon" type="image/x-icon" />
    <link href="../../Content/images/ibeticon.png" rel="shortcut icon" /> -->
</head>
<body>
        
<div id="Kontainer" style="margin:30px auto;width:450px;">
    <div id="Content" style="margin:0 auto;width:100%;border-radius: 5px 5px 5px 5px;-moz-border-radius: 5px 5px 5px 5px;">
        <div style="float:left;padding:0;width:100%">
            <%=content%>
        </div>
    </div>
    
    <div id="Footer" style="margin:10px auto;width:450px;">
        <p style="float:right;margin-right:10px;margin-top:3px;">TSI 2</p>
        <p style="float:left;margin-left:10px;margin-top:3px;">GeoRed.Uy</p>
    </div>
    
    <div style="margin-left: 10px;color: #666">
        <br />     
    </div>
</div>

</body>
</html>
