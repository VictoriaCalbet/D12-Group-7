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

<display:table name="users" id="row" requestURI="${requestURI}" pagesize="5">
	
	<spring:message code="user.profile" var="profileHeader" />	
	<display:column title="${profileHeader}">	
		<a href="user/display.do?userId=${row.id}">
		 	<spring:message code="user.profile" />
		</a>
	</display:column>

	<spring:message code="user.fullName" var="fullNameHeader" />
	<display:column property="fullName" title="${fullNameHeader}" sortable="true" />
	
	<spring:message code="user.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />
	
	<spring:message code="user.bio" var="bioHeader" />
	<display:column property="bio" title="${bioHeader}" sortable="true" />
	
	<spring:message code="user.telephone" var="telephoneHeader" />
	<display:column property="telephone" title="${telephoneHeader}" sortable="true" />
	
	<spring:message code="user.username" var="usernameHeader" />
	<display:column property="userAccount.username" title="${usernameHeader}" sortable="true" />
	
	<security:authorize access="hasRole('ADMIN')">
		<spring:message code="user.ban.unban" var="banUnbanHeader" />
		<display:column title="${banUnbanHeader}">
			<jstl:choose>
				<jstl:when test="${!row.userAccount.isEnabled}">
					<a href="user/administrator/unban.do?userId=${row.id}">
		 				<spring:message code="user.unban" />
					</a>
				</jstl:when>
				<jstl:when test="${hasLegitReports[row.id]}">
					<a href="user/administrator/ban.do?userId=${row.id}">
		 				<spring:message code="user.ban" />
					</a>
				</jstl:when>
			</jstl:choose>
		</display:column>
	</security:authorize>

</display:table>