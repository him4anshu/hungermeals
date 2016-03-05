<!DOCTYPE html>
<!--[if IE 9]><html class="ie ie9"> <![endif]-->
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="keywords" content="Home cooked, Delivery, Fresh, Pure, Take Away, Meals, Delicious, Healthy, Nutritious, Ingredients, Comfortable, Timely">
    <meta name="description" content="">
    <meta name="author" content="varunsk23@gmail.com">

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
<section class="parallax-window" id="short" data-parallax="scroll" data-image-src="../images/menu.jpg" data-natural-width="1400" data-natural-height="350">
	<div id="subheader">
    	<div id="sub_content" class="">
            <h1>From the home cooks kitchen</h1></br><h3>Scroll to view menu.</h3>
        </div><!-- End sub_content -->
	</div><!-- End subheader -->
	<div id="count" class="hidden-xs">
        <ul>
            <li>We are in:</li>
            <li>Koramangala</li>
            <li>HSR Layout</li>
            <li>BTM Layout</li>
            <li>Madiwala</li>
            <li>Bellandur and expanding rapidly...</li>
        </ul>
    </div>	
</section><!-- End section -->
<!-- End SubHeader ============================================ -->

<!-- Content ================================================== -->
<div class="container margin_60_35">
    <div id="position1" style="display: none">
        <div class="container">
        	<h4 class="no_delivery_banner"><i class="icon-frown"></i>Sorry!!! We will deliver soon there.</h4>
        </div>
    </div><!-- Position1 -->
	<div id="container_pin">
		<div class="row">
        
			<div class="col-md-9">
				<div class="box_style_2" id="main_menu" style="background: rgba(255, 255, 255, 0);">
					<!-- <h2 class="inner">Menu</h2> -->
					<!-- <h3 class="nomargin_top" id="starters">South Meals</h3> -->
					<p class="maco_desc"></p>
<!-- 					<div class="strip_list1 wow fadeIn" data-wow-delay="0.1s">
						<div class="ribbon_1">
							Popular
						</div>
						<div class="row">
							<div class="col-md-9 col-sm-9">
								<div class="desc1">
									<div class="thumb_strip1">
										<a href="detail_page.html"><img src="img/thumb_restaurant.jpg" alt=""></a>
									</div>
									<div class="rating">
										<i class="icon_star voted"></i><i class="icon_star voted"></i><i class="icon_star voted"></i><i class="icon_star voted"></i><i class="icon_star"></i> (<small><a href="#0">98 reviews</a></small>)
									</div>
									<h3>North Thali</h3>
									<div class="type">
										North
									</div>
									<div class="location hidden-sm visible-lg-block">
										135 Newtownards Road, Belfast, BT4.
									</div>
									<ul>
										<li><strong><i class="icon-rupee"></i>55</strong></li>
									</ul>
								</div>
							</div>
							<div class="col-md-3 col-sm-3">
								<div class="go_to">
									<div>
										<a href="#0" onclick='cartfiller(1,"Varun",44)'><i class="icon_plus_alt2"></i></a>
									</div>
								</div>
							</div>
						</div>
					</div>--><!-- End strip_list-->

					
					<!-- TEMPLATE - 3 -->
<!-- 					<div class="row magnific-gallery">
	                   	<div class="col-md-6">
	                   		<a href="img/img_1.jpg" title="Photo title"><img src="img/img_1.jpg" alt="" class="img-responsive"></a>
	                   	</div>
	                   	<div class="col-md-3">
							<div class="desc2">
								<h3>Taco Mexican</h3>
								<div class="type">
									Mexican / American
								</div>
								<div class="location">
									135 Newtownards Road, Belfast, BT4. <span class="opening">Opens at 17:00.</span> Minimum order: $15
								</div>
								<ul>
									<li>Take away<i class="icon_check_alt2 ok"></i></li>
								</ul>
							</div>
	                   	</div>
	                   	<div class="col-md-3">
							<div class="go_to">
								<div>
									<a href="detail_page.html" class="btn_1">View Menu</a>
								</div>
							</div>
	                   	</div>
	               	</div> -->
	               
					<div>
						<div class="row magnific-gallery wow fadeIn" data-wow-delay="0.1s" style="padding: 10px;"  id="starters_list">
						</div>
					</div>
					<!-- <h3 id="main_courses">North Meals</h3> -->
					<p class="maco_desc"></p>
					<hr>
					<div>
						<div class="row magnific-gallery wow fadeIn" data-wow-delay="0.1s" style="padding: 10px;"  id="main_courses_list">
						</div>

					</div>
					<hr>

