<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import="georeduy.backend.model.*, java.util.List" %>

<%
    String newEvent = (String)request.getAttribute("NewEvent");
	String errorMsg = (String)request.getAttribute("ErrorMsg");
	List<Event> events = (List<Event>)request.getAttribute("Events");
 %>
<script type="text/javascript" src="/GeoRedUy.BackEnd/scripts/jquery.validate.min.js"></script>
<script type="text/javascript" src="/GeoRedUy.BackEnd/scripts/jquery.validate.unobtrusive.min.js"></script>
<script type="text/javascript" src="/GeoRedUy.BackEnd/scripts/jquery-ui-1.8.11.js"></script>
<link href="/GeoRedUy.BackEnd/scripts/themes/base/jquery.ui.all.css" rel="stylesheet" type="text/css"/>


<script type="text/javascript">
    $(document).ready(function () { $('.date').datepicker({ dateFormat: "dd/mm/yy" }); });   
</script>

<%=(errorMsg!=null)?errorMsg:""%>

<% if (newEvent != null) {%>
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

<form action="?AddEvent" method="post">    
	<div class="reg-box big-red" style="margin:20px 40px 0px auto; float:right">
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
            <label>Beginning date</label>
            </div>
            <div class="editor-field" >
                <input class="date" id="dateBegin" name="dateBegin" readonly="readonly" type="text" value="11/07/2012" />
            </div>            
            <div class="editor-label">
                <label for="Tags">Tags</label>
            </div>
            <div class="editor-field">
                <input id="Tags" name="Tags" type="text" value="" />
            </div>

            <p>
                <input type="submit" value="Add event" style="display:none;" id="WorkaroundForOperaInputFocusBorderBug" />
                <input type="submit"  class="boton-big-red" value="Add event" />
            </p>
        </fieldset>
    </div>
</form>

<% } %>

<% if (events != null && events.size() > 0) { %>
	<ul class="list1" style="margin:0; position:relative;overflow-x:hidden; overflow-y:auto; float:right">
	<% for (Event event: events) { %>
		<li class="item" style="width:98%;">
		    <div class="shadowl">
		        <div class="shadowr">
		            <div class="int">
		           		<div class="img" style="float:left;">
	                    	<img class="image" style="margin:0;" width="48px" src="/GeoRedUy.BackEnd/media/images/Thumb/Site.png" alt="Image" />
	                    </div>
		                <div class="body">
		                    <div class="title" style="float:left;margin-right:10px;">
		                        <h3 style="margin-bottom:0;"><%=event.getName()%></h3>
		                    </div>
		                    <div style="float:left;">
		                        <%=event.getDescription()%>
		                        <%DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); %>
		                        <%=df.format(event.getDate())%> 
		                        <br />
		                        
		                    </div>
		                    <div class="footer">
		                        Address: <%=event.getAddress()%> 
		                    	Latitude: <%=event.getCoordinates()[0]%>
		                    	Longitude: <%=event.getCoordinates()[1]%>
		                        
		                        <div class="likes">
		                        	<% for (Tag tag: event.getTags()) { %>
		                        		<%=tag.getName()%>
		                        	<% } %>
		                        	
		                        	<input class="btn1" type="submit" name="editEvent" value="Edit" />
                                	<input class="btn1" type="submit" name="deleteEvent" value="Delete" />
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