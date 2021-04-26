<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>

<cheapy:layout pageName="usuarios">
    <h2 style="font-family: 'Lobster'; text-align:center; font-size:200%;  color: rgb(0, 64, 128); padding:10px">
        <fmt:message key="CambiarContraseña"/>
    </h2>
    
    <form:form modelAttribute="usuario" class="form-horizontal" id="add-usuario-form">
        <div class="form-group has-feedback">
            <form:hidden path="nombre"/>
	        <form:hidden path="apellidos"/>
	        <form:hidden path="municipio"/>
	        <form:hidden path="direccion"/>
	        <form:hidden path="email"/>
            <cheapy:passwordField label="Nueva contraseña"  name="usuar.password"/>
					        
            
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            	<div class="btn-mod">
	                        <button class="btn btn-default" type="submit" style="font-family: 'Lobster'; font-size: 20px;">
	                        <span class="glyphicon glyphicon-floppy-save" aria-hidden="true" style="padding: 5px"> </span>
	                        Modificar</button>
                </div>
            </div>
        </div>
    </form:form>
    	<script>
			function myFunction() {
				
				
				  var x = document.getElementById("myInput");
				  if (x.type === "password") {
				    x.type = "text";
				    $( "#showPassword" ).prop( "checked", true );
				  } else {
				    x.type = "password";
				    $( "#showPassword" ).prop( "checked", false );
				  }
				}
	
		</script>
    
</cheapy:layout>