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

<div>
	<b><spring:message code="user.fullName" />: </b>
	<jstl:out value="${user.fullName}" /><br>
	
	<b><spring:message code="user.email" />: </b>
	<jstl:out value="${user.email}" /><br>
	
	<b><spring:message code="user.bio" />: </b>
	<jstl:out value="${user.bio}" /><br>
	
	<b><spring:message code="user.telephone" />: </b>
	<jstl:out value="${user.telephone}" /><br>
	
	<b><spring:message code="user.username" />: </b>
	<jstl:out value="${user.userAccount.username}" /><br><br>
</div>

<b><spring:message code="user.createdProjects" />: </b><br>

<display:table name="createdProjects" id="row" requestURI="${requestURI}" pagesize="5">

	<spring:message code="project.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" style="${style}" />
	
	<spring:message code="project.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" style="${style}" />
	
	<spring:message code="project.economicGoal" var="economicGoalHeader" />
	<display:column title="${economicGoalHeader}" style="${style}">
        <fmt:formatNumber value = "${row.economicGoal}" currencySymbol="&euro;" pattern="${patternCurrency}" type = "currency"  minFractionDigits="2"/>
	</display:column>
	
	<spring:message code="project.dueDate" var="dueDateHeader" />
	<spring:message code="project.creationMoment.pattern" var="datePattern"/>
	<display:column title="${dueDateHeader}" style="${style}">
		<fmt:formatDate value="${row.dueDate}" pattern="${datePattern}"/>
	</display:column>
	
</display:table><br>

<b><spring:message code="user.fundedProjects" />: </b><br>

<display:table name="fundedProjects" id="row" requestURI="${requestURI}" pagesize="5">

	<spring:message code="project.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" style="${style}" />
	
	<spring:message code="project.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" style="${style}" />
	
	<spring:message code="project.economicGoal" var="economicGoalHeader" />
	<display:column title="${economicGoalHeader}" style="${style}">
        <fmt:formatNumber value = "${row.economicGoal}" currencySymbol="&euro;" pattern="${patternCurrency}" type = "currency"  minFractionDigits="2"/>
	</display:column>
	
	<spring:message code="project.dueDate" var="dueDateHeader" />
	<spring:message code="project.creationMoment.pattern" var="datePattern"/>
	<display:column title="${dueDateHeader}" style="${style}">
		<fmt:formatDate value="${row.dueDate}" pattern="${datePattern}"/>
	</display:column>
	
</display:table><br>