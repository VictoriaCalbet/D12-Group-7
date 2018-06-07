<%--
 * display.jsp
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

<form:form action="${actionURI}" modelAttribute="awardForm">
	<form:hidden path="id"/>
	<form:hidden path="projectId"/>
	<form:hidden path="userId"/>
	<form:hidden path="minimumPatronageAmount"/>
	
	<acme:textbox code="award.name" path="name"/>
	<acme:textbox code="award.description" path="description"/>
	<acme:textbox code="award.moneyGoal" path="moneyGoal"/>
	
	<spring:message code="award.project.minimumPatronageAmount.information" var="awardProjectMinimumPatronageAmountInformation"/>
	<br/>
	<b><jstl:out value="${awardProjectMinimumPatronageAmountInformation} ${awardForm.minimumPatronageAmount}"/></b>
	<br/>
	
	<br/>
	<acme:submit name="save" code="award.save"/> &nbsp;
	<acme:cancel url="${cancelURI}" code="award.cancel"/>
	<br/>
</form:form>