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

<display:table name="awards" id="row" requestURI="${requestURI}" pagesize="5">

	<display:column>
		<spring:message code="award.display" var="awardDisplayLink"/>
		<a href="${displayURI}${row.id}"><jstl:out value="${awardDisplayLink}"/></a>
	</display:column>

	<spring:message code="award.name" var="awardNameHeader"/>
	<display:column property="name" title="${awardNameHeader}"/>
	
	<spring:message code="award.moneyGoal" var="awardMoneyGoalHeader" />
	<spring:message code="award.moneyGoal.setLocale" var="setLocale"/>
	<spring:message code="award.moneyGoal.patternCurrency" var="patternCurrency" />
	<display:column title="${awardMoneyGoalHeader}" style="${style}">
		<fmt:setLocale value="${setLocale}"/>
        <fmt:formatNumber value = "${row.moneyGoal}" currencySymbol="&euro;" pattern="${patternCurrency}" type="currency"  minFractionDigits="2"/>
	</display:column>
	
</display:table>

<security:authorize access="hasRole('USER')">
	<jstl:if test="${canCreate eq true}">
		<spring:message code="award.create" var="awardCreateLink"/>
		<a href="${createURI}"><jstl:out value="${awardCreateLink}"/></a>
		<br/>
		<br/>
	</jstl:if>
</security:authorize>