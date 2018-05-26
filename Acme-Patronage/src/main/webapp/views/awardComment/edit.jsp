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
	
	<acme:textbox code="awardComment.text" path="text"/>
	
	<form:label path="rating">
	<spring:message code="awardComment.rating"/>
	</form:label>
	<form:errors cssClass="error" path="rating"/>
	<form:select code="awardComment.rating" path="rating">
  		<form:options items="${numbers}"/>
	</form:select>
	<br/>
	<br/>
	<acme:submit name="save" code="awardComment.save" />
	<acme:cancel url="/" code="awardComment.cancel" /> 	
	
</form:form>	