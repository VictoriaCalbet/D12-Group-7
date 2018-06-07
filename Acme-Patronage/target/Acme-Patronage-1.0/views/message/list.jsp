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

<display:table name="messages" id="row" requestURI="${requestURI}" pagesize="5">

	<spring:message code="message.header" var="headerHeader" />
	<display:column property="header" title="${headerHeader}" sortable="true" />
	
	<spring:message code="message.body" var="bodyHeader" />
	<display:column property="body" title="${bodyHeader}" sortable="true" />
	
	<spring:message code="message.attachmentLink" var="attachmentLinkHeader" />
	<display:column property="attachmentLink" title="${attachmentLinkHeader}" sortable="true" />
	
	<jstl:choose>
		<jstl:when test="${requestURI == 'message/actor/outbox.do'}">
			<spring:message code="message.recipient" var="recipientHeader" />
			<display:column property="recipient.userAccount.username" title="${recipientHeader}" sortable="true" />
		</jstl:when>
		<jstl:when test="${requestURI == 'message/actor/inbox.do'}">
			<spring:message code="message.sender" var="senderHeader" />
			<display:column property="sender.userAccount.username" title="${senderHeader}" sortable="true" />
		</jstl:when>
	</jstl:choose>
	
</display:table>