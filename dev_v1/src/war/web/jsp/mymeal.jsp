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

    <div class="container margin_80_35">
        
        <div class="main_title">
            <h3 class="nomargin_top">Choose from the popular plans</h3>
            <p>
                Find the suitable plan for your subscription.
            </p>
        </div>
        
        <div class="row" id="combo_list">
            <!-- <div class="col-md-5 ">
                <div>
                    <span class="mymeal_items_name">asdasd</span>
                    <span class="mymeal_items_price">2000</span>
                </div>
                <a href="#0" class="strip_list_meal mymeal_items_div">
                    <div class="desc_meal1">
                        <div class="thumb_strip_meal no_border_mymeal  mymeal_overlay">
                            <img src="pics/3andhra-veg-meal.jpg" alt="">
                        </div>
                    </div> 
                <span class="mymeal_overlay_text">
                    Find the suitable plan for your subscription.Find the suitable plan for your subscription.Find the suitable plan for your subscription.
                    <div onclick="mymeal_sel('1')"><span class="btn_1 mymeal_overlay_btn"><i class=\"icon-plus\"></i> Add</span></div>
                </span>
                </a> 
            </div>

            <div class="col-md-2"></div>   -->

            <div class="col-md-5 ">
                <a href="#0" class="strip_list_meal mymeal_items_div">
                    <div class="desc_meal1">
                        <div class="thumb_strip_meal no_border_mymeal  mymeal_overlay">
                            <img src="pics/3andhra-chicken-meal.jpg" alt="">
                        </div>
                    </div><!-- End desc-->
                <span class="mymeal_overlay_text">
                    Find the suitable plan for your subscription.Find the suitable plan for your subscription.Find the suitable plan for your subscription.
                    <div onclick="mymeal_sel('2')"><span class="btn_1 mymeal_overlay_btn"><i class="icon-plus"></i> Add</span></div>
                </span>
                </a><!-- End strip_list-->
            </div><!-- End col-md-5-->
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

    
<!-- Login modal -->   
<div class="modal fade" id="login_2" tabindex="-1" role="dialog" aria-labelledby="myLogin" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content modal-popup">
                <a href="#" class="close-link"><i class="icon_close_alt2"></i></a>
                <form class="popup-form" method="POST">
                    <div class="login_icon"><i class="icon_lock_alt"></i></div>
                     <input type="text" class="form-control form-white" placeholder="Mobile" name="username" onkeypress='return event.charCode >= 48 && event.charCode <= 57' maxlength="10">
                    <!-- <input type="text" class="form-control form-white" placeholder="Mobile" name="username"> -->
                    <div class="text-right" title="Forgot password? You can login with OTP as well.">
                        Login with <a href="#" onclick="getotp();" id="otpRequest">Instant OTP?</a>
                    </div>
                    <input type="password" class="form-control form-white" placeholder="Password" name="password">
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
                    <div class="login_icon"><i class="icon_lock_alt"></i></div>
                    <input type="text" class="form-control form-white" placeholder="Name" name="uName">
                    <input type="text" class="form-control form-white" placeholder="Mobile" name="mobile" onkeypress='return event.charCode >= 48 && event.charCode <= 57' maxlength="10">
                    <input type="email" class="form-control form-white" placeholder="Email" name="email">
                    <input type="password" class="form-control form-white" placeholder="Password"  id="password1" name="password1">
                    <input type="password" class="form-control form-white" placeholder="Confirm password"  id="password2" name="password2">
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
    
<!-- COMMON SCRIPTS -->
<script src="../scripts/js/jquery-1.11.2.min.js"></script>
<script src="../scripts/js/common_scripts_min.js"></script>
<script src="../scripts/js/functions.js"></script>
<script src="../scripts/assets/validate.js"></script>

<!-- SPECIFIC SCRIPTS -->
<script  src="../scripts/js/cat_nav_mobile.js"></script>
<script>$('#cat_nav').mobileMenu();</script>
<script src="../scripts/js/jquery.pin.min.js"></script>
<script>
	$("#cart_box").pin({padding: {top: 80, bottom: 25},minWidth: 1100, containerSelector: "#container_pin"})
</script>

<!-- HUNGER SCRIPTS -->
<script src="../scripts/jscript/js-cookie.js"></script>
<script src="../scripts/jscript/common.js"></script>
<script src="../scripts/jscript/credentials.js"></script>
<script src="../scripts/jscript/mymeal.js"></script>

</body>
</html>