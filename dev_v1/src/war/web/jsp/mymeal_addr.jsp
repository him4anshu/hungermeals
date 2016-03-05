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
<section class="parallax-window" id="short" data-parallax="scroll" data-image-src="pics/addr.jpg" data-natural-width="1400" data-natural-height="350">
    <div id="subheader">
        <div id="sub_content">
         <h1>Place your order</h1>
            <div class="bs-wizard">
                <div class="col-xs-4 bs-wizard-step complete">
                  <div class="text-center bs-wizard-stepnum"><strong>1.</strong> Your order</div>
                  <div class="progress"><div class="progress-bar"></div></div>
                  <a href="menu.html" class="bs-wizard-dot"></a>
                </div>
                               
                <div class="col-xs-4 bs-wizard-step active">
                  <div class="text-center bs-wizard-stepnum"><strong>2.</strong> Details!</div>
                  <div class="progress"><div class="progress-bar"></div></div>
                  <a href="#" class="bs-wizard-dot"></a>
                </div>  

                <div class="col-xs-4 bs-wizard-step disabled">
                  <div class="text-center bs-wizard-stepnum"><strong>3.</strong> Finish!</div>
                  <div class="progress"><div class="progress-bar"></div></div>
                  <a href="#0" class="bs-wizard-dot"></a>
                </div>  
        </div><!-- End bs-wizard --> 
        </div><!-- End sub_content -->
    </div><!-- End subheader -->
</section><!-- End section -->
<!-- End SubHeader ============================================ -->

    <div id="position">
        <div class="container">
            <ul>
                <li><a href="index.html">Home</a></li>
                <li><a href="addon.html">Add-on</a></li>
                <li>Address</li>
            </ul>
        </div>
    </div><!-- Position -->

<!-- Content ================================================== -->
<div class="container margin_80_35">
    <div id="container_pin">
        <div class="row">
            <div class="col-md-3">
            
                <div class="box_style_2 hidden-xs info">
                    <!-- <h4 class="nomargin_top">Delivery time <i class="icon_clock_alt pull-right"></i></h4> -->
                    <p>
                        It takes more than good flavour to make a healthy meal. We know that body needs fresh green vegetables along with some meat from time to time. At HungerMeals we try to provide meals that has balance of nutrient and calorie.
                    </p>
                    <hr>
                    <!-- <h4>Secure payment <i class="icon_creditcard pull-right"></i></h4> -->
                    <p>
                        Apart from bringing traditional north Indian and south Indian dishes to your doorsteps, we at HungerMeals also believes in innovation with Indian touch. We will be introducing HungerMeals special dishes from time to time. Do not miss!
                    </p>
                </div><!-- End box_style_1 -->
                
                <div class="box_style_2 hidden-xs" id="help">
                    <i class="icon_lifesaver"></i>
                    <h4>Need <span>Help?</span></h4>
                    <a href="tel://9986122222" class="phone">9986122222 </a>
                    <small>Monday to Friday 11.00am - 11.00pm</small>
                </div>
                
            </div><!-- End col-md-3 -->
            
            <div class="col-md-6">

                <div class="box_style_2" id="order_process">
                    <h2 class="inner">Your order locations</h2>
                    
<!--                    <div class="form-group" id="address-group">
                        <label id="addr1radioLabel">
                            <input id="addr1radio" type="radio" value="1" checked name="addr_option" class="icheck" data-addr-id="0"><span>Address 1:</span>
                        </label>
                        <ul id="addr1" class="address_ul"></ul>

                        <label id="addr2radioLabel">
                            <input id="addr2radio" type="radio" value="2" name="addr_option" class="icheck" data-addr-id="0"><span>Address 2:</span>
                        </label>
                        <ul  id="addr2" class="address_ul"></ul>
                    </div> -->
                    <div class="form-group" id="address-group">
                        
                    </div>
                    <div>
                        <a href="#0" class="btn_full_outline" id="add_new_addr"><i class="icon-plus-circled"></i>Add New</a>
                    </div>
                    <hr>
                    <div class="new_addr_group">
                        <div class="form-group">
                            <label>Name</label>
                            <input type="text" class="form-control" id="name_order" name="HM_name" placeholder="Full name">
                        </div>
                        <div class="form-group">
                            <label>Locality:</label>
