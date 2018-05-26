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

<display:table name="sponsorships" id="row" requestURI="${requestURI}" pagesize="5">

	
	<spring:message code="sponsorship.banner" var="banner" />
	<display:column title="${banner}">
		<a href="<jstl:url value="${row.bannerURL}"/>">
			<jstl:url value="${row.bannerURL}"/>
		</a>
	</display:column>
	
	<spring:message code="sponsorship.page" var="page" />
	<display:column title="${page}">
		<a href="<jstl:url value="${row.pageURL}"/>">
			<jstl:url value="${row.pageURL}"/>
		</a>
	</display:column>
	
	<spring:message code="sponsorship.project" var="project" />
	<display:column title="${project}">
		<jstl:out value="${row.project.title}"></jstl:out>
	</display:column>
	
	<jstl:if test="${editable eq true}">
	<spring:message code="sponsorship.edit" var="edit" />
	<display:column sortable="false" title="${edit}">
		<a href="sponsorship/corporation/edit.do?sponsorshipId=${row.id}">
			${edit}
		</a>
	</display:column>
	<spring:message code="sponsorship.delete" var="delete" />
	<display:column sortable="false" title="${delete}">
		<a href="${deleteURI}${row.id}">
			${delete}
		</a>
	</display:column>
	</jstl:if>
</display:table>
<jstl:if test="${editable eq true}">
<spring:message code="sponsorship.create" var="create" />
<a href="sponsorship/corporation/create.do">${create}</a>
</jstl:if>