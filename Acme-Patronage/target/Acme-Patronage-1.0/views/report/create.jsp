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

<form:form action="${requestURI}" modelAttribute="report">

	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="user"/>
	<form:hidden path="reason"/>
	<form:hidden path="isLegit"/>
	
	<acme:select code="report.project" path="project" items="${projects}" itemLabel="title"/><br/>
	<acme:textarea code="report.complaint" path="complaint"/>	
	
	<br/>
	<input type="submit" name="save" value="<spring:message code="report.save"/>"/>
	<acme:cancel url="/" code="report.cancel" /> <br/>
</form:form>