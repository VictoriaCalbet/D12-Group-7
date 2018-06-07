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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<display:table name="categories" id="row" requestURI="${requestURI}" pagesize="5">


	<spring:message code="category.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}"
 		sortable="false" style="${style}" />

	<spring:message code="category.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" 
		sortable="false" style="${style}" />

	<spring:message code="project.project" var="projectHeader" />	
		<display:column title="${projectHeader}">	
			<a href="project/listByCategory.do?categoryId=${row.id}">
			 	<spring:message code="project.showProject" />
			</a>
		</display:column>
	
	
	<security:authorize access="hasRole('ADMIN')">
	<spring:message code="category.edit" var="editHeader" />
		<display:column title="${editHeader}" style="${style}">
			<jstl:choose>
				<jstl:when test="${(fn:length(row.projects) == 0)}">
					<spring:message var="categoryEditLink" code="category.edit"/>
					<a href="category/administrator/edit.do?categoryId=${row.id}"><jstl:out value="${categoryEditLink}"/></a>
				</jstl:when>
				<jstl:otherwise>
					<spring:message code="category.notEditable" var="categoryNotEditableMessage" />
					<jstl:out value="${categoryNotEditableMessage}"/>
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
	</security:authorize>
	
	
	<security:authorize access="hasRole('ADMIN')">
	<spring:message code="category.delete" var="deleteCategoryHeader" />
	<display:column title="${deleteCategoryHeader}" style="${style}">
		<jstl:choose>
			<jstl:when test="${(fn:length(row.projects) == 0)}">
				<spring:message var="categoryDeleteLink" code="category.delete"/>
 				<a href="category/administrator/delete.do?categoryId=${row.id}"><jstl:out value="${categoryDeleteLink}"/></a>
			</jstl:when>
			<jstl:otherwise>
					<spring:message code="category.notDeletable" var="categoryNotDeletableMessage" />
					<jstl:out value="${categoryNotDeletableMessage}"/>
				</jstl:otherwise>
			</jstl:choose>
	</display:column>
	</security:authorize>
	

	</display:table>
		<security:authorize access="hasRole('ADMIN')">
		
		<a href="category/administrator/create.do">
		<spring:message code="category.create" /></a>
		
</security:authorize>
	

