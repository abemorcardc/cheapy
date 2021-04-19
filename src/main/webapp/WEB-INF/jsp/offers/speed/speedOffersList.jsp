<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<cheapy:layout pageName="ofertas por rapidez">
	<script>
		function pagNum(pagina) {
			paginaAct = pagina + 1;
			document.write("Página " + paginaAct + " <br />");
		}
		$(document).ready(function(){
		  $('[data-toggle="desplegable"]').popover();   
		});
	</script>
	<div class="text-center">
		<div class="btn-filter-max">
			<spring:url value="/offers/foodOfferList/{page}" var="foodOfferListUrl">
				<spring:param name="page" value="0"/>
		    </spring:url>
		    <button type="button" role="link" class="btn-filter" onclick="window.location='${fn:escapeXml(foodOfferListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
			<span class="glyphicon 	glyphicon glyphicon-cutlery" aria-hidden="true" style="padding: 5px"> </span>
			Ofertas de plato específico</button>
			
			<spring:url value="/offers/nuOfferList/{page}" var="nuOfferListUrl">
				<spring:param name="page" value="0"/>
		    </spring:url>
		    <button type="button" role="link" class="btn-filter" onclick="window.location='${fn:escapeXml(nuOfferListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
			<span class="glyphicon 	glyphicon glyphicon-cutlery" aria-hidden="true" style="padding: 5px"> </span>
			Ofertas por número de comensales</button>
			
			<spring:url value="/offers/speedOfferList/{page}" var="speedOfferListUrl">
				<spring:param name="page" value="0"/>
		    </spring:url>
		    <button type="button" role="link" class="btn-filter-active" onclick="window.location='${fn:escapeXml(speedOfferListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
			<span class="glyphicon 	glyphicon glyphicon-cutlery" aria-hidden="true" style="padding: 5px"> </span>
			Ofertas de velocidad</button>
			
			<spring:url value="/offers/timeOfferList/{page}" var="timeOfferListUrl">
				<spring:param name="page" value="0"/>
		    </spring:url>
		    <button type="button" role="link" class="btn-filter" onclick="window.location='${fn:escapeXml(timeOfferListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
			<span class="glyphicon 	glyphicon glyphicon-cutlery" aria-hidden="true" style="padding: 5px"> </span>
			Ofertas de franja horaria</button>
		</div>
	</div>
    <h2 style="font-family: 'Lobster'; text-align:center; font-size:200%;  color: rgb(0, 64, 128); padding:10px"><fmt:message key="speedOffers"/>
    	<a title="Informacion" data-toggle="desplegable" data-trigger="hover" data-placement="bottom" data-content="Descuento al consumir en menos de alguno de los tres posibles tiempos">
    	<span class="glyphicon glyphicon-question-sign" aria-hidden="true" style="padding: 5px"> </span></a>
    </h2>
	<c:if test="${empty speedOfferLs }">
		<p id="vacio" >No hay ninguna oferta por tiempo empleado en comer activa.</p>
	</c:if>
	<c:if test="${not empty speedOfferLs }">
	<div class="table-responsive">
	    <table id="speedOfferTable" class="table table-striped">
	        <thead>
	        <tr>
	        	<!-- <th style="width: 150px;">Restaurante</th> -->
	            <th><fmt:message key="name"/></th>
	            <th><fmt:message key="startDate"/></th>
	            <th><fmt:message key="endDate"/></th>
	            <th><fmt:message key="goldGoal"/></th>
	            <th><fmt:message key="goldDiscount"/></th>
	            <th><fmt:message key="municipio"/></th>
	            <th> </th>
	            
	        </tr>
	        </thead>
	        <tbody>
	        <c:forEach items="${speedOfferLs}" var="speedOffer">
	            <tr>
	                <td>
	                    <c:out value="${speedOffer.client.name}"/>
	                </td>
	                <td>
	                    <c:out value="${localDateTimeFormat.format(speedOffer.start)}"/>
	                </td>
	                <td>
	                    <c:out value="${localDateTimeFormat.format(speedOffer.end)}"/>
	                </td>
	                <td>
	                    <c:out value="${speedOffer.gold} minutos"/>
	                </td>
	                <td>
	                    <c:out value="${speedOffer.discountGold}%"/>
	                </td>
	                
	                <td>
	                    <c:out value="${speedOffer.client.municipio}"/>
	                </td>
	                
	                <td>
	                    <spring:url value="/offers/speed/{speedOfferId}" var="speedOfferUrl">
	                        <spring:param name="speedOfferId" value="${speedOffer.id}"/>
	                    </spring:url>
	                    <div class="btn-detalles">
		                    <button type="button" role="link" onclick="window.location='${fn:escapeXml(speedOfferUrl)}'" class="btn-detalles" style="font-family: 'Lobster'; font-size: 20px;">
		                    <span class="glyphicon glyphicon-info-sign" aria-hidden="true" style="padding: 5px"> </span>
		                    <fmt:message key="details"/> </button>
		                </div>
	                </td>
	                  
	            </tr>
	        </c:forEach>
	        </tbody>
	    </table>
    </div>
    <div class="text-center">
    	<script type="text/javascript">
			          
    		pagNum(${page});
								
		</script>
    </div>
    <div>
	    <c:if test='${page!=0}'>
		    <div class="text-left">
		    	<spring:url value="/offers/speedOfferList/{page}" var="speedOfferListUrl">
		    		<spring:param name="page" value="${page-1}"/>
		    	</spring:url>
		    	<button type="button" class="btn-pag" role="link" onclick="window.location='${fn:escapeXml(speedOfferListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
				<span class="glyphicon 	glyphicon glyphicon-arrow-left" aria-hidden="true" style="padding: 5px"> </span>
				Pág. anterior</button>
			</div>
	    </c:if>
	    
	    <c:if test="${fn:length(speedOfferLs) == 5}">
		    <div class="text-right">
		    	<spring:url value="/offers/speedOfferList/{page}" var="speedOfferListUrl">
		    		<spring:param name="page" value="${page+1}"/>
		    	</spring:url>
		    	<button type="button" class="btn-pag" role="link" onclick="window.location='${fn:escapeXml(speedOfferListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
				Pág. siguiente
				<span class="glyphicon 	glyphicon glyphicon-arrow-right" aria-hidden="true" style="padding: 5px"> </span></button>
			</div>
		</c:if>
	</div>	
    </c:if>
</cheapy:layout>
