<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import="georeduy.backend.model.*" %>

<%
    String success = (String)request.getAttribute("Success");
	String errorMsg = (String)request.getAttribute("ErrorMsg");
	User user = (User)session.getAttribute("User");
 %>

 <% if (success == null) { %>
<br /><br />
<h2 style="margin-left:30px;">System administrator login</h2>

<script src="/GeoRedUy.BackEnd/scripts/jquery.validate.min.js" type="text/javascript"></script>
<script src="/GeoRedUy.BackEnd/scripts/jquery.validate.unobtrusive.min.js" type="text/javascript"></script>

<div style="margin-left:30px;">
<%=errorMsg%>
</div>

<br />
<form action="" method="post">    <div class="reg-box big-red" style="margin:0 auto;">
        <fieldset>
            <div class="editor-label">
                <label for="UserName">User name</label>
            </div>
            <div class="editor-field">
                <input data-val="true" data-val-required="The User name field is required." id="UserName" name="UserName" type="text" value="" />
                <span class="field-validation-valid" data-valmsg-for="UserName" data-valmsg-replace="true"></span>
            </div>

            <div class="editor-label">
                <label for="Password">Password</label>
            </div>
            <div class="editor-field">
                <input data-val="true" data-val-required="The Password field is required." id="Password" name="Password" type="password" />
                <span class="field-validation-valid" data-valmsg-for="Password" data-valmsg-replace="true"></span>
            </div>

            <div class="editor-label">
                <input data-val="true" data-val-required="The Remember me? field is required." id="RememberMe" name="RememberMe" type="checkbox" value="true" /><input name="RememberMe" type="hidden" value="false" />
                <label for="RememberMe">Remember me?</label>
            </div>

            <p>
                <input type="submit" value="Log On" style="display:none;" id="WorkaroundForOperaInputFocusBorderBug" />
                <input type="submit"  class="boton-big-red" value="Log On" />
            </p>
        </fieldset>
    </div>
</form>

<%
 } else {
%>
	Bienvenido <%=user.getUserName()%>!
<%
 }
%>