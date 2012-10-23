<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import="georeduy.backend.model.*" %>

<%
    Object content = request.getAttribute("content");
	User user = (User)session.getAttribute("User");
 %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>GeoRed.UY</title>
    <link href="/GeoRedUy.BackEnd/media/styles/Site.css" rel="stylesheet" type="text/css" />
    <link href="/GeoRedUy.BackEnd/media/styles/forms.css" rel="stylesheet" type="text/css" />
    <link href="/GeoRedUy.BackEnd/media/styles/menu.css" rel="stylesheet" type="text/css" />
    <link href="/GeoRedUy.BackEnd/media/styles/lists.css" rel="stylesheet" type="text/css" />
	<link href="/GeoRedUy.BackEnd/media/styles/MeetingsList.css" rel="stylesheet" type="text/css" />
    <script src="/GeoRedUy.BackEnd/scripts/jquery-1.5.1.min.js" type="text/javascript"></script>
    
    <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
    <meta http-equiv="Content-language" content="es" />

    <!--<link href="../../Content/images/ibeticon.png" rel="icon" type="image/x-icon" />
    <link href="../../Content/images/ibeticon.png" rel="shortcut icon" /> -->
</head>
<body>

<div id="contenedor">
    <div id="menu">
        <ul style="width:300px;">
            <li class="outer"><span class="menubtn"><a href="/GeoRedUy.BackEnd/home">Home</a></span></li>
            
            <!-- system admin -->
            <li class="outer"><span class="menubtn"><a>Sitios</a></span>
                <ul>
                    <li class="menuitm"><a href="/GeoRedUy.BackEnd/site?NewSite">Nuevo sitio</a></li>
                    <li class="menuitm"><a href="/GeoRedUy.BackEnd/site?ListSites">Ver sitios</a></li>
                </ul>
            </li>
            
            <li class="outer"><span class="menubtn"><a href="/GeoRedUy.BackEnd/test">Test</a></span></li>
            
        </ul>                
        <span class="menulogon">
            <ul>
                <li class="outer" style="position:relative; "><span class="userbtn"><a><%=user.getUserName()%></a></span>
                    <ul style="width:100%">
                        <li class="menuitm"><a href="./?LogOut">LogOut</a></li>
                    </ul>
                </li>
            </ul>
        </span>                   
    </div>
</div>
        
<div id="Kontainer">
    <div id="Content" style="width:996px;">
        <div style="float:left;padding:10px;width:100%">
            <%=content%>
        </div>
    </div>
    
    <div id="Footer" style="width:996px;margin-left:0;">
        <p style="float:right;margin-right:10px;margin-top:3px;">TSI 2</p>
        <p style="float:left;margin-left:10px;margin-top:3px;">GeoRed.Uy</p>
    </div>
    
    <div style="margin-left: 10px;color: #666">
        <br />     
    </div>
</div>

</body>
</html>
