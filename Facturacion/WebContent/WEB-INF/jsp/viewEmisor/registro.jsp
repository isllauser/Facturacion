<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Registro | Amanda Admin Template</title>
<link rel="stylesheet" 	href="/FactElect/amandaTemplate/css/style.default.css" type="text/css" />
<link rel="stylesheet" 	href="/FactElect/amandaTemplate/css/validationEngine.jquery.css" 	type="text/css" />
<link rel="stylesheet" href="/FactElect/amandaTemplate/css/template.css" 	type="text/css" />

<script type="text/javascript" 	src="/FactElect/amandaTemplate/js/plugins/jquery-1.7.min.js"></script>
<script type="text/javascript" 	src="/FactElect/amandaTemplate/js/plugins/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" 	src="/FactElect/amandaTemplate/js/plugins/jquery.cookie.js"></script>
<script type="text/javascript" 	src="/FactElect/amandaTemplate/js/plugins/jquery.uniform.min.js"></script>
<script type="text/javascript" 	src="/FactElect/amandaTemplate/js/plugins/jquery.smartWizard-2.0.min.js"></script>
<script type="text/javascript" 	src="/FactElect/amandaTemplate/js/plugins/jquery.colorbox-min.js"></script>
<script type="text/javascript" 	src="/FactElect/amandaTemplate/js/custom/general.js"></script>
<script type="text/javascript" 	src="/FactElect/amandaTemplate/js/plugins/jquery.validate.min.js"></script>
<script src="/FactElect/amandaTemplate/js/languages/jquery.validationEngine-es.js" type="text/javascript" charset="utf-8"></script> 
<script src="/FactElect/amandaTemplate/js/jquery.validationEngine.js" 	type="text/javascript" charset="utf-8"></script>
<!--[if lte IE 8]><script language="javascript" type="text/javascript" src="js/plugins/excanvas.min.js"></script><![endif]-->
<!--[if IE 9]>
    <link rel="stylesheet" media="screen" href="css/style.ie9.css"/>
<![endif]-->
<!--[if IE 8]>
    <link rel="stylesheet" media="screen" href="css/style.ie8.css"/>
<![endif]-->
<!--[if lt IE 9]>
	<script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>
<![endif]-->
<script type="text/javascript">
	window.scrollTo(0, 1);
</script>
<script type="text/javascript">
	jQuery(document).ready(function() {
		// Smart Wizard 	
		jQuery('#wizard').smartWizard({
			onLeaveStep : leaveAStepCallback,
			onFinish : onFinishCallback
		});
		jQuery('#wizard2').smartWizard({
			onLeaveStep : leaveAStepCallback,
			onFinish : onFinishCallback
		});
		jQuery('#wizard3').smartWizard({
			onLeaveStep : leaveAStepCallback,
			onFinish : onFinishCallback
		});
		jQuery('#wizard4').smartWizard({
			onLeaveStep : leaveAStepCallback,
			onFinish : onFinishCallback
		});

		function leaveAStepCallback(obj) {
			var step_num = obj.attr('rel');
			if (validateStep3(step_num))
				return jQuery('#formID').validationEngine('validate');
			else
				return false;
		}

		function onFinishCallback() 
		{
		   if(document.getElementById("agree").checked)
		   {
             alert(" La cuenta se ha creado satisfactoriamente. Le hemos enviado al correo electrÛnico registrado la confirmaciÛn de su alta junto con los datos de su cuenta.");
		     document.getElementById('formID').submit();
		   }
		   else
		   {
		     alert("Debe acordar con los terminos y condiciones para crear su cuenta");
		   }
	    }

		jQuery(".inline").colorbox({
			inline : true,
			width : '60%',
			height : '500px'
		});

		jQuery('select, input:checkbox').uniform();

	});

	function validateStep3(step) {

		var isValid = true;
		//validate email  email

		if (step == 2) {
			var estado = jQuery('#estado').val();
			var delegacion = jQuery('#delegacion').val();

			if (estado && estado.length > 0) {
				isValid = true;
				jQuery('#msg_estado').html('Por favor seleccione un estado')
						.hide();

				if (delegacion && delegacion.length > 0) {
					isValid = true;
					jQuery('#msg_delegacion').html(
							'Por favor seleccione un municipio o delegacion')
							.hide();
				} else {
					isValid = false;
					jQuery('#msg_delegacion').html(
							'Por favor seleccione un municipio o delegacion')
							.show();
				}

			} else {
				isValid = false;
				jQuery('#msg_estado').html('Por favor seleccione un estado')
						.show();

				if (delegacion && delegacion.length > 0) {
					jQuery('#msg_delegacion').html(
							'Por favor seleccione un municipio o delegacion')
							.hide();
				}

			}

		}
		return isValid;
	}