<!--                            <input type="text" id="address_order" name="HM_address1" class="form-control" placeholder=" Your address"> -->  
                            <div class="styled-select">
                                <select class="form-control" name="HM_address1" id="address_order">
                                    <option selected="selected">Select locality</option>    
                                  <optgroup label="HSR Layout" data-pcode="560102">
                                    <option value="Sector 1">Sector 1</option>
                                    <option value="Sector 2">Sector 2</option>
                                    <option value="Sector 3">Sector 3</option>
                                    <option value="Sector 4">Sector 4</option>
                                    <option value="Sector 5">Sector 5</option>
                                    <option value="Sector 6">Sector 6</option>
                                    <option value="Sector 7">Sector 7</option>
                                    <option value="Haralur">Haralur</option>
                                    <option value="Royal Placid Layout,PWD Quarters,Sector 1">Royal Placid Layout, PWD Quarters, Sector 1</option>
                                    <option value="Somasundarapalaya">Somasundarapalaya</option>
                                    <option value="Reliable Tranquil Layout">Reliable Tranquil Layout</option>
                                    <option value="Rajiv Gandhi Nagar,HSR Layout">Rajiv Gandhi Nagar,HSR Layout</option>
                                    <option value="Parangi Palaya, Sector 2">Parangi Palaya, Sector 2</option>
                                    <option value="Silk Board Bus Stop,NH7,Muneswara Nagar,Sector 6">Silk Board Bus Stop, NH7, Muneswara Nagar, Sector 6</option>
                                    <option value="Ambalipura">Ambalipura</option>
                                  </optgroup>

                                  <optgroup label="BTM Layout" data-pcode="560029">
                                    <option value="Stage 1">Stage 1</option>
                                    <option value="Stage 2">Stage 2</option>
                                    <option value="Silk Board Flyover,Madiwala,Stage 2">Silk Board Flyover, Madiwala, Stage 2</option>
                                  </optgroup>

                                  <optgroup label="Madiwala" data-pcode="560068">
                                    <option value="Stage 1">Madiwala, Stage 1</option>
                                    <option value="Madiwala Ayyappa Temple Bus Stop, Hosur Rd">Madiwala Ayyappa Temple Bus Stop, Hosur Rd</option>
                                    <option value="Madiwala, Hosur Rd">Madiwala, Hosur Rd</option>
                                    <option value="Maruthi Nagar">Maruthi Nagar</option>
                                    <option value="Mangammanapalaya">Mangammanapalaya</option>
                                    <option value="Bandepalaya">Bandepalaya</option>
                                    <option value="VGP Layout">VGP Layout</option>
                                    <option value="Singasandra">Singasandra</option>
                                  </optgroup>

                                  <optgroup label="Koramangala" data-pcode="560034">
                                    <option value="1st Block">1st Block</option>
                                    <option value="2nd Block">2nd Block</option>
                                    <option value="3rd Block">3rd Block</option>
                                    <option value="4th Block">4th Block</option>
                                    <option value="5th Block">5th Block</option>
                                    <option value="6th Block">6th Block</option>
                                    <option value="7th Block">7th Block</option>
                                    <option value="8th Block">8th Block</option>
                                    <option value="Silk Board">Silk Board</option>
                                  </optgroup>

                                  <optgroup label="Sadduguntepalaya" data-pcode="560060">
                                    <option value="Sadduguntepalaya">Sadduguntepalaya</option>
                                    <option value="Indian Oil Petrol Bunk, Bannerghatta Road, Bhavani Nagar">Indian Oil Petrol Bunk, Bannerghatta Road, Bhavani Nagar</option>
                                    <option value="South Indian Bank, Sadduguntepalya, Tavarekere">South Indian Bank, Sadduguntepalya, Tavarekere</option>
                                  </optgroup>

                                  <optgroup label="Ejipura" data-pcode="560047">
                                    <option value="Ejipura">Ejipura</option>
                                    <option value="Ejipura Bus Stand, Ejipura Main Rd">Ejipura Bus Stand, Ejipura Main Rd</option>
                                    <option value="Sreenivagilu, Chandra Reddy Layout, S T Bed Layout">Sreenivagilu, Chandra Reddy Layout, S T Bed Layout</option>
                                    <option value="Ejipura Signal Bus Stop, Koramangala Indiranagar Ring Rd">Ejipura Signal Bus Stop, Koramangala Indiranagar Ring Rd</option>
                                  </optgroup>

                                  <optgroup label="Bellandur" data-pcode="560103">
                                    <option value="Bellandur">Bellandur</option>
                                    <option value="Bellandur Post Office, Bellandur Main Rd">Bellandur Post Office, Bellandur Main Rd</option>
                                    <option value="Bellandur Gate Bus Stop, Sarjapur Road">Bellandur Gate Bus Stop, Sarjapur Road</option>
                                    <option value="Bellandur Main Rd">Bellandur Main Rd</option>
                                  </optgroup>

                                  <optgroup label="Kadabeesanahalli" data-pcode="560087">
                                    <option value="Kadabeesanahalli">Kadabeesanahalli</option>
                                  </optgroup>

                                  <optgroup label="Sarjapur Road" data-pcode="560035">
                                    <option value="Sarjapur Road">Sarjapur Road</option>
                                    <option value="Valliyamma Layout">Valliyamma Layout</option>
                                    <option value="Kasavanahalli">Kasavanahalli</option>
                                    <option value="Kaikondrahalli">Kaikondrahalli</option>
                                    <option value="Doddakanneli">Doddakanneli</option>
                                  </optgroup>

                                  <optgroup label="Rajiv Gandhi Nagar" data-pcode="560091">
                                    <option value="Rajiv Gandhi Nagar">Rajiv Gandhi Nagar</option>
                                  </optgroup>

                                  <optgroup label="Muneshwara Nagar" data-pcode="560056">
                                    <option value="Muneshwara Nagar">Muneshwara Nagar</option>
                                  </optgroup>

                                  <optgroup label="HAL Layout" data-pcode="560008">
                                    <option value="HAL Layout">HAL Layout</option>
                                  </optgroup>

                                  <optgroup label="AECS Layout" data-pcode="560066">
                                    <option value="A Block">AECS Layout A Block</option>
                                  </optgroup>

                                  <optgroup label="Parappana Agrahara" data-pcode="560100">
                                    <option value="Parappana Agrahara">Parappana Agrahara</option>
                                  </optgroup>

                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Delivery Address:</label>
