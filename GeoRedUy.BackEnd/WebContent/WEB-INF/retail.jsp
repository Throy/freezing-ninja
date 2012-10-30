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
<form action="?AddStore&ListStores" method="post">    
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
<form action="?AddProduct&ListMyProducts" method="post">    
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
	<ul class="list1" style="margin:0; position:relative;overflow-x:hidden; overflow-y:auto;">
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
		                        Latitude: <%=retailStore.getCoordinates()[0]%>
		                    	Longitude: <%=retailStore.getCoordinates()[1]%> 
		                        
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
	                    	<img class="image" style="margin:0;" width="48px" src="/GeoRedUy.BackEnd/media/images/Thumb/Site.png" alt="Image" />
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
