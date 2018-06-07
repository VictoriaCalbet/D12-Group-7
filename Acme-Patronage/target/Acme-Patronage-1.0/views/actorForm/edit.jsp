<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<!-- Form -->
<form:form action="${requestURI}" modelAttribute="actorForm">

	<!-- Hidden attributes -->
	<form:hidden path="id"/>
	
	<!-- Editable attributes -->
	<acme:textbox code="actorForm.fullName" path="fullName"/>
	<acme:textbox code="actorForm.email" path="email"/>
	<acme:textarea code="actorForm.bio" path="bio"/>
	<acme:textbox code="actorForm.telephone" path="telephone"/>
	
	<jstl:choose>
		<jstl:when test="${actorForm.id == 0}"> <acme:textbox code="actorForm.username" path="username" readonly="false"/> </jstl:when>
		<jstl:when test="${actorForm.id != 0}"> <acme:textbox code="actorForm.username" path="username" readonly="true"/> </jstl:when>
	</jstl:choose>	
	
	<acme:password code="actorForm.password" path="password"/>
	<acme:password code="actorForm.repeatedPassword" path="repeatedPassword"/>
	
	<jstl:if test="${actorForm.id == 0}">
		<acme:checkbox code="actorForm.acceptTerm" path="acceptTerms" />
		<a href="legal-terms/index.do"> <spring:message code="actorForm.acceptTerm.text" /></a>
	</jstl:if><br/>
		
	
	<!-- Action buttons -->
	<acme:submit name="save" code="actorForm.save" /> &nbsp;
	<acme:cancel url="/" code="actorForm.cancel" /> <br/>	

</form:form>