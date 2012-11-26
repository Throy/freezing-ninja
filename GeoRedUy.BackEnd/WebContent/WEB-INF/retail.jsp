<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import="georeduy.backend.model.*, java.util.List" %>

<%
    String newForm = (String)request.getParameter("NewForm");
	if (newForm == null)
		newForm = "";
	String errorMsg = (String)request.getAttribute("ErrorMsg");
	List<Retailer> retailers = (List<Retailer>)request.getAttribute("Retailers");
	List<RetailStore> stores = (List<RetailStore>)request.getAttribute("Stores");
	RetailStore store = (RetailStore)request.getAttribute("Store");
	List<Product> products = (List<Product>)request.getAttribute("Products");
 %>


<%=(errorMsg!=null)?errorMsg:""%>

<% if (newForm.equals("Retailer")) {%>
<form action="?AddRetailer&ListRetailers" method="post">    
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
                <label for="ImageUrl">Image url</label>
            </div>
            <div class="editor-field">
                <input id="ImageUrl" name="ImageUrl" type="text" value="" />
            </div>
            
            <div class="editor-label">
                <label for="UserName">User name</label>
            </div>
            <div class="editor-field">
                <input data-val="true" data-val-required="The UserName field is required." id="UserName" name="UserName" type="text" value="" />
                <span class="field-validation-valid" data-valmsg-for="UserName" data-valmsg-replace="true"></span>
            </div>

            <p>
                <input type="submit" value="Add retailer" style="display:none;" id="WorkaroundForOperaInputFocusBorderBug" />
                <input type="submit"  class="boton-big-red" value="Add retailer" />
            </p>
        </fieldset>
    </div>
</form>

<%} else if (newForm.equals("Store")) {%>

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

<form action="?AddStore&ListMyStores" method="post">    
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
                <label for="Address">Address</label>
            </div>
            <div class="editor-field">
                <input data-val="true" data-val-required="The Address field is required." id="Address" name="Address" type="text" value="" />
                <span class="field-validation-valid" data-valmsg-for="Address" data-valmsg-replace="true"></span>
            </div>

            <div class="editor-label">
                <label for="PhoneNumber">Contact number</label>
            </div>
            <div class="editor-field">
                <input data-val="true" data-val-required="The PhoneNumber field is required." id="PhoneNumber" name="PhoneNumber" type="text" value="" />
                <span class="field-validation-valid" data-valmsg-for="PhoneNumber" data-valmsg-replace="true"></span>
            </div>
            
            <div class="editor-label">
                <label for="PayPalMail">PayPal Account Mail</label>
            </div>
            <div class="editor-field">
                <input  id="PayPalMail" name="PayPalMail" type="text" value="" />
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
            
            <p>
                <input type="submit" value="Add store" style="display:none;" id="WorkaroundForOperaInputFocusBorderBug" />
                <input type="submit"  class="boton-big-red" value="Add store" />
            </p>
        </fieldset>
    </div>
</form>
<% } else if (newForm.equals("Product")) {%>

<form action="?AddProduct&ListMyProducts" method="post" enctype="multipart/form-data">    
	<div class="reg-box big-red" style="margin-left:40%; float:left">
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
                <label for="imageData">Imagen</label>
            </div>
            <div class="editor-field">
                <input type="file" name="imageData" size="50" />
            </div>
            
            <div class="editor-label">
                <label for="Price">Price</label>
            </div>
            <div class="editor-field">
                <input data-val="true" data-val-required="The Price field is required." id="Price" name="Price" type="text" value="" />
                <span class="field-validation-valid" data-valmsg-for="Price" data-valmsg-replace="true"></span>
            </div>

            <p>
                <input type="submit" value="Add product" style="display:none;" id="WorkaroundForOperaInputFocusBorderBug" />
                <input type="submit"  class="boton-big-red" value="Add product" />
            </p>
        </fieldset>
    </div>
</form>
<% } %>

