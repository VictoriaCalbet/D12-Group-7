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

<form:form action="${requestURI}" modelAttribute="projectForm">

	<!-- Hidden attributes -->
	<form:hidden path="id"/>
		
	<!-- Editable attributes -->
	
	<acme:textbox code="project.title" path="title"/>
	<acme:textarea code="project.description" path="description"/>
	<acme:textbox code="project.economicGoal" path="economicGoal"/>
	<acme:textbox code="project.minimumPatronageAmount" path="minimumPatronageAmount"/>
	<acme:date code="project.dueDate" path="dueDate"/>
	<acme:checkbox code="project.isDraft" path="isDraft" />
	<acme:select items="${categories}" itemLabel="name" code="project.category" path="category"/>
	
		<!-- Action buttons -->
	<acme:submit name="save" code="project.save" /> &nbsp;
	<acme:cancel url="/" code="project.cancel" /> <br/>

</form:form>
