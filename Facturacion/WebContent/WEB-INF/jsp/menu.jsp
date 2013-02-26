<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Dashboard | Amanda Admin Template</title>
<link rel="stylesheet" href="/FactElect/amandaTemplate/css/style.default.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="/FactElect/css/styleshome.css" />

<script type="text/javascript" src="/FactElect/amandaTemplate/js/plugins/jquery-1.7.min.js"></script>
<script type="text/javascript" src="/FactElect/amandaTemplate/js/plugins/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="/FactElect/amandaTemplate/js/plugins/jquery.cookie.js"></script>
<script type="text/javascript" src="/FactElect/amandaTemplate/js/plugins/jquery.uniform.min.js"></script>
<script type="text/javascript" src="/FactElect/amandaTemplate/js/plugins/jquery.flot.min.js"></script>
<script type="text/javascript" src="/FactElect/amandaTemplate/js/plugins/jquery.flot.resize.min.js"></script>
<script type="text/javascript" src="/FactElect/amandaTemplate/js/plugins/jquery.slimscroll.js"></script>
<script type="text/javascript" src="/FactElect/amandaTemplate/js/custom/general.js"></script>
<script type="text/javascript" src="/FactElect/amandaTemplate/js/custom/dashboard.js"></script>
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


</head>


<body class="withvernav">

<div class="bodywrapper">
 
    <jsp:include page="/WEB-INF/jsp/header.jsp" ></jsp:include>
    
    <div class="header">
    	<ul class="headermenu">
        	<li class="current"><a href="facturas/index.jsp"><span class="icon icon-flatscreen"></span>Facturas</a></li>
            <li><a href="../administracion/index.jsp"><span class="icon icon-pencil"></span>Administraci&oacute;n</a></li>
         
        </ul>
        
        <div class="headerwidget">
        	<div class="earnings">
            	<div class="one_half">
                	<h4>Folios Disponibles</h4>
                    <h2>76</h2>
                </div><!--one_half-->
               
            </div><!--earnings-->
        </div><!--headerwidget-->
        
        
    </div><!--header-->
    
    <div class="vernav2 iconmenu">
    	<ul id="menu">
    	<li><a id="1" href="forms.html"  class="gallery" >Emitir Factura</a></li>
                    <li><a id="2" href="wizard.html"  class="gallery">Corregir Factura</a></li>
                    <li><a id="3" href="editor.html" class="elements">Cancelar Factura</a></li>
                    <li><a id="4" href="forms.html" class="widgets">Consultar Factura</a></li>
                    <li><a id="5" href="wizard.html" class="calendar">Consultar Folios</a></li>
        
            
           
          
        </ul>
        <a class="togglemenu"></a>
        <br /><br />
    </div><!--leftmenu-->
        
    <div id="contenedor" class="centercontent">
    
     
        
	</div><!-- centercontent -->
    
    
</div><!--bodywrapper-->

</body>

</html>
