<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import="georeduy.backend.model.*, java.util.List" %>

<%
	String reportData = (String)request.getAttribute("ReportData");
	if (reportData == null)
		reportData = "[]";
	String report = (String)request.getParameter("Report");
	if (report == null)
		report = "";
	String errorMsg = (String)request.getAttribute("ErrorMsg");
 %>


<%=(errorMsg!=null)?errorMsg:""%>

<% if (report.equals("Purchase")) {%>

 
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
    <script src="http://code.jquery.com/jquery-1.8.2.js"></script>
    <script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
    <script language="javascript" type="text/javascript" src="/GeoRedUy.BackEnd/scripts/jquery.flot.js"></script>
    <script>
    $(function() {
        $( "#fechaDesdeString" ).datepicker({
            onSelect: function(fecha,dtpicker){
         			var time = new Date(fecha).getTime();
             			       	$('#fechaDesde').val(time);
                }
        });
        $( "#fechaHastaString" ).datepicker({
            onSelect: function(fecha,dtpicker){
         			var time = new Date(fecha).getTime();
             			       	$('#fechaHasta').val(time);
                }
        });

        var d1 = [];
        var dataArray = eval(<%=reportData%>);
        var i =0;
		for(i; i<dataArray.length; i++){
            d1.push([new Date(dataArray[i].date).getTime(),i]);
        }

		var options;
		if(d1.length > 0)
			options = { 	  
	    	        xaxis: {
   	    	                mode: "time",
   	       	             	timeformat: "%m/%d/%y",
   	    	                minTickSize: [1, "day"],
    	                    timezone : "browser",
    	                    min:d1[0][0],
    	                    max:d1[d1.length-1][0]
    	                }};
		else options = {
				mode: "time",
  	             	timeformat: "%m/%d/%y",
	                minTickSize: [1, "day"],
                timezone : "browser",
                min:new Date("1/1/2012").getTime(),
                max:new Date("1/1/2013").getTime()
				};
	    $.plot($("#placeholder"), [d1], options);
    });
    </script>       


     <div id="placeholder" style="width:600px;height:300px;"></div>

	<!--  -->
	<form action="/GeoRedUy.BackEnd/report" method="post">    
		<div class="reg-box big-red" style="margin:0 auto;">
	        <fieldset>
	        	<input type="hidden" name="Report" value="Purchase" />
	                <input type="hidden" id="fechaDesde" name="fechaDesde" value="0"/>
	                <input type="hidden" id="fechaHasta" name="fechaHasta" value="0" />
	            <div class="editor-field">
	                <p>Start Date:</p> <input type="text" id="fechaDesdeString" value="" />
	            </div>
	
	            <div class="editor-label">
	                <p>End Date:</p> <input type="text" id="fechaHastaString" value="" />
	            </div>
	            <p>
	                <input type="submit" value="Generate Report" id="WorkaroundForOperaInputFocusBorderBug" class="boton-big-red" />
	            </p>
	        </fieldset>
	    </div>
	</form>	

<%} else if (report.equals("Visits")) {%>

    <style type="text/css">
      body {
        margin: 0;
        padding: 10px 20px 20px;
        font-family: Arial;
        font-size: 16px;
      }

      #map-container {
        padding: 6px;
        border-width: 1px;
        border-style: solid;
        border-color: #ccc #ccc #999 #ccc;
        -webkit-box-shadow: rgba(64, 64, 64, 0.5) 0 2px 5px;
        -moz-box-shadow: rgba(64, 64, 64, 0.5) 0 2px 5px;
        box-shadow: rgba(64, 64, 64, 0.1) 0 2px 5px;
        width: 600px;
      }

      #map {
        width: 600px;
        height: 400px;
      }

    </style>

    <link rel="stylesheet" href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
    <script src="http://code.jquery.com/jquery-1.8.2.js"></script>
    <script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
    <script language="javascript" type="text/javascript" src="/GeoRedUy.BackEnd/scripts/jquery.flot.js"></script>
    <script>
    $(function() {
        $( "#fechaDesdeString" ).datepicker({
            onSelect: function(fecha,dtpicker){
         			var time = new Date(fecha).getTime();
             			       	$('#fechaDesde').val(time);
                }
        });
        $( "#fechaHastaString" ).datepicker({
            onSelect: function(fecha,dtpicker){
         			var time = new Date(fecha).getTime();
             			       	$('#fechaHasta').val(time);
                }
        });
    });
    </script>       
    <script src="http://maps.google.com/maps/api/js?sensor=false"></script>
    <script type="text/javascript" src="/GeoRedUy.BackEnd/scripts/markerclusterer.js"></script>

    <script type="text/javascript">
      function initialize() {
        var center = new google.maps.LatLng(37.4419, -122.1419);

        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 3,
          center: center,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        });

        var markers = [];


	//  markers propios
		var d1 = [];
		var dataArray = eval(<%=reportData%>);
		var i =0;
		for(i; i<dataArray.length; i++){
		    var lat = dataArray[i].coordinates[1];
		    var lon = dataArray[i].coordinates[0];
		    d1.push(new google.maps.LatLng(lat,lon));
		}
        
        // Mica        

        
        //for (var i = 0; i < 100; i++) {
        i =0;
        for(i; i<d1.length; i++){
        	
	         var latLng = d1[i];
	         var marker = new google.maps.Marker({
	           position: latLng
	         });
	         
          markers.push(marker);
        }
        var markerCluster = new MarkerClusterer(map, markers);
      }
      google.maps.event.addDomListener(window, 'load', initialize);
    </script>    
    <div id="map-container" style="float:left;"><div id="map"></div></div>
	<!--  -->
	<form action="/GeoRedUy.BackEnd/report" method="post" style="float:left; width:35%;">    
		<div class="reg-box big-red" style="margin:0 auto;">
	        <fieldset>
	        	<input type="hidden" name="Report" value="Visits" />
	                <input type="hidden" id="fechaDesde" name="fechaDesde" value="0" style="width:80%"/>
	                <input type="hidden" id="fechaHasta" name="fechaHasta" value="0" style="width:80%"/>
	            <div class="editor-field">
	                <p>Start Date:</p> <input type="text" id="fechaDesdeString" style="width:80%" value="" />
	            </div>
	
	            <div class="editor-label">
	                <p>End Date:</p> <input type="text" id="fechaHastaString" style="width:80%" value="" />
	            </div>
	            <p>
	                <input type="submit" value="Generate Report" style="margin-right:75%" id="WorkaroundForOperaInputFocusBorderBug" class="boton-big-red" />
	            </p>
	        </fieldset>
	    </div>
	</form>	
	


<% } %>
