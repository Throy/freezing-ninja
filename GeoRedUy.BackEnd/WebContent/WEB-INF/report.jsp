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
             			       	$('#fechaDesde').attr("value",time);
                }
        });
        $( "#fechaHastaString" ).datepicker({
            onSelect: function(fecha,dtpicker){
         			var time = new Date(fecha).getTime();
             			       	$('#fechaHasta').attr("value",time);
                }
        });

        var d1 = [];
        var dataArray = eval(<%=reportData%>);
		for(i = 0; i<dataArray.length; i++){
            d1.push([new Date(dataArray[i].date).getTime(),i]);
        }
	        var options = {
	    	        xaxis: {
   	    	                mode: "time",
   	       	             	timeformat: "%m/%d/%y",
   	    	                minTickSize: [1, "day"],
    	                    timezone : "browser",
    	                    min:d1[0][0],
    	                    max:d1[d1.length-1][0]
    	                }};
	    $.plot($("#placeholder"), [d1], options);
    });
    </script>       
 

    <div id="placeholder" style="width:600px;height:300px;"></div>
	
	<!--  -->
	<form action="/GeoRedUy.BackEnd/report" method="post">    
		<div class="reg-box big-red" style="margin:0 auto;">
	        <fieldset>
	        	<input type="hidden" name="Report" value="Purchase" />
	            <div class="editor-field">
	                <p>Start Date: <input type="text" id="fechaDesdeString" /><input type="hidden" id="fechaDesde" /></p>
	            </div>
	
	            <div class="editor-label">
	                <p>End Date: <input type="text" id="fechaHastaString" /><input type="hidden" id="fechaHasta" /></p>
	            </div>
	            <p>
	                <input type="submit" value="Generate Report" id="WorkaroundForOperaInputFocusBorderBug" class="boton-big-red" />
	            </p>
	        </fieldset>
	    </div>
	</form>	

<%} else if (report.equals("Visits")) {%>

<% } %>
