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

<%-- Menús para project, dependiendo de cada rol --%>

<security:authorize access="hasAnyRole('MODERATOR', 'CORPORATION') or isAnonymous()">
				<li><a class="fNiv"><spring:message code="master.page.projectAll" /></a>
					<ul>
						<li class="arrow"></li>
						<li><a href="project/list.do"><spring:message code="master.page.project" /></a></li>
						<li><a href="project/listOrdered.do"><spring:message code="master.page.projectOrdered" /></a></li>
					</ul>
				</li>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
				<li><a class="fNiv"><spring:message code="master.page.projectAll" /></a>
					<ul>
						<li class="arrow"></li>
						<li><a href="project/list.do"><spring:message code="master.page.project" /></a></li>
						<li><a href="project/administrator/list.do"><spring:message code="master.page.project.admin.list" /></a></li>	
						<li><a href="project/listOrdered.do"><spring:message code="master.page.projectOrdered" /></a></li>
					</ul>
				</li>
</security:authorize>

<security:authorize access="hasRole('USER')">
				<li><a class="fNiv"><spring:message code="master.page.projectAll" /></a>
					<ul>
						<li class="arrow"></li>
						<li><a href="project/list.do"><spring:message code="master.page.project" /></a></li>
						<li><a href="project/user/list.do"><spring:message code="master.page.project.user.list" /></a></li>
						<li><a href="project/listOrdered.do"><spring:message code="master.page.projectOrdered" /></a></li>
					</ul>
				</li>
</security:authorize>
<%-- Fin de los menús para project --%>




		<security:authorize access="permitAll">
				<li><a class="fNiv" href="user/list.do"><spring:message code="master.page.user"/></a></li>
				<li><a class="fNiv" href="corporation/list.do"><spring:message code="master.page.corporation"/></a></li>
				<li><a class="fNiv" href="category/list.do"><spring:message code="master.page.category"/></a></li>
		</security:authorize>
		
		<!-- #User -->
		<security:authorize access="hasRole('USER')">			
			<%-- Menú para patronages --%>
			<li><a class="fNiv"><spring:message code="master.page.patronage" /></a>
					<ul>
						<li class="arrow"></li>
						<li><a href="patronage/user/listPatronagesToMyProjects.do"><spring:message code="master.page.patronagesToMyProjects.user.list" /></a></li>	
						<li><a href="patronage/user/list.do"><spring:message code="master.page.patronages.user.list" /></a></li>			
					</ul>
				</li>
			<%-- Fin del menú para patronages --%>
			
			<li><a class="fNiv" href="report/user/create.do"><spring:message code="master.page.report.user.create" /></a></li>		
			
			<%-- Menú para comentarios --%>
			<li><a class="fNiv"><spring:message code="master.page.comments" /></a>
					<ul>
						<li class="arrow"></li>
						<li><a href="awardComment/user/list.do"><spring:message code="awardComment.list"/></a></li>			
						<li><a href="projectComment/user/list.do"><spring:message code="projectComment.list"/></a></li>			
						<li><a href="announcementComment/user/list.do"><spring:message code="announcementComment.listOwn"/></a></li>			
					</ul>
				</li>
			<%-- Fin del menú para comentarios --%>
			
		</security:authorize>
		
		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv"><spring:message code="master.page.announcement" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="announcement/user/stream.do"><spring:message code="master.page.showStreamOfAnnouncements" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message code="master.page.award" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="award/user/list.do"><spring:message code="master.page.myAwards" /></a></li>
				</ul>
			</li>
		</security:authorize>

		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv" href="comment/administrator/list.do"><spring:message code="comment.list"/></a></li>			
			<li><a class="fNiv" href="administrator/dashboard.do"><spring:message code="master.page.dashboard"/></a></li>			
			<li><a class="fNiv"><spring:message
						code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="moderator/administrator/create.do"><spring:message
								code="master.page.moderator.create" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="report/administrator/list.do"><spring:message code="master.page.report.administrator.list" /></a></li>
		</security:authorize>
		<security:authorize access="hasRole('MODERATOR')">
			<li><a class="fNiv" href="report/moderator/list.do"><spring:message code="master.page.report.moderator.list" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('CORPORATION')">
			<li><a class="fNiv" href="sponsorship/corporation/list.do"><spring:message code="master.page.sponsorshipt.corporation.list" /></a></li>
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
			<li><a class="fNiv"><spring:message
						code="message.messages" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="message/actor/outbox.do"><spring:message
								code="master.page.message.outbox" /></a></li>
					<li><a href="message/actor/inbox.do"><spring:message
								code="master.page.message.inbox" /></a></li>
					<li><a href="message/actor/create.do"><spring:message
								code="master.page.message.create" /></a></li>
				</ul>
			</li>
			
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

