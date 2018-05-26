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

<security:authentication property="principal" var="loggedactor"/>

<display:table name="announcements" id="row" requestURI="${requestURI}" pagesize="5">

	<spring:message code="announcement.title" var="announcementTitleHeader"/>
	<display:column property="title" title="${announcementTitleHeader}"/>
	
	<spring:message code="announcement.description" var="announcementDescriptionHeader"/>
	<display:column property="description" title="${announcementDescriptionHeader}"/>
	
	<spring:message code="announcement.creationMoment" var="announcementCreationMomentHeader" />
	<spring:message code="announcement.creationMoment.pattern" var="datePattern"/>
	<display:column title="${announcementCreationMomentHeader}" style="${style}">
		<fmt:formatDate value="${row.creationMoment}" pattern="${datePattern}"/>
	</display:column>
	
	<spring:message code="announcementComment.listGeneral" var="aCom"/>
	<display:column title="${aCom}">
	<a href="announcementComment/list.do?announcementId=${row.id}"><spring:message code="announcementComment.announcement.list"></spring:message></a>
	</display:column>
	
	<security:authorize access="hasRole('USER')">
		<display:column>
		<jstl:if test="${row.project.getIsCancelled()==false and row.project.getIsDraft()==false}">
		<a href="announcementComment/user/create.do?announcementId=${row.id}"><spring:message code="announcementComment.create"></spring:message></a>
		</jstl:if>
		</display:column>
		
	</security:authorize>

</display:table>

<security:authorize access="hasRole('USER')">
	<jstl:if test="${canCreate eq true}">
		<spring:message code="announcement.create" var="announcementCreateLink"/>
		<a href="${createURI}"><jstl:out value="${announcementCreateLink}"/></a>
		<br/>
		<br/>
	</jstl:if>
</security:authorize>