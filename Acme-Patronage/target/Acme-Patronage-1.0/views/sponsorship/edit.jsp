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


<form:form action="${requestURI}" modelAttribute="sponsorship">

	<form:hidden path="id"/>
	<form:hidden path="corporation"/>
	<form:hidden path="version"/>
	
	<jstl:if test="${createBoolean eq true}">
		<acme:select code="sponsorship.project" path="project" items="${projects}" itemLabel="title"/><br/>
	</jstl:if>
	<jstl:if test="${createBoolean eq false}">
		<form:hidden path="project"/>
	</jstl:if>
	<acme:textbox code="sponsorship.banner" path="bannerURL"/>
	<acme:textbox code="sponsorship.page" path="pageURL"/>

	
	<br/>
	<input type="submit" name="save" value="<spring:message code="sponsorship.save"/>"/>
	<acme:cancel url="sponsorship/corporation/list.do" code="sponsorship.cancel" /> <br/>
</form:form>