<!--                            <input type="text" id="address_order" name="HM_address2" class="form-control" placeholder=" Your address"> -->
                            <textarea id="delviery_order" name="HM_address2" class="form-control" placeholder="Your full delivery address" style="resize: none;max-width: 100%;max-height: 100%;"></textarea>
                        </div>
                        <div class="row">
                            <div class="col-md-6 col-sm-6">
                                <div class="form-group">
                                    <label>City</label>
                                    <input type="text" id="city_order" name="HM_city" class="form-control" placeholder="Your city">
                                </div>
                            </div>
                            <div class="col-md-6 col-sm-6">
                                <div class="form-group">
                                    <label>Pin code</label>
                                    <input type="text" id="pcode_oder" name="HM_pcode" class="form-control" placeholder=" Your pin code" onkeypress='return event.charCode >= 48 && event.charCode <= 57' maxlength="6">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 col-sm-6 err_addr"></div>
                            <div class="col-md-6 col-sm-6">
                                <div class="go_to pull-right">
                                    <div>
                                        <button class="btn_1" id="save_new_addr">
                                            <i class="icon-spin6 animate-spin" id="myAddrSave" style="display:none;"></i>
                                            Save address
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr>
                    </div> <!--End add_new_addr -->
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
                    <a class="btn_full" href="#" id="addr_next_btn">Go to checkout</a>
                    <a class="btn_full_outline" href="menu.html"><i class="icon-right"></i> Add more items</a>
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
<script src="../scripts/jscript/mymeal_addr.js"></script>

</body>
</html>