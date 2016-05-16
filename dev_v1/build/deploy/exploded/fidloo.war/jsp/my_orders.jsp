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
    
    <!--[if lt IE 9]>
      <script src="js/html5shiv.min.js"></script>
      <script src="js/respond.min.js"></script>
    <![endif]-->

	<!-- HUNGER LINKS -->
	<link rel="stylesheet" href="../scripts/css/hungerstyle.css">

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

<!-- SubHeader =============================================== -->
<section class="parallax-window" id="short" data-parallax="scroll" data-image-src="../images/my-orders.jpg" data-natural-width="1400" data-natural-height="350">
    <div id="subheader">
	<div id="sub_content">
        <h1 id="xcust_name"></h1>
    </div><!-- End sub_content -->
</div><!-- End subheader -->
</section><!-- End section -->
<!-- End SubHeader ============================================ -->

    <div id="position">
        <div class="container">
            <ul>
                <li><a href="index.jsp">Home</a></li>
                <li>My Orders</li>
            </ul>
        </div>
    </div><!-- Position -->

<!-- Content ================================================== -->
<div class="container margin_80_35">
	<div id="container_pin">
		<div class="row">
        
			<div class="col-md-12">
				<div class="box_style_2" id="main_menu">
					<h2 class="inner">My Orders</h2>
					<div id="my_ord_list"></div>
					<hr>
				</div><!-- End box_style_1 -->
			</div><!-- End col-md-12 -->
            
		</div><!-- End row -->
	</div><!-- End container pin -->
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
    
<!-- COMMON SCRIPTS -->
<script src="../scripts/js/jquery-1.11.2.min.js"></script>
<script src="../scripts/js/common_scripts_min.js"></script>
<script src="../scripts/js/functions.js"></script>
<script src="../scripts/assets/validate.js"></script>


<!-- SPECIFIC SCRIPTS -->
<script  src="../scripts/js/cat_nav_mobile.js"></script>
<script>$('#cat_nav').mobileMenu();</script>
<script src="../scripts/js/jquery.pin.min.js"></script>
<script>$("#cart_box").pin({padding: {top: 80, bottom: 25},minWidth: 1100, containerSelector: "#container_pin"})</script>
<script>
 $(function() {
	 'use strict';
	  $('a[href*=#]:not([href=#])').click(function() {
	    if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'') && location.hostname == this.hostname) {
	      var target = $(this.hash);
	      target = target.length ? target : $('[name=' + this.hash.slice(1) +']');
	      if (target.length) {
	        $('html,body').animate({
	          scrollTop: target.offset().top - 70
	        }, 1000);
	        return false;
	      }
	    }
	  });
	});
</script>

<!-- HUNGER SCRIPTS -->
<script src="../scripts/jscript/js-cookie.js"></script>
<script src="../scripts/jscript/common.js"></script>
<script src="../scripts/jscript/my_details.js"></script>


<script>
	window.onload = function() {
         $("#short").css("background-size","100% 100%");
  		setLoggedInfo();
	};

</script>

</body>
</html>