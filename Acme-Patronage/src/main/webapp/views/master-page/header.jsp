<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div>
	<a href=""><img src="images/logo.jpg"
		alt="Acme-Patronage Co., Inc." height="200"/></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
		</security:authorize>

		<security:authorize access="permitAll">
			<li><a class="fNiv" href="user/list.do"><spring:message code="master.page.user"/></a></li>
			<li><a class="fNiv" href="project/list.do"><spring:message code="master.page.project"/></a></li>
			<li><a class="fNiv" href="category/list.do"><spring:message code="master.page.category"/></a></li>
		</security:authorize>
		
		<!-- #User -->
		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv" href="project/user/list.do"><spring:message code="master.page.project.user.list" /></a></li>	
		</security:authorize>

		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv" href="project/administrator/list.do"><spring:message code="master.page.project.admin.list" /></a></li>
			<li><a class="fNiv"><spring:message
						code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="moderator/administrator/create.do"><spring:message
								code="master.page.moderator.create" /></a></li>
				</ul>
			</li>
		</security:authorize>


		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message
						code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/action-1.do"><spring:message
								code="master.page.customer.action.1" /></a></li>
					<li><a href="customer/action-2.do"><spring:message
								code="master.page.customer.action.2" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv"><spring:message
						code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="user/create.do"><spring:message
								code="master.page.user.create" /></a></li>
					<li><a href="corporation/create.do"><spring:message
								code="master.page.corporation.create" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('USER')">
						<li><a href="user/user/edit.do"><spring:message
									code="master.page.profile.display" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('ADMIN')">
						<li><a href="administrator/administrator/edit.do"><spring:message
									code="master.page.profile.display" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('MODERATOR')">
						<li><a href="moderator/moderator/edit.do"><spring:message
									code="master.page.profile.display" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('CORPORATION')">
						<li><a href="corporation/corporation/edit.do"><spring:message
									code="master.page.profile.display" /></a></li>
					</security:authorize>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

