  
  // Load the SDK asynchronously
  (function(d, s, id){
     var js, fjs = d.getElementsByTagName(s)[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement(s); js.id = id;
     js.src = "//connect.facebook.net/en_US/sdk.js";
     fjs.parentNode.insertBefore(js, fjs);
   }(document, 'script', 'facebook-jssdk'));


  // This is called with the results from from FB.getLoginStatus().
  function statusChangeCallback(response) {
    // console.log('statusChangeCallback');
    // console.log(response);

    // The response object is returned with a status field that lets the
    // app know the current login status of the person.
    // Full docs on the response object can be found in the documentation
    // for FB.getLoginStatus().
    if (response.status === 'connected') {
      // Logged into your app and Facebook.
	    FB.api('/me', { locale: 'en_US', fields: 'name, email' }, function(response) {
	      // console.log(response);
          var user_details = {};
          user_details.user = {
            uName: response.name,
            lastName: null,
            mobile: null,
            email: response.email,
            password1: null,
            userType: "FB"
          };
          call_register(user_details);
       // alert(JSON.stringify(user_details));


	    });
    } else if (response.status === 'not_authorized') {
      // The person is logged into Facebook, but not your app.
      alert("Please provide access to HungerMeals app to continue.");
    } else {
      // The person is not logged into Facebook, so we're not sure if
      // they are logged into this app or not.
     // console.log("Please login to continue.");
    }
  }

  // This function is called when someone finishes with the Login
  // Button.  See the onlogin handler attached to it in the sample
  // code below.
  function checkLoginState() {
    FB.getLoginStatus(function(response) {
      statusChangeCallback(response);
    });
  }

//appId      : '516246331890701',   //www.hungermeals.com
  window.fbAsyncInit = function() {
    FB.init({
      	appId      : '200125207004151',
        cookie     : true,  // enable cookies to allow the server to access the session
      	xfbml      : true,
      	version    : 'v2.5',
        oauth:true
    });

  // Now that we've initialized the JavaScript SDK, we call 
  // FB.getLoginStatus().  This function gets the state of the
  // person visiting this page and can return one of three states to
  // the callback you provide.  They can be:
  //
  // 1. Logged into your app ('connected')
  // 2. Logged into Facebook, but not your app ('not_authorized')
  // 3. Not logged into Facebook and can't tell if they are logged into
  //    your app or not.
  //
  // These three cases are handled in the callback function.

  FB.getLoginStatus(function(response) {
    statusChangeCallback(response);
  });

  };



/*--------------------google api call----------------------------*/
/*
  function onSignIn(googleUser) {
    // Useful data for your client-side scripts:
    var profile = googleUser.getBasicProfile();
    // console.log("ID: " + profile.getId()); // Don't send this directly to your server!
    // console.log("Name: " + profile.getName());
    // console.log("Image URL: " + profile.getImageUrl());
    console.log("Email: " + profile.getEmail());

    // The ID token you need to pass to your backend:
    var id_token = googleUser.getAuthResponse().id_token;
    // console.log("ID Token: " + id_token);
  };
  */

function onSuccess(googleUser) {
     var user_details = {};
          user_details.user = {
            uName: googleUser.getBasicProfile().getName(),
            lastName: null,
            mobile: null,
            email: googleUser.getBasicProfile().getEmail(),
            password1: null,
            userType: "GM"
          };
          call_register(user_details);
}
function onFailure(error) {
    console.log(error);
}
function renderButton() {
	gapi.signin2.render('my-signin2', {
		'scope': 'https://www.googleapis.com/auth/plus.login',
		'width': 170,
		'border-radius': 4,
		'longtitle': true,
		'theme': 'dark',
    'onsuccess': onSuccess
	});

/*	gapi.signin.render("mySignIn", { 
	  	'clientid': '177440405734-1qj73nekdna54hv9a8riig0nt1ee1jqo.apps.googleusercontent.com', 
	  	'cookiepolicy': 'single_host_origin', 
	  	'requestvisibleactions': 'http://schemas.google.com/AddActivity',
	  	'scope': 'https://www.googleapis.com/auth/plus.login',
		'onsuccess': onSuccess
	});*/
}