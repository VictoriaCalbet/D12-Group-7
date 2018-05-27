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


<jsp:useBean id="now" class="java.util.Date"/>

<jstl:choose>
 <jstl:when test="${pageContext.response.locale.language=='en'}">
      <fmt:setLocale value = "en_US"/>
      <jstl:set var="patternCurrency" value="¤ #,##0.00"></jstl:set>
 </jstl:when>
 <jstl:otherwise>
      <fmt:setLocale value = "es_ES"/>
      <jstl:set var="patternCurrency" value="#,##0.00 ¤"></jstl:set>
 </jstl:otherwise> 
</jstl:choose>




<div>
	<b><spring:message code="project.title" />: </b>
	<jstl:out value="${project.title}" /><br>
	
	<b><spring:message code="project.description" />: </b>
	<jstl:out value="${project.description}" /><br>	
	
	<b><spring:message code="project.economicGoal" />: </b>
	<fmt:formatNumber value = "${project.economicGoal}" currencySymbol="&euro;" pattern="${patternCurrency}" type = "currency" minFractionDigits="2"/><br>
	
	
	<b><spring:message code="project.minimumPatronageAmount" />: </b>
	<fmt:formatNumber value = "${project.minimumPatronageAmount}" currencySymbol="&euro;" pattern="${patternCurrency}" type = "currency" minFractionDigits="2"/><br>
	 <br/>
	
	<div>
	<b><spring:message code="project.sponsorship" />: </b>
	</div>
 	<br/>
	<jstl:if test="${sponsorshipBanner!=null}">
		<spring:url var="sponsorshipBannerURL"
			value="${sponsorshipBanner.bannerURL}" />
		<spring:url var="sponsorshipPageURL"
			value="${sponsorshipBanner.pageURL}" />
		<spring:message code="project.sponsorship" var="sponsorship" />
		<a href="${sponsorshipPageURL}"> <img
			src="${sponsorshipBannerURL}" alt="${sponsorship}" />
		</a>
	</jstl:if>
	<jstl:if test="${sponsorshipBanner==null}">
		<spring:message code="project.sponsorship.noBannerUrl" />
</jstl:if>
</div>
