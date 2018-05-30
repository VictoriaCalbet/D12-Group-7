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

<display:table name="announcementComments" id="row" requestURI="${requestURI}" pagesize="5">

	<spring:message code="announcementComment.user" var="aCUser" />	
	<display:column property="user.fullName" title="${aCUser}"/>

	<spring:message code="announcementComment.text" var="aCText" />	
	<display:column property="text" title="${aCText}"/>
	
	<spring:message code="announcementComment.announcement" var="aCPro" />	
	<display:column property="announcement.title" title="${aCPro}"/>
	
	<spring:message code="announcementComment.rating" var="aCRating" />
	<display:column property="rating" title="${aCRating}"/>
	
	<spring:message code="announcementComment.creationMoment" var="aCCreationMoment" />
	<spring:message code="announcementComment.creationMoment.pattern" var="datePattern"/>
	<display:column title="${aCCreationMoment}">
		<fmt:formatDate value="${row.creationMoment}" pattern="${datePattern}"/>
	</display:column>

</display:table>
