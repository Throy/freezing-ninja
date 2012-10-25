<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import="georeduy.backend.model.*, java.util.List" %>

<%
    String newSite = (String)request.getAttribute("NewSite");
	String errorMsg = (String)request.getAttribute("ErrorMsg");
	List<Site> sites = (List<Site>)request.getAttribute("Sites");
 %>

<%=(errorMsg!=null)?errorMsg:""%>

<% if (newSite != null) {%>
<form action="?AddSite" method="post">    
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
            
            <div class="editor-label">
                <label for="Address">Address</label>
            </div>
            <div class="editor-field">
                <input data-val="true" data-val-required="The Address field is required." id="Address" name="Address" type="text" value="" />
                <span class="field-validation-valid" data-valmsg-for="Address" data-valmsg-replace="true"></span>
            </div>
            
            <div class="editor-label">
                <label for="Latitude">Latitude</label>
            </div>
            <div class="editor-field">
                <input data-val="true" data-val-required="The Latitude field is required." id="Latitude" name="Latitude" type="text" value="" />
                <span class="field-validation-valid" data-valmsg-for="Latitude" data-valmsg-replace="true"></span>
            </div>
            
            <div class="editor-label">
                <label for="Longitude">Longitude</label>
            </div>
            <div class="editor-field">
                <input data-val="true" data-val-required="The Longitude field is required." id="Longitude" name="Longitude" type="text" value="" />
                <span class="field-validation-valid" data-valmsg-for="Longitude" data-valmsg-replace="true"></span>
            </div>
            
            <div class="editor-label">
                <label for="ImageUrl">Image url</label>
            </div>
            <div class="editor-field">
                <input id="ImageUrl" name="ImageUrl" type="text" value="" />
            </div>
            
            <div class="editor-label">
                <label for="Tags">Tags</label>
            </div>
            <div class="editor-field">
                <input id="Tags" name="Tags" type="text" value="" />
            </div>

            <p>
                <input type="submit" value="Add site" style="display:none;" id="WorkaroundForOperaInputFocusBorderBug" />
                <input type="submit"  class="boton-big-red" value="Add site" />
            </p>
        </fieldset>
    </div>
</form>

<% } %>

<% if (sites != null && sites.size() > 0) { %>
	<ul class="list1" style="margin:0; position:relative;overflow-x:hidden; overflow-y:auto;">
	<% for (Site site: sites) { %>
		<li class="item" style="width:98%;">
		    <div class="shadowl">
		        <div class="shadowr">
		            <div class="int">
		           		<div class="img" style="float:left;">
	                    	<img class="image" style="margin:0;" width="48px" src="/GeoRedUy.BackEnd/media/images/Thumb/Site.png" alt="Image" />
	                    </div>
		                <div class="body">
		                    <div class="title" style="float:left;margin-right:10px;">
		                        <h3 style="margin-bottom:0;"><%=site.getName()%></h3>
		                    </div>
		                    <div style="float:left;">
		                        <%=site.getDescription()%>
		                        <br />
		                        
		                    </div>
		                    <div class="footer">
		                        Address: <%=site.getAddress()%> 
		                    	Latitude: <%=site.getCoordinates()[0]%>
		                    	Longitude: <%=site.getCoordinates()[1]%>
		                        
		                        <div class="likes">
		                        	<% for (Tag tag: site.getTags()) { %>
		                        		<%=tag.getName()%>
		                        	<% } %>
		                        	
		                        	<input class="btn1" type="submit" name="editSite" value="Edit" />
                                	<input class="btn1" type="submit" name="deleteSite" value="Delete" />
		                   		</div>
		                    </div>
		                </div>
		            </div>
		        </div>
		    </div>
		</li>
	<% } %>
	</ul>
<% } %>