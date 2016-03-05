<!DOCTYPE html>
<!--[if IE 9]><html class="ie ie9"> <![endif]-->
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="keywords" content="Home cooked, Delivery, Fresh, Pure, Take Away, Meals, Delicious, Healthy, Nutritious, Ingredients, Comfortable, Timely">
    <meta name="description" content="">
    <meta name="author" content="Varunsk23">

    <title>HungerMeals</title>

    <!-- Favicons-->
    <link rel="shortcut icon" href="../images/favicon.ico" type="image/x-icon">
    <link rel="apple-touch-icon" type="image/x-icon" href="../images/apple-touch-icon-57x57-precomposed.png">
    <link rel="apple-touch-icon" type="image/x-icon" sizes="72x72" href="../images/apple-touch-icon-72x72-precomposed.png">
    <link rel="apple-touch-icon" type="image/x-icon" sizes="114x114" href="../images/apple-touch-icon-114x114-precomposed.png">
    <link rel="apple-touch-icon" type="image/x-icon" sizes="144x144" href="../images/apple-touch-icon-144x144-precomposed.png">
    
    <!-- BASE CSS -->
    <link href="../scripts/css/base.css" rel="stylesheet">
    
    <!-- Radio and check inputs -->
    <link href="../scripts/css/skins/square/grey.css" rel="stylesheet">

	<!-- HUNGER LINKS -->
    <link rel="stylesheet" href="../scripts/css/jquery-ui.css">
    <link rel="stylesheet" href="../scripts/css/jquery-ui.structure.css">
    <link rel="stylesheet" href="../scripts/css/jquery-ui.theme.css">
    <style type="text/css">
        /* begin: jQuery UI Datepicker moving pixels fix */
            table.ui-datepicker-calendar {border-collapse: separate;}
            .ui-datepicker-calendar td {border: 1px solid transparent;}
        /* end: jQuery UI Datepicker moving pixels fix */

        /* begin: jQuery UI Datepicker emphasis on selected dates */
        .ui-datepicker .ui-datepicker-calendar .ui-state-highlight a {
            background: #f27242 none; /* a color that fits the widget theme */
            color: white; /* a color that is readeable with the color above */
        }
        /* end: jQuery UI Datepicker emphasis on selected dates */

        .ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active{
            border: 1px solid #f27242;
        }

        .ui-state-highlight, .ui-widget-content .ui-state-highlight, .ui-widget-header .ui-state-highlight{
            border: 1px solid #f27242;
            background: rgba(242, 114, 66, 0.22) url("../images/ui-bg_highlight-soft_75_ffe45c_1x100.png") 50% top repeat-x;
            color: #363636;
        }

        .ui-widget-header {
            border: 1px solid #f27242;
            background: #f27242 url("../images/ui-bg_gloss-wave_35_f6a828_500x100.png") 50% 50% repeat-x;
            color: #ffffff;
            font-weight: bold;
        }
        .ui-state-disabled, .ui-widget-content .ui-state-disabled, .ui-widget-header .ui-state-disabled{
            opacity: .75;
        }
        .ui-state-highlight, .ui-widget-content .ui-state-highlight, .ui-widget-header .ui-state-highlight{
            background: rgb(242, 114, 66) url("../images/ui-bg_highlight-soft_75_ffe45c_1x100.png") 50% top repeat-x;
        }
    </style>

    <!--[if lt IE 9]>
      <script src="js/html5shiv.min.js"></script>
      <script src="js/respond.min.js"></script>
    <![endif]-->

</head>

<body>

<!--[if lte IE 8]>
    <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a>.</p>
<![endif]-->

	<div id="preloader">
        <div class="sk-spinner sk-spinner-wave" id="status">
            <div class="sk-rect1"></div>
            <div class="sk-rect2"></div>
            <div class="sk-rect3"></div>
            <div class="sk-rect4"></div>
            <div class="sk-rect5"></div>
        </div>
    </div><!-- End Preload -->

    <!-- Header ================================================== -->
    <header>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-4 col-sm-4 col-xs-4">
            </div>
            <div class="col-md-3 col-sm-3 col-xs-3 text-center">
                <a href="index.jsp" id="logo">
                <img src="../images/logo.png" width="190" height="23" alt="" data-retina="true" class="hidden-xs">
                <img src="../images/logo_mobile.png" width="30" height="23" alt="" data-retina="true" class="hidden-lg hidden-md hidden-sm">
                </a>
            </div>
            <nav class="col-md-5 col-sm-5 col-xs-5">
            <a class="cmn-toggle-switch cmn-toggle-switch__htx open_close" href="javascript:void(0);"><span>Menu mobile</span></a>
            <div class="main-menu">
                <div id="header_menu">
                    <img src="../images/logo.png" width="190" height="23" alt="" data-retina="true">
                </div>
                <a href="#" class="open_close" id="close_in"><i class="icon_close"></i></a>
                 <ul id="head_menu">
                    <li><a href="about.jsp">About</a></li>
                    <li><a href="mymeal.jsp">Make My Meal</a></li>
                    <li id="login_link"><a href="#0" data-toggle="modal" data-target="#login_2">Login</a></li>
                </ul>
            </div><!-- End main-menu -->
            </nav>
        </div><!-- End row -->
    </div><!-- End container -->
    </header>
    <!-- End Header =============================================== -->

