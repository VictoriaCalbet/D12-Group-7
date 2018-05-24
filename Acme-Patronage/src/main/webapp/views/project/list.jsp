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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<form action="${requestURI}" method="get">
	<label><b><spring:message code="project.keyWord"/>:&nbsp;</b></label>
	<input type="text" name="word" placeholder="<spring:message code="project.KeyWord.filter"/>"/> 
	<input type="submit" value="<spring:message code="project.KeyWord.filter"/>"/>
</form>
<br/>

<jsp:useBean id="now" class="java.util.Date"/>
<security:authentication property="principal" var="loggedactor"/>

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

<display:table name="projects" id="row" requestURI="${requestURI}" pagesize="5">
	
	<jstl:set var="isDraft" value="${row.isDraft}" />
	<jstl:set var="isCancelled" value="${row.isCancelled}"/>
	
	<jstl:if test="${isDraft eq true}">
		<jstl:set var="style" value="background-color:Khaki;" />
	</jstl:if>
	
	<jstl:if test="${isCancelled eq true}">
		<jstl:set var="style" value="background-color:Silver;" />
	</jstl:if>
	
	<jstl:if test="${isCancelled eq false and isDraft eq false}">
		<jstl:set var="style" value="background-color:transparent;" />
	</jstl:if>


	<spring:message code="project.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" style="${style}" />
	
	<spring:message code="project.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" style="${style}" />
	
	<spring:message code="project.creationMoment" var="creationMomentHeader" />
	<spring:message code="project.creationMoment.pattern" var="datePattern"/>
	<display:column title="${creationMomentHeader}" style="${style}">
		<fmt:formatDate value="${row.creationMoment}" pattern="${datePattern}"/>
	</display:column>
	
	<spring:message code="project.awards" var="awardsHeader" />	
	<spring:message code="project.showAwards" var="showAwardsLink" />
	<display:column title="${awardsHeader}" style="${style}">
	
		<security:authorize access="hasRole('USER')">
			<a href="award/user/list.do?projectId=${row.id}"><jstl:out value="${showAwardsLink}" /></a>
		</security:authorize>
		<security:authorize access="hasRole('ADMIN')">
			<a href="award/administrator/list.do?projectId=${row.id}"><jstl:out value="${showAwardsLink}" /></a>
		</security:authorize>
		<security:authorize access="isAnonymous()">
			<a href="award/list.do?projectId=${row.id}"><jstl:out value="${showAwardsLink}" /></a>
		</security:authorize>

	</display:column>
	
	<spring:message code="project.economicGoal" var="economicGoalHeader" />
	<display:column title="${economicGoalHeader}" style="${style}">
        <fmt:formatNumber value = "${row.economicGoal}" currencySymbol="&euro;" pattern="${patternCurrency}" type = "currency"  minFractionDigits="2"/>
	</display:column>
	
	<spring:message code="project.minimumPatronageAmount" var="minimumPatronageAmountHeader" />
	<display:column title="${minimumPatronageAmountHeader}" style="${style}">
        <fmt:formatNumber value = "${row.minimumPatronageAmount}" currencySymbol="&euro;" pattern="${patternCurrency}" type = "currency"  minFractionDigits="2"/>
	</display:column>
	
	<spring:message code="project.dueDate" var="dueDateHeader" />
	<spring:message code="project.creationMoment.pattern" var="datePattern"/>
	<display:column title="${dueDateHeader}" style="${style}">
		<fmt:formatDate value="${row.dueDate}" pattern="${datePattern}"/>
	</display:column>
	
	<spring:message code="project.category" var="categoryHeader" />	
	<display:column title="${categoryHeader}" style="${style}">
		<jstl:out value="${row.category.name}" />
	</display:column>
	
	
	
	<security:authorize access="hasRole('USER')">
	
	<spring:message code="project.delete" var="deleteHeader" />	
		<display:column title="${deleteHeader}">
		<jstl:choose>
			<jstl:when test="${(row.isCancelled eq false) && row.dueDate.time > now.time && row.creator.userAccount.username==loggedactor.username }">
				<jstl:choose>
						<jstl:when test="${(fn:length(row.patronages) ==0)}">	
							<a href="project/user/delete.do?projectId=${row.id}">
							 	<spring:message code="project.deleteButton" />
							</a>
						</jstl:when>
						
						<jstl:when test="${(row.isDraft eq false) && (fn:length(row.patronages) !=0)}">	
							<a href="project/user/cancel.do?projectId=${row.id}">
							 	<spring:message code="project.cancel" />
							</a>
						</jstl:when>
				</jstl:choose>	
			</jstl:when>
			<jstl:otherwise>
				<spring:message code= "project.notCancelDelete" var="projectNotCancelDelete"/>
					<jstl:out value="${projectNotCancelDelete}"/> 
			</jstl:otherwise>
			
		</jstl:choose>
	
		</display:column>
		
		<spring:message code="project.edit" var="editHeader" />	
		<display:column title="${editHeader}">
			<jstl:choose>
			
				<jstl:when test="${(row.isCancelled eq false) && (row.isDraft eq true) && row.dueDate.time > now.time && row.creator.userAccount.username==loggedactor.username}">	
					<a href="project/user/edit.do?projectId=${row.id}">
					 	<spring:message code="project.editButton" />
					</a>
				</jstl:when>
				<jstl:otherwise>
					<spring:message code= "project.notEdit" var="projectNotEditable"/>
						<jstl:out value="${projectNotEditable}"/> 
				</jstl:otherwise>
			</jstl:choose>	
						
		</display:column>
	
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
	
	<spring:message code="project.delete" var="deleteHeader" />	
		<display:column title="${deleteHeader}">	
			<a href="project/administrator/delete.do?projectId=${row.id}">
			 	<spring:message code="project.deleteButton" />
			</a>
		</display:column>
	
	</security:authorize>
	
	<spring:message code="patronage.totalAmount" var="totalAmountsHeader" />
	<display:column title="${totalAmountsHeader}" >
	
	<jstl:forEach var="amount" items="${totalAmounts}" >

   	<jstl:out value="${amount}">
   		
 	 </jstl:out>

	</jstl:forEach>
    
		
		
		
	
	</display:column>
	
	
	<security:authorize access="hasRole('USER')">
	
	<spring:message code="project.patronage" var="patronageHeader" />	
	<display:column title="${patronageHeader}">	
		<jstl:choose>
		<jstl:when test="${row.creator != principal}">
			<a href="patronage/user/edit.do?projectId=${row.id}">
			 	<spring:message code="project.patronageButton" />
			</a>
		</jstl:when>
		</jstl:choose>
	</display:column>
	
	</security:authorize>
</display:table>

<security:authorize access="hasRole('USER')">
	
	<div>
		<b><a href="project/user/create.do"> 
			<spring:message code="project.create"/>
		</a></b>
	</div>
	<br/>
</security:authorize>


<security:authorize access="isAuthenticated()">
	<jstl:if test="${not empty projects}">
		<span style="background-color:Khaki; border-radius: 15px 50px;">&nbsp;&nbsp;<spring:message code="project.isDraft"/>&nbsp;&nbsp;</span>
		<span style="background-color:Silver; border-radius: 15px 50px;">&nbsp;&nbsp;<spring:message code="project.isCancelled"/>&nbsp;&nbsp;</span>
	</jstl:if>
</security:authorize>



