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

<form:form action="${actionURI}" modelAttribute="award">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="project"/>
	<form:hidden path="awardComments"/>
	
	<fieldset>
		<legend>
			<spring:message code="award.project.title" var="awardProjectTitleLegend"/>
			<h2><b><jstl:out value="${awardProjectTitleLegend}"/>:&nbsp;</b> <jstl:out value="${award.project.title}"/></h2>
		</legend>
		
		<acme:textbox code="award.name" path="name"/>
		<acme:textbox code="award.description" path="description"/>
		<acme:textbox code="award.moneyGoal" path="moneyGoal"/>
	</fieldset>
	
	<br/>
	<acme:submit name="save" code="award.save"/> &nbsp;
	<acme:cancel url="${cancelURI}" code="award.cancel"/>
	<br/>
	</form:form>