<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">
	function formSubmit() {
		document.getElementById("lg").submit();
	}
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sistema de Facturacion Electronica</title>
<link rel="stylesheet" type="text/css" href="../css/styleshome.css" />

</head>
<body>
	<div id="main_container">

		<div id="header">

			<div id="logo">
				<a href="index.html"><img src="../images/logo.png" width="147"
					height="78" alt="" title="" border="0" /></a>
			</div>

			<div class="banner_adds"></div>
			<div class="menu">
				<ul>
					<li><a href="index.jsp">Home</a></li>
				</ul>
			</div>
		</div>
		<div id="main_content">
			<div class="column1">
				<div class="left_box">
					<div class="top_left_box"></div>
					<div class="center_left_box">
						<div class="box_title">
							<span>Entrar</span> al Sistema
						</div>
						<div class="box_title">
							<span></span>
						</div>

						<form id="lg" method="POST"
							action="<c:url value="/j_spring_security_check" />">

							<div class="form">
								<div class="form_row">
									<label class="left">RFC: </label><input type="text"
										class="form_input" name="j_username" />
								</div>
								<div class="form_row">
									<label class="left">Password: </label><input type="password"
										class="form_input" name="j_password" />
								</div>
								<div style="float: right; padding: 10px 25px 0 0;">
									<input type="image" src="../images/login.gif"
										onclick="formSubmit()" />
								</div>

							</div>
						</form>
					</div>
					<div class="bottom_left_box"></div>
				</div>
				<div class="left_box">
					<div class="top_left_box"></div>
					<div class="center_left_box">
						<div class="box_title">
							<span>Registrarse</span> en el Sistema
						</div>
						<div class="form">
							<div style="float: center; padding: 10px 25px 0 0;">
							<a href="registro.html"><img alt="Registro" src="../images/join.gif"></a>
								
							</div>

						</div>
					</div>
					<div class="bottom_left_box"></div>
				</div>
				<div class="left_box">
					<div class="top_left_box"></div>
					<div class="center_left_box">
						<div class="box_title">
							<span>Informacion</span> de contacto:
						</div>
						<div class="form">
							<div class="form_row">
								<img src="../images/contact_envelope.gif" width="50" height="47"
									border="0" class="img_right" alt="" title="" />
								<div class="contact_information">
									Email: contacto@islla.com<br /> Telephone: 0234 789 90<br />
									Mobile: 234 345 234534<br /> Fax: 34534 3456 3456(54)<br />
									<br /> <span>www.islla.com</span>
								</div>
							</div>
						</div>


					</div>
					<div class="bottom_left_box"></div>
				</div>

			</div>
			<!-- end of column one -->

			<div class="column2">
				<div class="main_text_box">
					<h2>Empresa</h2>
					<p>Ayudar a que nuestros clientes se enfoquen en el
						cumplimiento de sus objetivos estrategicos de su negocio, haciendo
						nuestros los compromisos derivados de sus necesidades de TI,
						ofreciendo soluciones basadas fuertemente en el uso adecuado de la
						tecnología y el conocimiento y talento de nuestra gente.</p>
				</div>

				<div class="main_text_box">
					<h2>Comprobantes Fiscales Digitales</h2>
					<div class="proposal">
						<p class="proposal_text">Sistema que le ayudara a emitir sus
							comprobantes fiscales por medios digitales.</p>
					</div>
				</div>
			</div>
			<!-- end of column two -->

		</div>
		<!-- end of main_content -->

		<div id="footer">

			<div id="copyright">
				<div style="float: left; padding: 3px;">
					<a href="#"><img src="../images/footer_logo.gif" width="42"
						height="35" alt="" title="" border="0" /></a>
				</div>
				<div style="float: left; padding: 14px 10px 10px 10px;">iSLLA
					Consulting Group.&copy; Todos los derechos reservados 2012</div>
			</div>


			<ul class="footer_menu">
				<li><a href="index.html" class="nav_footer"> Home </a></li>
				<li><a href="#" class="nav_footer"> Noticias </a></li>
				<li><a href="" class="nav_footer"> RSS </a></li>
				<li><a href="" class="nav_footer"> Contacto </a></li>
			</ul>

		</div>


	</div>
	<!-- end of main_container -->

</body>
</html>