</script>



<script>
	jQuery(document).ready(function() {
		// binds form submission and fields to the validation engine
		jQuery("#formID").validationEngine();
	});
</script>

<script type="text/javascript">

    function activarTipoPersona(tipo)
    {
       if(tipo == "F")
       {
         document.getElementById("personaFisica").style.display = 'block';
         document.getElementById("personaMoral").style.display = 'none';
       }
       
       if(tipo == "M")
       {
         document.getElementById("personaFisica").style.display = 'none';
         document.getElementById("personaMoral").style.display = 'block';
       }
    }
    
</script>

<!-- ****************************************************************************** -->
<c:url var="findStateCitiesURL" value="/delegmunicipio" />
<c:url var="findStatesURL" value="/estados" />
<script type="text/javascript">
jQuery(document).ready(function() { 
	jQuery('#estado').change(
			function() {
				jQuery.getJSON('${findStateCitiesURL}', {
					stateName : $(this).val(),
					ajax : 'true'
				}, function(data) {
					var html = '<option value="">City</option>';
					var len = data.length;
					for ( var i = 0; i < len; i++) {
						html += '<option value="' + data[i].name + '">'
								+ data[i].name + '</option>';
					}
					html += '</option>';

					jQuery('#delgMunicipio').html(html);
				});
			});
});
</script>

<script type="text/javascript">
jQuery(document).ready(
			function() {
				jQuery.getJSON('${findStatesURL}', {
					ajax : 'true'
				}, function(data) {
					var html = '<option value="">State</option>';
					var len = data.length;
					for ( var i = 0; i < len; i++) {
						html += '<option value="' + data[i].name + '">'
								+ data[i].name + '</option>';
					}
					html += '</option>';

					jQuery('#estado').html(html);
				});
			});
</script>

<script type="text/javascript">
function validate_fileupload(fileName)
{
	if(!/(\.jpg|\.jpeg|\.gif|\.png)$/i.test(fileName.value)) {
        alert("Tipo de imagen invalida, el archivo debe de ser de tipo .jpg, .gif y .png");
        document.getElementById('file').value ='';
        fileName.focus();         
    }
	 var fileInput = document.getElementById ('file');     
	 for (var i = 0; i < fileInput.files.length; i++) {
		 var file = fileInput.files[i];
		 if ('size' in file) {
			 if(file.size>512000)
			 {
			 	alert("El archivo seleccionada sobrepasa las dimensiones de 500kb");
			 	document.getElementById('file').value ='';
			 	break;
			 }
		 }
	 }
}
</script>




 
<!-- ****************************************************************************** -->
<style>
.error {
	color: #ff0000;
}
 