<!-- Content ================================================== -->
<div class="container margin_80_35">
	<div class="row">
		<div class="col-md-10">
			<div class="box_style_2" id="planDetails">
                <!-- dynamic data -->
			</div>
		</div>
	</div><!-- End row -->
</div><!-- End container -->
<!-- End Content =============================================== -->

<!-- Footer ================================================== -->
    <footer>
        <div class="container">
        <div class="row">
            <div class="col-md-6 col-sm-6">
                <h3>About</h3>
                <ul>
                    <li><a href="about.jsp">About us</a></li>
                    <li><a href="faq.jsp">Faq</a></li>
                    <li><a href="termsnpolicy.jsp">Terms and Policy</a></li>
                    <li><a href="contact.jsp">Contact us</a></li>
                </ul>
            </div>
            <div class="col-md-6 col-sm-6" id="newsletter">
                 
            </div>
        </div><!-- End row -->
        <div class="row">
            <div id="social_footer">
                <ul>
                    <li><a href="https://www.facebook.com/hungermeals/"><i class="icon-facebook"></i></a></li>
                    <li><a href="https://twitter.com/HungerMeals2016"><i class="icon-twitter"></i></a></li>
                    <li><a href="https://www.instagram.com/hungermeals/"><i class="icon-instagram"></i></a></li>
                </ul>
                <p>© HungerMeals 2016</p>
            </div>                
        </div><!-- End row -->
        </div><!-- End container -->
    </footer>
<!-- End Footer =============================================== -->

<div class="layer"></div><!-- Mobile menu overlay mask --> 

<div class="modal fade" id="hm_alert" tabindex="-1" role="dialog" aria-labelledby="myhm_alert" aria-hidden="true"></div>
<!-- End hm_alert modal -->
    
<!-- Register modal -->   
<div class="modal fade" id="edit_mymeal" tabindex="-1" role="dialog" aria-labelledby="myedit_mymeal" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content modal-popup bg-white">
                <a href="#" class="close-link" style="color: RGBA(51, 51, 51, 0.59);"><i class="icon_close_alt2"></i></a>
                <div class="row margin15">
                    <label>Please re-schedule dates for remaining meal:</label>
                    <div class="col-md-6 col-lg-6">
                        <div id="calEditView"></div>
                        <input type="hidden" id="alt_sel_dates" />
                    </div>
                    <div class="col-md-6 col-lg-6">
                        <ul id="remData" class="remaining_meals_ul">
                        </ul>
                    </div>
                </div>
                <div class="row margin15">
                    <span id="resMsg"></span>
                </div>
                <div class="row margin15">
                    <div class="col-md-12 col-lg-12">
                        <a href="#0" class="btn_1" id="update_mymeal" style="display:none;">Update Plan</a>
                    </div>
                </div>
            </div>
        </div>
    </div><!-- End Register modal -->


<!-- COMMON SCRIPTS -->
<script src="../scripts/js/jquery-1.11.2.min.js"></script>
<script src="../scripts/js/common_scripts_min.js"></script>
<script src="../scripts/js/functions.js"></script>

<!-- HUNGER SCRIPTS -->
<script src="../scripts/jscript/js-cookie.js"></script>
<script src="../scripts/jscript/common.js"></script>
<script src="../scripts/jscript/mymeal_view.js"></script>

<!-- <script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script> -->
<script src="../scripts/jscript/jquery-ui.js"></script>
<script type="text/javascript" src="../scripts/jscript/jquery-ui-1.11.1.js"></script>
<script type="text/javascript" src="../scripts/jscript/jquery-ui.multidatespicker.js"></script>

<script>
	window.onload = function() {
  		setLoggedInfo();
	};
</script>

</body>
</html>