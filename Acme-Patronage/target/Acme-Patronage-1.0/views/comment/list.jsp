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

<spring:message code="comment.announcementC" var="titleAn" />	
<spring:message code="comment.awardC" var="titleAw" />	
<spring:message code="comment.projectC" var="titlePr" />	
<b><jstl:out value="${titleAn}"/></b>

<display:table name="announcementComments" id="row" requestURI="${requestURI}" pagesize="5">

	<spring:message code="announcementComment.user" var="aCUser" />	
	<display:column property="user.fullName" title="${aCUser}"/>

	<spring:message code="announcementComment.announcement" var="aN" />	
	<display:column property="announcement.title" title="${aN}"/>

	<spring:message code="announcementComment.text" var="aCText" />	
	<display:column property="text" title="${aCText}"/>
	
	<spring:message code="announcementComment.rating" var="aCRating" />
	<display:column property="rating" title="${aCRating}"/>
	
	<spring:message code="announcementComment.creationMoment" var="aCCreationMoment" />
	<spring:message code="announcementComment.creationMoment.pattern" var="datePattern"/>
	<display:column title="${aCCreationMoment}">
		<fmt:formatDate value="${row.creationMoment}" pattern="${datePattern}"/>
	</display:column>
	
	<display:column>
		<a href="comment/administrator/deleteAnnouncementComment.do?announcementCommentId=${row.id}"><spring:message code="awardComment.delete"/></a>
	</display:column>

</display:table>

<b><jstl:out value="${titleAw}"/></b>

<display:table name="awardComments" id="row" requestURI="${requestURI}" pagesize="5">

	<spring:message code="awardComment.user" var="aCUser" />	
	<display:column property="user.fullName" title="${aCUser}"/>
	
	<spring:message code="awardComment.award" var="aW" />	
	<display:column property="award.name" title="${aW}"/>

	<spring:message code="awardComment.text" var="aCText" />	
	<display:column property="text" title="${aCText}"/>
	
	<spring:message code="awardComment.rating" var="aCRating" />
	<display:column property="rating" title="${aCRating}"/>
	
	<spring:message code="awardComment.creationMoment" var="aCCreationMoment" />
	<spring:message code="awardComment.creationMoment.pattern" var="datePattern"/>
	<display:column title="${aCCreationMoment}">
		<fmt:formatDate value="${row.creationMoment}" pattern="${datePattern}"/>
	</display:column>
	
	<display:column>
		<a href="comment/administrator/deleteAwardComment.do?awardCommentId=${row.id}"><spring:message code="awardComment.delete"/></a>
	</display:column>

</display:table>

<b><jstl:out value="${titlePr}"/></b>

<display:table name="projectComments" id="row" requestURI="${requestURI}" pagesize="5">

	<spring:message code="projectComment.user" var="aCUser" />	
	<display:column property="user.fullName" title="${aCUser}"/>
	
	<spring:message code="projectComment.project" var="aPr" />	
	<display:column property="project.title" title="${aPr}"/>

	<spring:message code="projectComment.text" var="aCText" />	
	<display:column property="text" title="${aCText}"/>
	
	<spring:message code="projectComment.rating" var="aCRating" />
	<display:column property="rating" title="${aCRating}"/>
	
	<spring:message code="projectComment.creationMoment" var="aCCreationMoment" />
	<spring:message code="projectComment.creationMoment.pattern" var="datePattern"/>
	<display:column title="${aCCreationMoment}">
		<fmt:formatDate value="${row.creationMoment}" pattern="${datePattern}"/>
	</display:column>
	
	<display:column>
		<a href="comment/administrator/deleteProjectComment.do?projectCommentId=${row.id}"><spring:message code="projectComment.delete"/></a>
	</display:column>

</display:table>

