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

<security:authorize access="hasRole('ADMIN')">

	<spring:message code="dashboard.avg" var="dashboardAvg"/>
	<spring:message code="dashboard.std" var="dashboardStd"/>
	<spring:message code="dashboard.min" var="dashboardMin"/>
	<spring:message code="dashboard.max" var="dashboardMax"/>
	<spring:message code="dashboard.ratio" var="dashboardRatio"/>
	
	<spring:message code="dashboard.project.title" var="dashboardProjectTitle"/>
	<spring:message code="dashboard.user.username" var="dashboardUserUsername"/>
	
	<!-- LEVEL C -->
	
	<table border="1">
		<tr>
			<td colspan="2"> <b> <spring:message code="dashboard.avgProjectsPerUser"/>:&nbsp; </b> </td>
		</tr>
		<tr>
			<td> <b> <jstl:out value="${dashboardAvg}"/>:&nbsp; </b> <jstl:out value="${avgProjectsPerUser}"/></td>
			<td> <b> <jstl:out value="${dashboardStd}"/>:&nbsp; </b> <jstl:out value="${stdProjectsPerUser}"/></td>
		</tr>
	</table>
	
	<table border="1">
		<tr>
			<td colspan="2"> <b> <spring:message code="dashboard.avgAwardsPerProject"/>:&nbsp; </b> </td>
		</tr>
		<tr>
			<td> <b> <jstl:out value="${dashboardAvg}"/>:&nbsp; </b> <jstl:out value="${avgAwardsPerProject}"/></td>
			<td> <b> <jstl:out value="${dashboardStd}"/>:&nbsp; </b> <jstl:out value="${stdAwardsPerProject}"/></td>
		</tr>
	</table>
	
	<table border="1">
		<tr>
			<td colspan="2"> <b> <spring:message code="dashboard.avgAwardsPerUser"/>:&nbsp; </b> </td>
		</tr>
		<tr>
			<td> <b> <jstl:out value="${dashboardAvg}"/>:&nbsp; </b> <jstl:out value="${avgAwardsPerUser}"/></td>
			<td> <b> <jstl:out value="${dashboardStd}"/>:&nbsp; </b> <jstl:out value="${stdAwardsPerUser}"/></td>
		</tr>
	</table>
	
	<table border="1">
		<tr>
			<td colspan="2"> <b> <spring:message code="dashboard.ratioFundedProjects"/>:&nbsp; </b> </td>
		</tr>
		<tr>
			<td> <b> <jstl:out value="${dashboardRatio}"/>:&nbsp; </b> <jstl:out value="${ratioFundedProjects}"/></td>
		</tr>
	</table>
	
	<table border="1">
		<tr>
			<td colspan="2"> <b> <spring:message code="dashboard.findAllWith10PercentMoreProjectsThanAvg"/>:&nbsp; </b> </td>
		</tr>
		<tr>
			<td> <b> <jstl:out value="${dashboardUserUsername}"/>:&nbsp; </b> </td>
		</tr>
		<jstl:forEach items="${findAllWith10PercentMoreProjectsThanAvg}" var="user">
			<tr>
				<td> <jstl:out value="${user.userAccount.username}"/></td>
			</tr>
		</jstl:forEach>
	</table>
	
	<table border="1">
		<tr>
			<td colspan="2"> <b> <spring:message code="dashboard.findAllWith10PercentMorePatronagesThanAvg"/>:&nbsp; </b> </td>
		</tr>
		<tr>
			<td> <b> <jstl:out value="${dashboardUserUsername}"/>:&nbsp; </b> </td>
		</tr>
		<jstl:forEach items="${findAllWith10PercentMorePatronagesThanAvg}" var="user">
			<tr>
				<td> <jstl:out value="${user.userAccount.username}"/></td>
			</tr>
		</jstl:forEach>
	</table>
	
	<table border="1">
		<tr>
			<td colspan="2"> <b> <spring:message code="dashboard.top5ActiveProjectsWithMoreRaisedMoney"/>:&nbsp; </b> </td>
		</tr>
		<tr>
			<td> <b> <jstl:out value="${dashboardProjectTitle}"/>:&nbsp; </b> </td>
		</tr>
		<jstl:forEach items="${top5ActiveProjectsWithMoreRaisedMoney}" var="project">
			<tr>
				<td> <jstl:out value="${project.title}"/></td>
			</tr>
		</jstl:forEach>
	</table>
	
	<table border="1">
		<tr>
			<td colspan="2"> <b> <spring:message code="dashboard.top5DueProjectsWithMoreRaisedMoney"/>:&nbsp; </b> </td>
		</tr>
		<tr>
			<td> <b> <jstl:out value="${dashboardProjectTitle}"/>:&nbsp; </b> </td>
		</tr>
		<jstl:forEach items="${top5DueProjectsWithMoreRaisedMoney}" var="project">
			<tr>
				<td> <jstl:out value="${project.title}"/></td>
			</tr>
		</jstl:forEach>
	</table>
	
	<!-- LEVEL B -->
	
	<table border="1">
		<tr>
			<td colspan="2"> <b> <spring:message code="dashboard.avgSponsorshipPerCorporation"/>:&nbsp; </b> </td>
		</tr>
		<tr>
			<td> <b> <jstl:out value="${dashboardAvg}"/>:&nbsp; </b> <jstl:out value="${avgSponsorshipPerCorporation}"/></td>
			<td> <b> <jstl:out value="${dashboardStd}"/>:&nbsp; </b> <jstl:out value="${stdSponsorshipPerCorporation}"/></td>
		</tr>
	</table>
	
	<table border="1">
		<tr>
			<td colspan="2"> <b> <spring:message code="dashboard.avgSponsorshipPerProject"/>:&nbsp; </b> </td>
		</tr>
		<tr>
			<td> <b> <jstl:out value="${dashboardAvg}"/>:&nbsp; </b> <jstl:out value="${avgSponsorshipPerProject}"/></td>
			<td> <b> <jstl:out value="${dashboardStd}"/>:&nbsp; </b> <jstl:out value="${stdSponsorshipPerProject}"/></td>
		</tr>
	</table>
	
	<table border="1">
		<tr>
			<td colspan="2"> <b> <spring:message code="dashboard.avgCommentsPerProject"/>:&nbsp; </b> </td>
		</tr>
		<tr>
			<td> <b> <jstl:out value="${dashboardAvg}"/>:&nbsp; </b> <jstl:out value="${avgCommentsPerProject}"/></td>
			<td> <b> <jstl:out value="${dashboardStd}"/>:&nbsp; </b> <jstl:out value="${stdCommentsPerProject}"/></td>
		</tr>
	</table>
	
	<table border="1">
		<tr>
			<td colspan="2"> <b> <spring:message code="dashboard.findAllProjectWith10PercentMoreSponsorshipsThanAvg"/>:&nbsp; </b> </td>
		</tr>
		<tr>
			<td> <b> <jstl:out value="${dashboardProjectTitle}"/>:&nbsp; </b> </td>
		</tr>
		<jstl:forEach items="${findAllProjectWith10PercentMoreSponsorshipsThanAvg}" var="project">
			<tr>
				<td> <jstl:out value="${project.title}"/></td>
			</tr>
		</jstl:forEach>
	</table>
	
	<table border="1">
		<tr>
			<td colspan="2"> <b> <spring:message code="dashboard.findAllCorporationWith10PercentMoreSponsorshipsThanAvg"/>:&nbsp; </b> </td>
		</tr>
		<tr>
			<td> <b> <jstl:out value="${dashboardUserUsername}"/>:&nbsp; </b> </td>
		</tr>
		<jstl:forEach items="${findAllCorporationWith10PercentMoreSponsorshipsThanAvg}" var="corporation">
			<tr>
				<td> <jstl:out value="${corporation.userAccount.username}"/></td>
			</tr>
		</jstl:forEach>
	</table>
	
	<!-- LEVEL A -->
	
	<table border="1">
		<tr>
			<td colspan="2"> <b> <spring:message code="dashboard.avgReportsPerProject"/>:&nbsp; </b> </td>
		</tr>
		<tr>
			<td> <b> <jstl:out value="${dashboardAvg}"/>:&nbsp; </b> <jstl:out value="${avgReportsPerProject}"/></td>
			<td> <b> <jstl:out value="${dashboardStd}"/>:&nbsp; </b> <jstl:out value="${stdReportsPerProject}"/></td>
		</tr>
	</table>
	
	<table border="1">
		<tr>
			<td colspan="2"> <b> <spring:message code="dashboard.ratioReportsPerProject"/>:&nbsp; </b> </td>
		</tr>
		<tr>
			<td> <b> <jstl:out value="${dashboardRatio}"/>:&nbsp; </b> <jstl:out value="${ratioReportsPerProject}"/></td>
		</tr>
	</table>
	
	<table border="1">
		<tr>
			<td colspan="2"> <b> <spring:message code="dashboard.ratioReportsPerUser"/>:&nbsp; </b> </td>
		</tr>
		<tr>
			<td> <b> <jstl:out value="${dashboardRatio}"/>:&nbsp; </b> <jstl:out value="${ratioReportsPerUser}"/></td>
		</tr>
	</table>
	
	<table border="1">
		<tr>
			<td colspan="2"> <b> <spring:message code="dashboard.ratioLegitReports"/>:&nbsp; </b> </td>
		</tr>
		<tr>
			<td> <b> <jstl:out value="${dashboardRatio}"/>:&nbsp; </b> <jstl:out value="${ratioLegitReports}"/></td>
		</tr>
	</table>
	
	<table border="1">
		<tr>
			<td colspan="2"> <b> <spring:message code="dashboard.ratioBannedUsers"/>:&nbsp; </b> </td>
		</tr>
		<tr>
			<td> <b> <jstl:out value="${dashboardRatio}"/>:&nbsp; </b> <jstl:out value="${ratioBannedUsers}"/></td>
		</tr>
	</table>

</security:authorize>