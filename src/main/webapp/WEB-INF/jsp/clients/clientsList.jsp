<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>

<cheapy:layout pageName="clients">
    <h2 style="font-family: 'Lobster'; text-align:center; font-size:200%;  color: rgb(0, 64, 128); padding:10px"><fmt:message key="clients"/></h2>
    
	<c:if test="${empty clientLs }">
		<p id="vacio" >No hay ningun Cliente.</p>
	</c:if>
	<c:if test="${not empty clientLs }">
    <table id="clientTable" class="table table-striped">
        <thead>
        <tr>

        	<th><fmt:message key="name"/></th>
        	<th><fmt:message key="user"/></th>
        	<th><fmt:message key="enabled"/></th>   
            <th> </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${clientLs}" var="client">
            <tr>

                <td>
                    <c:out value="${client.name}"/>
                </td>
                <td>
                    <c:out value="${client.usuar.username}"/>
                </td>
                <td>
                	<c:if test="${client.usuar.enabled == 'true'}">
		            	<c:out value="Si"/>
		            </c:if>	
		            <c:if test="${client.usuar.enabled == 'false'}">
		                 <c:out value="No"/>
		             </c:if>
                </td>
                <td>
	                <spring:url value="/administrators/clients/{username}" var="clientUrl">
	                        <spring:param name="username" value="${client.usuar.username}"/>
	                </spring:url>
	                <div class="btn-detalles">
                		<button type="button" role="link" onclick="window.location='${fn:escapeXml(clientUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
                		<span class="glyphicon glyphicon-info-sign" aria-hidden="true" style="padding: 5px"> </span>
	                	<fmt:message key="details"/></button>
            		</div>
                </td>     
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </c:if>
</cheapy:layout>
