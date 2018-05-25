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
<display:table name="reports" id="row" requestURI="${requestURI}" pagesize="5">

	<spring:message code="report.complaint" var="complaintHeader" />
	<display:column property="complaint" title="${complaintHeader}" />
	<spring:message code="report.project" var="projectHeader" />
	<display:column title="${projectHeader}" ><jstl:out value="${row.project.title}"/></display:column>
	<spring:message code="report.user" var="userHeader" />
	<display:column title="${userHeader}" ><a href="user/display.do?userId=${row.user.id}">${row.user.fullName}</a></display:column>
	
	<security:authorize access="hasRole('MODERATOR')">
	
	<spring:message code="report.reject" var="rejectHeader" />
	<display:column title="${rejectHeader}" ><a href="report/moderator/reject.do?reportId=${row.id}">${rejectHeader}</a></display:column>
	
	<spring:message code="report.accept" var="acceptHeader" />
	<display:column title="${acceptHeader}" ><a href="report/moderator/accept.do?reportId=${row.id}">${acceptHeader}</a></display:column>
	
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
	
	<spring:message code="report.reason" var="reasonHeader" />
	<display:column property="reason" title="${reasonHeader}" />
	
	</security:authorize>
	


</display:table>