.errorblock {
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
</style>

</head>


<body>

	<div class="bodywrapper">

		<jsp:include page="/WEB-INF/jsp/header-register.jsp"></jsp:include>



		<div id="contentwrapper" class="contentwrapper">
			<div class="pageheader">
				<h1 class="pagetitle">Registrar Emisor</h1>
				<span class="pagedesc">Por favor ingrese la siguiente
					informaci&oacute;n para la alta de su cuenta</span>


			</div>
			<!--pageheader-->
			<div id="tabbed" class="subcontent">

				<!-- START OF TABBED WIZARD -->
				<form:form id="formID" class="stdform" method="post" action="addRegistro" modelAttribute="registroEmisorForm1" enctype="multipart/form-data">
				<form:errors path="*" cssClass="errorblock" element="div" />
					<div id="wizard2" class="wizard">

						<ul class="tabbedmenu">
							<li><a href="#wiz1step2_1"> <span class="h2">Paso
										1</span> <span class="label">Informaci&oacute;n B&aacute;sica</span>
							</a></li>
							<li><a href="#wiz1step2_2"> <span class="h2">Paso
										2</span> <span class="label">Informaci&oacute;n Fiscal</span>
							</a></li>
							<li><a href="#wiz1step2_3"> <span class="h2">Paso
										3</span> <span class="label">Terminos y condiciones</span>
							</a></li>
						</ul>

						<br clear="all" />
						<br />
						<br />

						<div id="wiz1step2_1" class="formwiz">
							<h4>Paso 1: Informaci&oacute;n B&aacute;sica</h4>

							<p>
								<label></label> <span class="formwrapper"> 
								
								<%-- <input type="radio" class="validate[required] radio" name="persona" path="persona" value="F"/> Persona F&iacute;sica &nbsp; &nbsp; 
								<input type="radio" class="validate[required] radio" name="persona" path="persona" value="M"/> Persona Moral &nbsp; &nbsp;--%>
								<!--  <br></br> -->
								<form:radiobutton name="tipoPersona" path="emisor.tippoPersona" value="F" class="validate[required] radio" onChange="activarTipoPersona('F')"/> Persona F&iacute;sica &nbsp; &nbsp; 
								<form:radiobutton name="tipoPersona" path="emisor.tippoPersona" value="M" class="validate[required] radio" onChange="activarTipoPersona('M')"/> Persona Moral &nbsp; &nbsp;
								
								
								</span>
							</p>

							<p>
								<label>RFC *</label> <span class="field">
								<form:input id="rfc" path="emisor.rfcEmisor" value="${registroEmisorForm1.emisor.rfcEmisor}" class="validate[required, custom[onlyLetterNumber], minSize[12], maxSize[13]] text-input" />
								<%-- <input type="text" maxlength="13" name="rfc" id="rfc" class="validate[required] text-input" path="rfcEmisor"/>--%>
								</span>
							</p>

							<p>
								<label>Confirmaci&oacute;n RFC *</label> <span class="field">
								<form:input path="confirmarRFC" value="${registroEmisorForm1.confirmarRFC}" class="validate[required,equals[rfc]] text-input"/>
								
								
								<%-- <input type="text" maxlength="13" name="confirmacionRFC" id="confirmacionRFC" class="validate[required,equals[rfc]] text-input"/>--%>
								</span>
							</p>

							<p>
								<label>Contraseña *</label> <span class="field">
								<form:input  type="password" id="password" path="emisor.passwd" value="${registroEmisorForm1.emisor.passwd}"  class="validate[required, minSize[8], custom[onlyLetterNumber]] text-input" />
								
								<%-- <input type="password" maxlength="10" name="password" id="password" class="validate[required] text-input" path="passwd" />--%>
								</span>
							</p>
							<p>
								<label>Confirmaci&oacute;n de Contraseña *</label> <span class="field">
								<form:input type="password" path="confirmarPassword" value="${registroEmisorForm1.confirmarPassword}" class="validate[required,equals[password]] text-input"/>
								
								<%--<input type="password" maxlength="10" name="password2" id="password2" class="validate[required,equals[password]] text-input" /> --%>
								
								</span>
							</p>
							<p>
								<label>Correo Electr&oacute;nico *</label> <span class="field">
								<form:input path="informacionFiscal.correo"  value="${registroEmisorForm1.informacionFiscal.correo}" class="validate[required,custom[email]] text-input" />
								<%--<input type="text" maxlength="50" name="email" id="email" class="validate[required,custom[email]] text-input" /> --%>
								
								</span>
							</p>





						</div>
						<!--#wiz1step2_1-->

						<div id="wiz1step2_2" class="formwiz">
							<h4>Paso 2: Persona F&iacute;sica/Moral</h4>
                         <div id="personaFisica">
							<p>
								<label>Nombre(s) *</label> <span class="field">
								
								<form:input path="informacionFiscal.nombre"  value="${registroEmisorForm1.informacionFiscal.nombre}" class="validate[required, custom[onlyLetterSp]] text-input" />
								<%--<input	type="text" maxlength="50" name="nombre" class="validate[required] text-input" />--%>
								
								</span>
							</p>
							<p>
								<label>Apellido Paterno *</label> 
								<span class="field">
									<form:input path="informacionFiscal.apellidoPaterno"  value="${registroEmisorForm1.informacionFiscal.apellidoPaterno}" class="validate[required, custom[onlyLetterSp]] text-input" />
									<%--<input type="text" maxlength="30" name="apellidoPaterno" class="validate[required] text-input" />--%>
								</span>
							</p>
							<p>
								
								<label>Apellido Materno *</label> 
								<span class="field">
									<form:input path="informacionFiscal.apellidoMaterno"  value="${registroEmisorForm1.informacionFiscal.apellidoMaterno}" class="validate[required, custom[onlyLetterSp]] text-input"/>
									<%--<input type="text" maxlength="30" name="apellidoMaterno" class="validate[required] text-input" />--%>
								
								</span>
							</p>
						</div>
						<div id="personaMoral">
						  <p>
						<label>Raz&oacute;n Social *</label> 
								<span class="field">
								     <form:input path="informacionFiscal.razonSocial"  value="${registroEmisorForm1.informacionFiscal.razonSocial}" class="validate[required, custom[onlyLetterSp]] text-input" />
								<%--<input	type="text" maxlength="50" name="razonSocial" class="validate[required] text-input" />--%>
						       </span>
							</p>
						</div>
						 
							<p>
								<label>Estado *</label> 
								<form:select id="estado" path="informacionFiscal.municipios.id.estadosIdEstado">
								</form:select> 
								<span id="msg_estado" style="color:red"></span>&nbsp;
							</p>
							<p>
								<label>Delegaci&oacute;n o Municipio *</label> 
								<form:select id="delegMunicipio" path="informacionFiscal.municipios.id.idMunicipio">
								<!-- <form:option value="">Delegaci&oacute; o Municipio</form:option> -->
								</form:select> 
								<span id="msg_delegacion" style="color:red"></span>&nbsp;
							</p>
		
							
							
							<p>
								<label>Direcci&oacute;n *</label>
								<span class="field">
									<form:input path="informacionFiscal.direccion"  value="${registroEmisorForm1.informacionFiscal.direccion}" class="validate[required] text-input" />
									<%--<input type="text" maxlength="80" name="direccion" class="validate[required] text-input" />--%>
								</span>
							</p>
							<p>
								<label>C&oacute;digo Postal *</label> 
								<span class="field">
									<form:input path="informacionFiscal.cp"  value="${registroEmisorForm1.informacionFiscal.cp}" class="validate[required, custom[onlyNumberSp], maxSize[5]] text-input" />
									<%--<input type="text" maxlength="5" name="codigoPostal" class="validate[required] text-input" />--%>
								</span>
							</p>
							<p>
								<label>Tel&eacute;fono *</label>
								<span class="field">
									<form:input path="informacionFiscal.telefono"  value="${registroEmisorForm1.informacionFiscal.telefono}"  class="validate[required, custom[phone] text-input"/>
									<%--<input	type="text" maxlength="10" name="telefono" class="validate[required] text-input" />--%>
								</span>
							</p>

							<p>
								<label>Logo</label> <span class="field">
								<form:input	type="file" path="file"  onChange="validate_fileupload(file)" id="file"/>
								<%-- <input type="file" name="logo" />--%>
								</span>
							</p>

						</div>
						<!--#wiz1step2_2-->

						<div id="wiz1step2_3">
							<h4>Paso 3: Terminos y Condiciones</h4>
							<div class="par terms">
								<p>AVISO DE PRIVACIDAD</p>
								
								<p>
									<input class="validate[required] checkbox" type="checkbox"
										id="agree" name="agree" />Estoy de acuerdo 
									
								</p>
							</div>
						</div>
						<!--#wiz1step2_3-->

					</div>
					<!--#wizard-->
				</form:form>

				<!-- END OF TABBED WIZARD -->

			</div>
			<!-- #tabbed -->

		</div>
		<!-- centercontent -->


	</div>
	<!--bodywrapper-->

</body>

</html>
