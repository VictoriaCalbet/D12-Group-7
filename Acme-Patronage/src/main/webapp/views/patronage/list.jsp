<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<display:table name="patronages" id="row" requestURI="${requestURI}" pagesize="5">
	
	<jstl:set var="isCancelled" value="${row.isCancelled}"/>	
	
	<jstl:if test="${isCancelled eq true}">
		<jstl:set var="style" value="background-color:Crimson;" />
	</jstl:if>
	<jstl:if test="${isCancelled eq false}">
		<jstl:set var="style" value="background-color:white;" />
	</jstl:if>
	
 	<spring:message code="patronage.project" var="projectHeader" />
	<display:column property="project.title" title="${projectHeader}"
 		sortable="false" style="${style}" />

	<spring:message code="patronage.amount" var="amountHeader" />
	<display:column property="amount" title="${amountHeader}"
 		sortable="false" style="${style}" />
	
	<security:authorize access="hasRole('USER')">
	<spring:message code="patronage.cancel" var="cancelHeader" />
		<display:column title="${cancelHeader}" style="${style}">
			<jstl:choose>
				<jstl:when test="${(principal== row.user and row.isCancelled eq false)}">
					<spring:message var="patronageCancelLink" code="patronage.cancel"/>
					<a href="patronage/user/cancel.do?patronageId=${row.id}"><jstl:out value="${patronageCancelLink}"/></a>
				</jstl:when>
				<jstl:otherwise>
					<spring:message code="patronage.notCancellable" var="patronageNotCancelableMessage" />
					<jstl:out value="${patronageNotCancelableMessage}"/>
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
		
	
	</security:authorize>
	
</display:table>


	<jstl:if test="${not empty patronages}">
		<span style="background-color:Crimson; border-radius: 15px 50px;">&nbsp;&nbsp;<spring:message code="patronage.cancelled"/>&nbsp;&nbsp;</span>
	</jstl:if>