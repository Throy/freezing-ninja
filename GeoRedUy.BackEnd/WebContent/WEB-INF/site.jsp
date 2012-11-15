<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import="georeduy.backend.model.*, java.util.List, georeduy.backend.util.Base64" %>

<%
    String newSite = (String)request.getAttribute("NewSite");
	String errorMsg = (String)request.getAttribute("ErrorMsg");
	List<Site> sites = (List<Site>)request.getAttribute("Sites");
 %>

<%=(errorMsg!=null)?errorMsg:""%>

<% if (newSite != null) {%>
<script type="text/javascript"
    src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDMxElmlzDp8EFWG0el5fZxRrRzN3E3NRU&sensor=false">
</script>

<script type="text/javascript">
    var map;
    var markersArray = [];
    function googleMapsInitialize() {
        // initialize map
        var myOptions = {
            center: new google.maps.LatLng(-34.892830, -56.130030),
            zoom: 16,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
        // setting the function that will respond onclick (create a marker)
        google.maps.event.addListener(map, 'click', function (event) {
            placeMarker(event.latLng);
        });
        // Delete Markers Function
        google.maps.Map.prototype.clearOverlays = function () {
            if (markersArray) {
                for (var i = 0; i < markersArray.length; i++) {
                    markersArray[i].setMap(null);
                }
            }
        }
    }
    function placeMarker(location) {
        map.clearOverlays();
        var marker = new google.maps.Marker({
            position: location,
            map: map
        });
        markersArray.push(marker);
        document.getElementById("Longitude").value = marker.getPosition().lng().toString();
        document.getElementById("Latitude").value = marker.getPosition().lat().toString();
    }

 </script>
<div id="map_canvas" style="width: 500px; height:385px; margin-top:35px; margin-left:50px; margin-bottom:20px; border-style:outset; border-width:2px; border-color:Gray; box-shadow:0 1px 2px #aaa; -moz-box-shadow:0 2px 3px #AAAAAA; float:left"  ></div>
<script type="text/javascript">googleMapsInitialize();</script>

<form action="/GeoRedUy.Server/Sites/add" method="post" enctype="multipart/form-data">    
	<div class="reg-box big-red" style="margin:20px 40px 0px auto; float:right">
        <fieldset>
            <div class="editor-label">
                <label for="Name">Nombre</label>
            </div>
            <div class="editor-field">
                <input data-val="true" data-val-required="The Name field is required." id="name" name="name" type="text" value="" />
                <span class="field-validation-valid" data-valmsg-for="Name" data-valmsg-replace="true"></span>
            </div>

            <div class="editor-label">
                <label for="Description">Descripción</label>
            </div>
            <div class="editor-field">
                <input data-val="true" data-val-required="The Description field is required." id="description" name="description" type="text" value="" />
                <span class="field-validation-valid" data-valmsg-for="Description" data-valmsg-replace="true"></span>
            </div>
            
            <div class="editor-label">
                <label for="Address">Dirección</label>
            </div>
            <div class="editor-field">
                <input data-val="true" data-val-required="The Address field is required." id="address" name="address" type="text" value="" />
                <span class="field-validation-valid" data-valmsg-for="Address" data-valmsg-replace="true"></span>
            </div>
            
            <div class="editor-label">
                <label for="Latitude">Latitud</label>
            </div>
            <div class="editor-field">
                <input data-val="true" data-val-required="The Latitude field is required." id="latitude" name="latitude" type="text" value="" />
                <span class="field-validation-valid" data-valmsg-for="Latitude" data-valmsg-replace="true"></span>
            </div>
            
            <div class="editor-label">
                <label for="Longitude">Longitud</label>
            </div>
            <div class="editor-field">
                <input data-val="true" data-val-required="The Longitude field is required." id="longitude" name="longitude" type="text" value="" />
                <span class="field-validation-valid" data-valmsg-for="Longitude" data-valmsg-replace="true"></span>
            </div>
            
            <div class="editor-label">
                <label for="Radius">Radio (metros)</label>
            </div>
            <div class="editor-field">
                <input data-val="true" data-val-required="Por favor ingrese el radio." id="Radius" name="Radius" type="text" value="" />
                <span class="field-validation-valid" data-valmsg-for="Radius" data-valmsg-replace="true"></span>
            </div>
            
            <div class="editor-label">
                <label for="ImageUrl">URL de imagen</label>
            </div>
            <div class="editor-field">
                <input type="file" name="imageData" size="50" />
            </div>
            
            <div class="editor-label">
                <label for="Tags">Etiquetas</label>
            </div>
            <div class="editor-field">
                <input id="Tags" name="tags" type="text" value="" />
            </div>

            <p>
                <input type="submit" value="Agregar sitio" style="display:none;" id="WorkaroundForOperaInputFocusBorderBug" />
                <input type="submit"  class="boton-big-red" value="Add site" />
            </p>
        </fieldset>
    </div>
</form>

<% } %>

<% if (sites != null && sites.size() > 0) { %>
	<ul class="list1" style="margin:0; position:relative;overflow-x:hidden; overflow-y:auto; float:right">
	<% for (Site site: sites) { %>
		<li class="item" style="width:98%;">
		    <div class="shadowl">
		        <div class="shadowr">
		            <div class="int">
		           		<div class="img" style="float:left;">
	                    	<img class="image" style="margin:0;" width="48px" src='data:image/gif;base64,<%=Base64.encodeBytes(site.getImage())%>' alt="Image" />
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
