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

<!-- Form -->
<form:form action="${requestURI}" modelAttribute="messageForm">

	<!-- Hidden attributes -->
	<form:hidden path="id"/>
	<form:hidden path="senderId"/>
	
	<!-- Editable attributes -->
	<acme:textbox code="message.header" path="header"/>
	<acme:textarea code="message.body" path="body"/>
	<acme:textbox code="message.attachmentLink" path="attachmentLink"/>
	<acme:select items="${recipients}" itemLabel="userAccount.username" code="message.recipient" path="recipientId"/>
	
	<!-- Action buttons -->
	<acme:submit name="save" code="message.save" /> &nbsp;
	<acme:cancel url="/" code="message.cancel" /> <br/>	

</form:form>