<!--
REMOVE comment to get data 
					<h3 id="biryani">Biryani</h3>
					<p class="bir_desc"></p>
					<div>
						<div class="row magnific-gallery wow fadeIn" data-wow-delay="0.1s" style="padding: 10px;"  id="biryani_list">
						</div>
					</div> -->

				</div><!-- End box_style_1 -->
			</div><!-- End col-md-6 -->
            
			<div class="col-md-3">
				<div id="cart_box">
					<h3>Your order <i class="icon_cart_alt pull-right"></i></h3>
					<table class="table table_summary" id="HMcart">
					<tbody>
					<tr>
						<td>
							<a href="#0" class="remove_item"></a> <strong>Cart is empty</strong>
						</td>
						<td>
							<strong class="pull-right"></strong>
						</td>
					</tr>
					</tbody>
					</table>
					<hr id="cart_break_1" style="display: none">
					<div class="row" id="options_2" style="display: none">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
							<label><input type="radio" value="" checked name="option_2" class="icheck">Delivery</label>
						</div>
					</div> <!-- Edn options 2 -->
                    
					<hr id="cart_break_2">
					<table class="table table_summary" id="cart_total_price">
					<tbody id="cart_total_price">
					<tr>
						<td>
							 Subtotal <span class="pull-right"> </span>
						</td>
					</tr>
					<tr>
						<td>
							 Delivery fee <span class="pull-right"> </span>
						</td>
					</tr>
					<tr>
						<td class="total">
							 TOTAL <span class="pull-right"> </span>
						</td>
					</tr>
					</tbody>
					</table>
					<hr>
					<a class="btn_full" id="menu_btn">Go next</a>
				</div><!-- End cart_box -->
			</div><!-- End col-md-3 -->
            
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
    
<!-- Login modal -->   
<div class="modal fade" id="login_2" tabindex="-1" role="dialog" aria-labelledby="myLogin" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content modal-popup">
                <a href="#" class="close-link"><i class="icon_close_alt2"></i></a>
                <form class="popup-form" method="POST">
                    <div class="login_icon">
						<img src="../images/logo.png" width="190" height="23" alt="" data-retina="true">
                    </div>
                     <input type="text" class="form-control" placeholder="Mobile" name="username" onkeypress='return event.charCode >= 48 && event.charCode <= 57' maxlength="10">
                    <!-- <input type="text" class="form-control" placeholder="Mobile" name="username"> -->
                    <div class="text-right" title="Forgot password? You can login with OTP as well.">
                        <label>Login with <a href="#" onclick="getotp();" id="otpRequest">Instant OTP?</a></label>
                    </div>
                    <input type="password" class="form-control" placeholder="Password" name="password">
                   	<div id="error_statusTxt" class="weakpass" style="display:none;"></div>
                    <button type="submit" class="btn btn-submit" id="myLogin">
                    	<i class="icon-spin6 animate-spin" id="myLoginSpin" style="display:none;"></i>
                    	Submit
                    </button>
                </form>
                <label>New to HungerMeals? <a href="#0" data-toggle="modal" data-target="#register">Sign Up</a></label>
            </div>
        </div>
    </div><!-- End modal -->   
    
<!-- Register modal -->   
<div class="modal fade" id="register" tabindex="-1" role="dialog" aria-labelledby="myRegister" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content modal-popup">
				<a href="#" class="close-link"><i class="icon_close_alt2"></i></a>
				<form class="popup-form">
                	<div class="login_icon">
						<img src="../images/logo.png" width="190" height="23" alt="" data-retina="true">
                    </div>
					<input type="text" class="form-control" placeholder="Name" name="uName">
					<input type="text" class="form-control" placeholder="Mobile" name="mobile" onkeypress='return event.charCode >= 48 && event.charCode <= 57' maxlength="10">
                    <input type="email" class="form-control" placeholder="Email" name="email">
                    <input type="password" class="form-control" placeholder="Password"  id="password1" name="password1">
                    <input type="password" class="form-control" placeholder="Confirm password"  id="password2" name="password2">
                    <div id="pass-info" class="clearfix"></div>
					<div class="checkbox-holder text-left">
						<div class="checkbox">
							<input type="checkbox" value="accept_2" id="check_2" name="check_2" />
							<label for="check_2"><span>I Agree to the <strong><a href="files/terms_conditions.pdf" target="_Blank">Terms &amp; Conditions</a></strong></span></label>
						</div>
					</div>
					<button type="button" class="btn btn-submit" id="myRegister">
						<i class="icon-spin6 animate-spin" id="myRegisterSpin" style="display:none;"></i>
						Register
					</button>
				</form>
			</div>
		</div>
	</div><!-- End Register modal -->

<div class="modal fade" id="forgotP" tabindex="-1" role="dialog" aria-labelledby="myforgotPassword" aria-hidden="true">
</div><!-- End forgotPassword modal -->

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
<script src="../scripts/jscript/credentials.js"></script>
<script src="../scripts/jscript/menu.js"></script>


<script>
	window.onload = function() {
  		$("#short").css("background-size","100% 100%");
  		if($(window).width() > 768){
        	$("#sub_content").addClass("sub_content_padding");
		}	
  		 
		// After complete page loaded
  		 flying_cart();
	};
	
</script>
</body>
</html>