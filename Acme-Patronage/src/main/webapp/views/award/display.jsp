<%--
 * display.jsp
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

<fieldset>
	<legend>
		<spring:message code="award.project.title" var="awardProjectTitleLegend"/>
		<h2><b><jstl:out value="${awardProjectTitleLegend}"/>:&nbsp;</b> <jstl:out value="${award.project.title}"/></h2>
	</legend>
	
	<table>
		<tr>
			<td>
				<spring:message code="award.name" var="awardNameLabel"/>
				<b><jstl:out value="${awardNameLabel}"/>:&nbsp;</b> <jstl:out value="${award.name}"/> 				
			</td>
		</tr>
		<tr>
			<td>
				<spring:message code="award.description" var="awardDescriptionLabel"/>
				<b><jstl:out value="${awardDescriptionLabel}"/>:&nbsp;</b> <jstl:out value="${award.description}"/> 	
			</td>
		</tr>
		<tr>
			<td>
				<spring:message code="award.moneyGoal" var="awardMoneyGoalLabel" />
				<spring:message code="award.moneyGoal.setLocale" var="setLocale"/>
				<spring:message code="award.moneyGoal.patternCurrency" var="patternCurrency" />
				<b><jstl:out value="${awardMoneyGoalLabel}"/>:&nbsp;</b> <fmt:formatNumber value = "${award.moneyGoal}" currencySymbol="&euro;" pattern="${patternCurrency}" type="currency"  minFractionDigits="2"/>				
			</td>
		</tr>
	</table>
	
	
	<security:authorize access="hasRole('USER')">
		<jstl:if test="${canEdit eq true}">
			<spring:message code="award.create" var="awardCreateLink"/>
			<acme:cancel url="${editURI}" code="award.edit"/>
		</jstl:if>
	</security:authorize>
	
	<jstl:out value="${editURL}"/>
	
	<acme:cancel url="${cancelURI}" code="award.cancel"/>
</fieldset>
