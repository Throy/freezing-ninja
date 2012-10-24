<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import="georeduy.backend.model.*, java.util.List" %>

<%
    String newTag = (String)request.getAttribute("NewTag");
	String errorMsg = (String)request.getAttribute("ErrorMsg");
	List<Tag> tags = (List<Tag>)request.getAttribute("Tags");
 %>

Estas en sites!

<%=(errorMsg!=null)?errorMsg:""%>

<% if (newTag != null) {%>
<form action="?AddTag" method="post">    
	<div class="reg-box big-red" style="margin:0 auto;">
        <fieldset>
            <div class="editor-label">
                <label for="Name">Name</label>
            </div>
            <div class="editor-field">
                <input data-val="true" data-val-required="The Name field is required." id="Name" name="Name" type="text" value="" />
                <span class="field-validation-valid" data-valmsg-for="Name" data-valmsg-replace="true"></span>
            </div>

            <div class="editor-label">
                <label for="Description">Description</label>
            </div>
            <div class="editor-field">
                <input data-val="true" data-val-required="The Description field is required." id="Description" name="Description" type="text" value="" />
                <span class="field-validation-valid" data-valmsg-for="Description" data-valmsg-replace="true"></span>
            </div>
      
            <p>
                <input type="submit" value="Add site" style="display:none;" id="WorkaroundForOperaInputFocusBorderBug" />
                <input type="submit"  class="boton-big-red" value="Add tag" />
            </p>
        </fieldset>
    </div>
</form>

<% } %>

<% if (tags != null && tags.size() > 0) { %>
	<ul class="list1" style="margin:0; position:relative;overflow-x:hidden; overflow-y:auto;">
	<% for (Tag tag: tags) { %>
		<li class="item" style="width:98%;">
		    <div class="shadowl">
		        <div class="shadowr">
		            <div class="int">
		                <div class="body">
		                    <div class="title" style="float:left;margin-right:10px;">
		                        <h3 style="margin-bottom:0;"><%=tag.getName()%></h3>
		                    </div>
		                    <div style="float:left;">
		                        <%=tag.getDescription()%>
		                        <br />
		                    </div>
	
	                        <div class="likes">
	                        	<input class="btn1" type="submit" name="editSite" value="Edit" />
                               	<input class="btn1" type="submit" name="deleteSite" value="Delete" />
	                   		</div>
		                </div>
		            </div>
		        </div>
		    </div>
		</li>
	<% } %>
	</ul>
<% } %>