<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>

<cheapy:layout pageName="client">

    <h2 style="font-family: 'Lobster'; text-align:center; font-size:200%;  color: rgb(0, 64, 128); padding:10px; text-transform: uppercase;"><c:out value="${client.name}"/></h2>


	
    <table class="table table-striped" id="clientTable">
    	<thead>
        <tr>
            <th><fmt:message key="clientInit"/></th>
            <td><c:out value="${client.init}h"/></td>
        </tr>
        <tr>
            <th><fmt:message key="clientFinish"/></th>
            <td><c:out value="${client.finish}h"/></td>
        </tr>
		<tr>
            <th><fmt:message key="name"/></th>
            <td><c:out value="${client.name}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="email"/></th>
            <td><c:out value="${client.email}"/> </td>
        </tr>
        
        <tr>
            <th><fmt:message key="addressClient"/></th>
            <td><c:out value="${client.address}"/> </td>
        </tr><tr>
            <th><fmt:message key="telephone"/></th>
            <td><c:out value="${client.telephone}"/> </td>
        </tr><tr>
            <th><fmt:message key="descriptionClient"/></th>
            <td><c:out value="${client.description}"/> </td>
        </tr><tr>
            <th><fmt:message key="foodClient"/></th>
            <td><c:out value="${client.food}"/> </td>
   
        
        
        
        </thead>
    </table>
    <div style="font-size: 150%" >
	    <sec:authorize access="hasAnyAuthority('usuario')">
		    <fmt:message key="reviews"/>
				<cheapy:showStars  value="${reviews}"></cheapy:showStars> 
		</sec:authorize>
	</div>
    <div class="btn-menu">
  
    
      <div class="text-left">
    	<spring:url value="/reviewsClientList/{client}/0" var="reviewsListUrl">
    		<spring:param name="client" value="${client.usuar.username}"/>
    	</spring:url>
    	<button type="button" class="btn-pag" role="link" onclick="window.location='${fn:escapeXml(reviewsListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
		Valoraciones</button>
 	
 	<sec:authorize access="hasAnyAuthority('usuario')">
 	     
    	<spring:url value="/reviewsClient/new/{client}/" var="reviewsCreateUrl">
    		<spring:param name="client" value="${client.usuar.username}"/>
    	</spring:url>
    	<button type="button" class="btn-pag" role="link" onclick="window.location='${fn:escapeXml(reviewsCreateUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
		Valorar</button>
 	
 	</sec:authorize>
  	</div>
  	</div>

</cheapy:layout>