<% if (retailers != null && retailers.size() > 0) { %>
	<ul class="list1" style="margin:0; position:relative;overflow-x:hidden; overflow-y:auto; float:right">
	<% for (Retailer retailer: retailers) { %>
		<li class="item" style="width:98%;">
		    <div class="shadowl">
		        <div class="shadowr">
		            <div class="int">
		           		<div class="img" style="float:left;">
	                    	<img class="image" style="margin:0;" width="48px" src="/GeoRedUy.BackEnd/media/images/Thumb/Site.png" alt="Image" />
	                    </div>
		                <div class="body">
		                    <div class="title" style="float:left;margin-right:10px;">
		                        <h3 style="margin-bottom:0;"><%=retailer.getName()%></h3>
		                    </div>
		                    <div style="float:left;">
		                        <%=retailer.getDescription()%>
		                        <br />
		                        
		                    </div>
		                    <div class="footer">
		                        User: <%=retailer.getUser().getUserName()%> 
		                        
		                        <div class="likes">		                        	
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

<% if (store != null) { %>
	<div class="shadowl">
		        <div class="shadowr">
		            <div class="int">
		           		<div class="img" style="float:left;">
	                    	<img class="image" style="margin:0;" width="48px" src="/GeoRedUy.BackEnd/media/images/Thumb/Site.png" alt="Image" />
	                    </div>
		                <div class="body">
		                    <div class="title" style="float:left;margin-right:10px;">
		                        <h3 style="margin-bottom:0;"><%=store.getName()%></h3>
		                    </div>
		                    <div style="float:left;">
		                        <%=store.getAddress()%>
		                        <br />
		                        
		                    </div>
		                    <div class="footer">
		                        Contact number: <%=store.getPhoneNumber()%>
		                        Latitude: <%=store.getCoordinates()[0]%>
		                    	Longitude: <%=store.getCoordinates()[1]%> 
		                        
		                        <div class="likes">		                        	
		                        	<input class="btn1" type="submit" name="editSite" value="Edit" />
                                	<input class="btn1" type="submit" name="deleteSite" value="Delete" />
		                   		</div>
		                    </div>
		                </div>
		            </div>
		        </div>
		    </div>
		    
<form action="?AddStoreProduct&StoreId=<%=store.getId()%>&ShowStore=<%=store.getId()%>" method="post">    
	<div class="reg-box big-red" style="margin:0 auto;">
        <fieldset>
            <div class="editor-label">
                <label for="Name">Name</label>
            </div>
            <div class="editor-field">
                <input data-val="true" data-val-required="The Name field is required." id="Name" name="Name" type="text" value="" />
                <span class="field-validation-valid" data-valmsg-for="Name" data-valmsg-replace="true"></span>
            </div>
            <p>
                <input type="submit" value="Add product" style="display:none;" id="WorkaroundForOperaInputFocusBorderBug" />
                <input type="submit"  class="boton-big-red" value="Add product to store" />
            </p>
        </fieldset>
    </div>
</form>
<% } %>
<% if (stores != null && stores.size() > 0) { %>
	<ul class="list1" style="margin:0; position:relative;overflow-x:hidden; overflow-y:auto;">
	<% for (RetailStore retailStore: stores) { %>
		<li class="item" style="width:98%;">
			<a href="/GeoRedUy.BackEnd/retail?ShowStore=<%=retailStore.getId()%>">
		    <div class="shadowl">
		        <div class="shadowr">
		            <div class="int">
		           		<div class="img" style="float:left;">
	                    	<img class="image" style="margin:0;" width="48px" src="/GeoRedUy.BackEnd/media/images/Thumb/Site.png" alt="Image" />
	                    </div>
		                <div class="body">
		                    <div class="title" style="float:left;margin-right:10px;">
		                        <h3 style="margin-bottom:0;"><%=retailStore.getName()%></h3>
		                    </div>
		                    <div style="float:left;">
		                        <%=retailStore.getAddress()%>
		                        <br />
		                        
		                    </div>
		                    <div class="footer">
		                        Contact number: <%=retailStore.getPhoneNumber()%>
		                        Latitude: <%=retailStore.getCoordinates()[1]%>
		                    	Longitude: <%=retailStore.getCoordinates()[0]%> 
		                        
		                        <div class="likes">		                        	
		                        	<input class="btn1" type="submit" name="editSite" value="Edit" />
                                	<input class="btn1" type="submit" name="deleteSite" value="Delete" />
		                   		</div>
		                    </div>
		                </div>
		            </div>
		        </div>
		    </div>
		    </a>
		</li>
	<% } %>
	</ul>
<% } %>

<% if (products != null && products.size() > 0) { %>
	<ul class="list1" style="margin:0; position:relative;overflow-x:hidden; overflow-y:auto;">
	<% for (Product product: products) { %>
		<li class="item" style="float:left;width:300px;margin-left:20px;clear:none;">
		    <div class="shadowl">
		        <div class="shadowr">
		            <div class="int" style="height:75px">
		           		<div class="img" style="float:left;">
	                    	<img class="image" style="margin:0;" width="48px" src="/GeoRedUy.Server/Products/GetImageById?productId=<%=product.getId()%>" alt="Image" />
	                    </div>
		                <div class="body">
		                    <div class="title" style="float:left;margin-right:10px;">
		                        <h3 style="margin-bottom:0;"><%=product.getName()%></h3>
		                    </div>
		                    <div style="float:left; width:210px">
		                        <%=product.getDescription()%>
		                        <br />
		                        U$D <%=product.getPrice()%>
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

