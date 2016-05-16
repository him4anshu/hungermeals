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
    <link rel="stylesheet" href="../scripts/css/jquery.datetimepicker.css">

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
            background: rgba(242, 114, 66, 0.22) url("images/ui-bg_highlight-soft_75_ffe45c_1x100.png") 50% top repeat-x;
            color: #363636;
        }

        .ui-widget-header {
            border: 1px solid #f27242;
            background: #f27242 url("images/ui-bg_gloss-wave_35_f6a828_500x100.png") 50% 50% repeat-x;
            color: #ffffff;
            font-weight: bold;
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
    <div id="container_pin">
        <div class="row">
            <div class="col-md-6">
            <div class="strip_list wow fadeIn" data-wow-delay="0.3s">
                <div class="row">
                    <div class="col-md-9 col-sm-9">
                        <div class="desc_mealType">
                            <h3>Addict Corporate Plan</h3>
                            <div class="location">
                                Desc 1
                            </div>
                            <ul>
                                <li>plan details1<i class="icon_check_alt2 ok"></i></li>
                                <li>plan details2<i class="icon_check_alt2 ok"></i></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-md-3 col-sm-3">
                        <div class="go_to">
                            <div>
                                <a href="#0" class="btn_1 mySubscribe" data-id="AdCoPlan" data-text="Addict Corporate Plan">Subscribe</a>
                            </div>
                        </div>
                    </div>
                </div><!-- End row-->
            </div><!-- End strip_list-->

            <div class="strip_list wow fadeIn" data-wow-delay="0.3s">
                <div class="row">
                    <div class="col-md-9 col-sm-9">
                        <div class="desc_mealType">
                            <h3 class="">Addict Home Plan</h3>
                            <div class="location">
                                Desc 1
                            </div>
                            <ul>
                                <li>plan details1<i class="icon_check_alt2 ok"></i></li>
                                <li>plan details2<i class="icon_check_alt2 ok"></i></li>
                                <li>Increase meal/day<i class="icon_check_alt2 ok"></i></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-md-3 col-sm-3">
                        <div class="go_to">
                            <div>
                                <a href="#0" class="btn_1 mySubscribe" data-id="AdHoPlan" data-text="Addict Home Plan">Subscribe</a>
                            </div>
                        </div>
                    </div>
                </div><!-- End row-->
            </div><!-- End strip_list-->
            </div><!-- End col-md-6 -->
            
            <div class="col-md-6">
            <div class="strip_list wow fadeIn" data-wow-delay="0.3s">
                <div class="row">
                    <div class="col-md-9 col-sm-9">
                        <div class="desc_mealType">
                            <h3>Taste Buds Corporate Plan</h3>
                            <div class="location">
                                Desc 1
                            </div>
                            <ul>
                                <li>plan details1<i class="icon_check_alt2 ok"></i></li>
                                <li>plan details2<i class="icon_check_alt2 ok"></i></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-md-3 col-sm-3">
                        <div class="go_to">
                            <div>
                                <a href="mymeal_subsc.html" class="btn_1">Subscribe</a>
                            </div>
                        </div>
                    </div>
                </div><!-- End row-->
            </div><!-- End strip_list-->

            <div class="strip_list wow fadeIn" data-wow-delay="0.3s">
                <div class="row">
                    <div class="col-md-9 col-sm-9">
                        <div class="desc_mealType">
                            <h3>Taste Buds Home Plan</h3>
                            <div class="location">
                                Desc 1
                            </div>
                            <ul>
                                <li>plan details1<i class="icon_check_alt2 ok"></i></li>
                                <li>plan details2<i class="icon_check_alt2 ok"></i></li>
                                <li>Increase meal/day<i class="icon_check_alt2 ok"></i></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-md-3 col-sm-3">
                        <div class="go_to">
                            <div>
                                <a href="mymeal_subsc.html" class="btn_1">Subscribe</a>
                            </div>
                        </div>
                    </div>
                </div><!-- End row-->
            </div><!-- End strip_list-->

            </div><!-- End col-md-6 -->
        </div><!-- End row -->

        <div class="row">
            <div class="col-md-9">
                <div class="box_style_2" id="main_menu"  style="background: rgba(255, 255, 255, 0);display:none;">
                    <h2 class="inner">Plan meal delivery</h2>
                    <div class="col-md-6">
                        <h3 class="nomargin_top">Choose delivery dates</h3>
                        <div id="sel_dates"></div>
                        <input id="alt_sel_dates" type="text" disabled hidden/><br>
                        <i id="ico_sel_dates" class="icon_close_alt2 no"></i>
                        <label id="days_rem"></label>                    
                    </div>
                    <div class="col-md-6">
                        <h3 class="nomargin_top">Choose delivery slot</h3>
                        <select class="form-control" id="deliveryDay" disabled>
                            <optgroup label="Lunch slots">
                                <option value="11">11 AM</option>
                                <option value="12">12 PM</option>
                                <option value="01">01 PM</option>
                                <option value="02">02 PM</option>
                                <option value="03">03 PM</option>
                                <option value="04">04 PM</option>
                            </optgroup>
                            <optgroup label="Dinner slots">
                                <option value="07">07 PM</option>
                                <option value="08">08 PM</option>
                                <option value="09">09 PM</option>
                                <option value="10">10 PM</option>
                            </optgroup>
                        </select>
                    </div>
                </div><!-- End box_style_1 -->
            </div><!-- End col-md-9 -->
            <div class="col-md-3">
                <div id="cart_box" style="display:none;">
                    <h3>Your order <i class="icon_cart_alt pull-right"></i></h3>
                    <table class="table table_summary" id="HMcart">
                    <tbody>
                        <!-- dynamic data -->
                    </tbody>
                    </table>                    
                    <hr id="cart_break_2">
                    <table class="table table_summary" id="cart_total_price">
                    <tbody id="cart_total_price">
                    </tbody>
                    </table>
                    <hr>
                    <a class="btn_full" id="combo_btn">Order now</a>
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

<div class="modal fade" id="hm_alert" tabindex="-1" role="dialog" aria-labelledby="myhm_alert" aria-hidden="true"></div>
<!-- End hm_alert modal -->
    
<!-- COMMON SCRIPTS -->
<script src="../scripts/js/jquery-1.11.2.min.js"></script>
<script src="../scripts/js/common_scripts_min.js"></script>
<script src="../scripts/js/functions.js"></script>
<script src="../scripts/assets/validate.js"></script>

<!-- SPECIFIC SCRIPTS -->
<script src="../scripts/js/jquery.pin.min.js"></script>

<!-- HUNGER SCRIPTS -->
<script src="../scripts/jscript/js-cookie.js"></script>
<script src="../scripts/jscript/common.js"></script>
<script src="../scripts/js/jquery.datetimepicker.full.js"></script>
<script src="../scripts/jscript/mymeal_orders.js"></script>

<!-- <script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script> -->
<script src="../scripts/jscript/jquery-ui.js"></script>
<!-- <script type="text/javascript" src="../scripts/jscript/jquery-ui-1.11.1.js"></script> -->
<script type="text/javascript" src="../scripts/jscript/jquery-ui.multidatespicker.js"></script>


</body>
</html>