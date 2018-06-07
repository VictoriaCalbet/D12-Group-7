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



	<form:form action="${requestURI}" modelAttribute="patronageForm">
	<form:hidden path="id"/>
	<form:hidden path="project"/>
	<acme:textbox code="patronage.amount" path="amount"/>

	<fieldset>
	<spring:message code="patronage.creditCard" var="patronageCreditCardLegend"/>
			<spring:message code="patronage.creditCard.legend" var="patronageCreditCardLegend"/>
			<legend><b><jstl:out value="${patronageCreditCardLegend}"/>:&nbsp;</b></legend>	
	<acme:textbox code="patronage.creditCard.brandName" path="creditCard.brandName"/>
	<acme:textbox code="patronage.creditCard.holderName" path="creditCard.holderName"/>
	<acme:textbox code="patronage.creditCard.number" path="creditCard.number"/>
	<acme:textbox code="patronage.creditCard.expirationMonth" path="creditCard.expirationMonth" maxlength="2"/>
	<acme:textbox code="patronage.creditCard.expirationYear" path="creditCard.expirationYear" maxlength="4"/>
	<acme:textbox code="patronage.creditCard.cvv" path="creditCard.cvv" maxlength="3"/>

</fieldset>
<input type="submit" name="save" value="<spring:message code="patronage.save"/>"/>
<acme:cancel url="project/list.do" code="patronage.cancel" /> <br/>

</form:form>