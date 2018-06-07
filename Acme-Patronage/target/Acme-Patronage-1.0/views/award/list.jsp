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
<jsp:useBean id="now" class="java.util.Date"/>

<display:table name="awards" id="row" requestURI="${requestURI}" pagesize="5">

	<display:column>
		<spring:message code="award.display" var="awardDisplayLink"/>
		<a href="${displayURI}${row.id}"><jstl:out value="${awardDisplayLink}"/></a>
	</display:column>
	
	<security:authorize access="hasRole('USER')">
		<jstl:if test="${row.project.dueDate.time > now.time and row.project.creator.userAccount.username eq loggedactor.username and row.project.isDraft eq true and row.project.isCancelled eq false}">
			<display:column>
				<spring:message code="award.edit" var="awardEditLink"/>
				<a href="${editURI}${row.id}"><jstl:out value="${awardEditLink}"/></a>
			</display:column>
			<display:column>
				<spring:message code="award.delete" var="awardDeleteLink"/>
				<a href="${deleteURI}${row.id}"><jstl:out value="${awardDeleteLink}"/></a>
			</display:column>
		</jstl:if>
	</security:authorize>

	<spring:message code="award.name" var="awardNameHeader"/>
	<display:column property="name" title="${awardNameHeader}"/>
	
	<spring:message code="award.moneyGoal" var="awardMoneyGoalHeader" />
	<spring:message code="award.moneyGoal.setLocale" var="setLocale"/>
	<spring:message code="award.moneyGoal.patternCurrency" var="patternCurrency" />
	
	<display:column title="${awardMoneyGoalHeader}" style="${style}">
		<fmt:setLocale value="${setLocale}"/>
        <fmt:formatNumber value = "${row.moneyGoal}" currencySymbol="&euro;" pattern="${patternCurrency}" type="currency"  minFractionDigits="2"/>
	</display:column>
	
	<spring:message code="awardComment.list" var="listAwardComments"/>
	<display:column title="${listAwardComments}" style="${style}">
		<spring:message code="awardComment.listAwardComments" var="awardCommentList"/>
		<a href="awardComment/list.do?awardId=${row.id}"><jstl:out value="${awardCommentList}"/></a>
	</display:column>
	
	
	<security:authorize access="hasRole('USER')">
		<jstl:if test="${row.project.getIsCancelled() eq false and row.project.getIsDraft() eq false}">
			<display:column>
				<spring:message code="awardComment.create" var="awardComment"/>
				<a href="awardComment/user/create.do?awardId=${row.id}"><jstl:out value="${awardComment}"/></a>
			</display:column>
		</jstl:if>
	</security:authorize>
	
</display:table>

<security:authorize access="hasRole('USER')">
	<jstl:if test="${canCreate eq true}">
		<spring:message code="award.create" var="awardCreateLink"/>
		<a href="${createURI}"><jstl:out value="${awardCreateLink}"/></a>
		<br/>
		<br/>
	</jstl:if>
</security:authorize>