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

<form:form action="${requestURI}" modelAttribute="commentForm">

	<!-- Hidden attributes -->
	<form:hidden path="id"/>
	<form:hidden path="creationMoment"/>
	
	<acme:textbox code="projectComment.text" path="text"/>
	<br/>
	<form:label path="rating">
	<spring:message code="projectComment.rating"/>
	</form:label>
	<form:errors cssClass="error" path="rating"/>
	<form:select code="projectComment.rating" path="rating">
  		<form:options items="${numbers}"/>
	</form:select>
	<br/>
	
<br><br>		
	<acme:submit name="save" code="projectComment.save" />
	<acme:cancel url="/" code="projectComment.cancel" /> 	
	
</form:form>	