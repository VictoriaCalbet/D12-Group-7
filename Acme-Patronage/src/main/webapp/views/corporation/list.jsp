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

<display:table name="corporations" id="row" requestURI="${requestURI}" pagesize="5">

	<spring:message code="corporation.fullName" var="fullNameHeader" />
	<display:column property="fullName" title="${fullNameHeader}" sortable="true" />
	
	<spring:message code="corporation.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />
	
	<spring:message code="corporation.bio" var="bioHeader" />
	<display:column property="bio" title="${bioHeader}" sortable="true" />
	
	<spring:message code="corporation.telephone" var="telephoneHeader" />
	<display:column property="telephone" title="${telephoneHeader}" sortable="true" />
	
	<spring:message code="corporation.username" var="usernameHeader" />
	<display:column property="userAccount.username" title="${usernameHeader}" sortable="true" />

</display:table>