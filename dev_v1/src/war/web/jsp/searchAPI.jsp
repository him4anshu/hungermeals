<html>
<head>
	<script src="http://maps.google.com/maps/api/js?libraries=places&region=uk&language=en"></script>
    <style type="text/css">
    .pac-container{
        background-color:#f5f5f5;
        position:absolute!important;
        z-index:1000;
        border-radius:2px;
        border-top:1px solid #d9d9d9;
        font-family:'Quicksand', sans-serif;
        box-shadow:0 2px 6px rgba(0,0,0,0.3);
        -moz-box-sizing:border-box;
        -webkit-box-sizing:border-box;
        box-sizing:border-box;
        overflow:hidden
    }

    .pac-logo:after{
        content:"";
        padding:1px 1px 1px 0;
        height:16px;
        text-align:right;
        display:block;
        background-image:url(https://maps.gstatic.com/mapfiles/api-3/images/powered-by-google-on-white3.png);
        background-position:right;
        background-repeat:no-repeat;
        background-size:120px 14px
    }

    .hdpi.pac-logo:after{
        background-image:url(https://maps.gstatic.com/mapfiles/api-3/images/powered-by-google-on-white3_hdpi.png)
    }

    .pac-item{
        cursor:default;
        padding:5px 14px;
        text-overflow:ellipsis;
        overflow:hidden;
        white-space:nowrap;
        line-height:30px;
        text-align:left;
        border-top:1px solid #e6e6e6;
        font-size:13px;
        color:#999
    }

    .pac-item:hover{
        background-color:#EFEFEF;
        cursor:pointer;
        font-weight:700
    }

    .pac-item-selected,.pac-item-selected:hover{
        background-color:#ebf2fe
    }

    .pac-matched{
        font-weight:700
    }

    .pac-item-query{
        font-size:13px;
        padding-right:3px;
        color:#000
    }

    .pac-icon{
        width:15px;
        height:20px;
        margin-right:7px;
        margin-top:6px;
        display:inline-block;
        vertical-align:top;
        background-image:url(https://maps.gstatic.com/mapfiles/api-3/images/autocomplete-icons.png);
        background-size:34px
    }

    .hdpi .pac-icon{background-image:url(https://maps.gstatic.com/mapfiles/api-3/images/autocomplete-icons_hdpi.png)}

    .pac-icon-search{background-position:-1px -1px}

    .pac-item-selected .pac-icon-search{background-position:-18px -1px}

    .pac-icon-marker{background-position:-1px -161px}

    .pac-item-selected .pac-icon-marker{background-position:-18px -161px}

    .pac-placeholder{color:gray}
    </style>
</head>
<body>

Address:
<input id="searchTextField" type="text" size="50" style="text-align: left;width:357px;direction: ltr;">
<br>
    <div id="map_canvas" style="display:none;"></div>


<p id="demo"></p>
<button class="ss" id="ss" onclick="getLocation();">My Location</button>
<p id="demo1"></p>
</body>
<script src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript">
    var addr_list = {
  "address": [
    {
      "locality_1": "Sector 1",
      "area_1": "HSR Layout",
      "area_2": "HSR Layout Sector 1",
      "city": "Bangalore",
      "pCode": 560102
    },
    {
      "locality_1": "Sector 2",
      "area_1": "HSR Layout",
      "area_2": "HSR Layout Sector 2",
      "city": "Bangalore",
      "pCode": 560102
    },
    {
      "locality_1": "Sector 3",
      "area_1": "HSR Layout",
      "area_2": "HSR Layout Sector 3",
      "city": "Bangalore",
      "pCode": 560102
    },
    {
      "locality_1": "Sector 4",
      "area_1": "HSR Layout",
      "area_2": "HSR Layout Sector 4",
      "city": "Bangalore",
      "pCode": 560102
    },
    {
      "locality_1": "Sector 5",
      "area_1": "HSR Layout",
      "area_2": "HSR Layout Sector 5",
      "city": "Bangalore",
      "pCode": 560102
    },
    {
      "locality_1": "Sector 6",
      "area_1": "HSR Layout",
      "area_2": "HSR Layout Sector 6",
      "city": "Bangalore",
      "pCode": 560102
    },
    {
      "locality_1": "Sector 7",
      "area_1": "HSR Layout",
      "area_2": "HSR Layout Sector 7",
      "city": "Bangalore",
      "pCode": 560102
    },
    {
      "locality_1": "Haralur",
      "area_1": "HSR Layout",
      "area_2": "HSR Layout Haralur",
      "city": "Bangalore",
      "pCode": 560102
    },
    {
      "locality_1": "Royal Placid Layout,PWD Quarters,Sector 1",
      "area_1": "HSR Layout",
      "area_2": "HSR Layout Royal Placid Layout,PWD Quarters,Sector 1",
      "city": "Bangalore",
      "pCode": 560102
    },
    {
      "locality_1": "Somasundarapalaya",
      "area_1": "HSR Layout",
      "area_2": "HSR Layout Haralur",
      "city": "Bangalore",
      "pCode": 560102
    },
    {
      "locality_1": "Reliable Tranquil Layout",
      "area_1": "HSR Layout",
      "area_2": "HSR Layout Haralur",
      "city": "Bangalore",
      "pCode": 560102
    },
    {
      "locality_1": "Rajiv Gandhi Nagar,HSR Layout",
      "area_1": "HSR Layout",
      "area_2": "HSR Layout Rajiv Gandhi Nagar,HSR Layout",
      "city": "Bangalore",
      "pCode": 560102
    },
    {
      "locality_1": "Parangi Palaya, Sector 2",
      "area_1": "HSR Layout",
      "area_2": "HSR Layout Parangi Palaya, Sector 2",
      "city": "Bangalore",
      "pCode": 560102
    },
    {
      "locality_1": "Silk Board Bus Stop,NH7,Muneswara Nagar,Sector 6",
      "area_1": "HSR Layout",
      "area_2": "HSR Layout Silk Board Bus Stop,NH7,Muneswara Nagar,Sector 6",
      "city": "Bangalore",
      "pCode": 560102
    },
    {
      "locality_1": "Ambalipura",
      "area_1": "HSR Layout",
      "area_2": "HSR Layout Ambalipura",
      "city": "Bangalore",
      "pCode": 560102
    },
    {
      "locality_1": "Stage 1",
      "area_1": "BTM Layout",
      "area_2": "BTM Layout Stage 1",
      "city": "Bangalore",
      "pCode": 560029
    },
    {
      "locality_1": "Stage 2",
      "area_1": "BTM Layout",
      "area_2": "BTM Layout Stage 2",
      "city": "Bangalore",
      "pCode": 560029
    },
    {
      "locality_1": "Silk Board Flyover,Madiwala,Stage 2",
      "area_1": "BTM Layout",
      "area_2": "BTM Layout Silk Board Flyover,Madiwala,Stage 2",
      "city": "Bangalore",
      "pCode": 560029
    },
    {
      "locality_1": "Stage 1",
      "area_1": "Madiwala",
      "area_2": "Madiwala Stage 1",
      "city": "Bangalore",
      "pCode": 560068
    },
    {
      "locality_1": "Madiwala Ayyappa Temple Bus Stop, Hosur Rd",
      "area_1": "Madiwala",
      "area_2": "Madiwala Madiwala Ayyappa Temple Bus Stop, Hosur Rd",
      "city": "Bangalore",
      "pCode": 560068
    },
    {
      "locality_1": "Madiwala, Hosur Rd",
      "area_1": "Madiwala",
      "area_2": "Madiwala Madiwala, Hosur Rd",
      "city": "Bangalore",
      "pCode": 560068
    },
    {
      "locality_1": "Maruthi Nagar",
      "area_1": "Madiwala",
      "area_2": "Madiwala Maruthi Nagar",
      "city": "Bangalore",
      "pCode": 560068
    },
    {
      "locality_1": "Mangammanapalaya",
      "area_1": "Madiwala",
      "area_2": "Madiwala Mangammanapalaya",
      "city": "Bangalore",
      "pCode": 560068
    },
    {
      "locality_1": "Ambalipura",
      "area_1": "Madiwala",
      "area_2": "Madiwala",
      "city": "Bangalore",
      "pCode": 560068
    },
    {
      "locality_1": "Bandepalaya",
      "area_1": "Madiwala",
      "area_2": "Madiwala Bandepalaya",
      "city": "Bangalore",
      "pCode": 560068
    },
    {
      "locality_1": "VGP Layout",
      "area_1": "Madiwala",
      "area_2": "Madiwala VGP Layout",
      "city": "Bangalore",
      "pCode": 560068
    },
    {
      "locality_1": "Singasandra",
      "area_1": "Madiwala",
      "area_2": "Madiwala Singasandra",
      "city": "Bangalore",
      "pCode": 560068
    },
    {
      "locality_1": "1st Block",
      "area_1": "Koramangala",
      "area_2": "Koramangala 1st Block",
      "city": "Bangalore",
      "pCode": 560034
    },
    {
      "locality_1": "2nd Block",
      "area_1": "Koramangala",
      "area_2": "Koramangala 2nd Block",
      "city": "Bangalore",
      "pCode": 560034
    },
    {
      "locality_1": "3rd Block",
      "area_1": "Koramangala",
      "area_2": "Koramangala 3rd Block",
      "city": "Bangalore",
      "pCode": 560034
    },
    {
      "locality_1": "4th Block",
      "area_1": "Koramangala",
      "area_2": "Koramangala 4th Block",
      "city": "Bangalore",
      "pCode": 560034
    },
    {
      "locality_1": "5th Block",
      "area_1": "Koramangala",
      "area_2": "Koramangala 5th Block",
      "city": "Bangalore",
      "pCode": 560034
    },
    {
      "locality_1": "6th Block",
      "area_1": "Koramangala",
      "area_2": "Koramangala 6th Block",
      "city": "Bangalore",
      "pCode": 560034
    },
    {
      "locality_1": "7th Block",
      "area_1": "Koramangala",
      "area_2": "Koramangala 7th Block",
      "city": "Bangalore",
      "pCode": 560034
    },
    {
      "locality_1": "8th Block",
      "area_1": "Koramangala",
      "area_2": "Koramangala 8th Block",
      "city": "Bangalore",
      "pCode": 560034
    },
    {
      "locality_1": "Silk Board",
      "area_1": "Koramangala",
      "area_2": "Koramangala Silk Board",
      "city": "Bangalore",
      "pCode": 560034
    },
    {
      "locality_1": "Sadduguntepalaya",
      "area_1": "Sadduguntepalaya",
      "area_2": "Sadduguntepalaya Sadduguntepalaya",
      "city": "Bangalore",
      "pCode": 560060
    },
    {
      "locality_1": "Indian Oil Petrol Bunk, Bannerghatta Road, Bhavani Nagar",
      "area_1": "Sadduguntepalaya",
      "area_2": "Sadduguntepalaya Indian Oil Petrol Bunk, Bannerghatta Road, Bhavani Nagar",
      "city": "Bangalore",
      "pCode": 560060
    },
    {
      "locality_1": "South Indian Bank, Sadduguntepalya, Tavarekere",
      "area_1": "Sadduguntepalaya",
      "area_2": "Sadduguntepalaya South Indian Bank, Sadduguntepalya, Tavarekere",
      "city": "Bangalore",
      "pCode": 560060
    },
    {
      "locality_1": "Ejipura",
      "area_1": "Ejipura",
      "area_2": "Ejipura Ejipura",
      "city": "Bangalore",
      "pCode": 560047
    },
    {
      "locality_1": "Ejipura Bus Stand, Ejipura Main Rd",
      "area_1": "Ejipura",
      "area_2": "Ejipura Ejipura Bus Stand, Ejipura Main Rd",
      "city": "Bangalore",
      "pCode": 560047
    },
    {
      "locality_1": "Sreenivagilu, Chandra Reddy Layout, S T Bed Layout",
      "area_1": "Ejipura",
      "area_2": "Ejipura Sreenivagilu, Chandra Reddy Layout, S T Bed Layout",
      "city": "Bangalore",
      "pCode": 560047
    },
    {
      "locality_1": "Bellandur",
      "area_1": "Bellandur",
      "area_2": "Bellandur Bellandur",
      "city": "Bangalore",
      "pCode": 560103
    },
    {
      "locality_1": "Bellandur Post Office, Bellandur Main Rd",
      "area_1": "Bellandur",
      "area_2": "Bellandur Bellandur Post Office, Bellandur Main Rd",
      "city": "Bangalore",
      "pCode": 560103
    },
    {
      "locality_1": "Bellandur Gate Bus Stop, Sarjapur Road",
      "area_1": "Bellandur",
      "area_2": "Bellandur Bellandur Gate Bus Stop, Sarjapur Road",
      "city": "Bangalore",
      "pCode": 560103
    },
    {
      "locality_1": "Bellandur Main Rd",
      "area_1": "Bellandur",
      "area_2": "Bellandur Bellandur Main Rd",
      "city": "Bangalore",
      "pCode": 560103
    },
    {
      "locality_1": "Kadabeesanahalli",
      "area_1": "Kadabeesanahalli",
      "area_2": "Kadabeesanahalli Kadabeesanahalli",
      "city": "Bangalore",
      "pCode": 560087
    },
    {
      "locality_1": "Sarjapur Road",
      "area_1": "Sarjapur Road",
      "area_2": "Sarjapur Road Sarjapur Road",
      "city": "Bangalore",
      "pCode": 560035
    },
    {
      "locality_1": "Valliyamma Layout",
      "area_1": "Sarjapur Road",
      "area_2": "Sarjapur Road Valliyamma Layout",
      "city": "Bangalore",
      "pCode": 560035
    },
    {
      "locality_1": "Kasavanahalli",
      "area_1": "Sarjapur Road",
      "area_2": "Sarjapur Road Kasavanahalli",
      "city": "Bangalore",
      "pCode": 560035
    },
    {
      "locality_1": "Doddakanneli",
      "area_1": "Sarjapur Road",
      "area_2": "Sarjapur Road Doddakanneli",
      "city": "Bangalore",
      "pCode": 560035
    },
    {
      "locality_1": "Muneshwara Nagar",
      "area_1": "Muneshwara Nagar",
      "area_2": "Muneshwara Nagar Muneshwara Nagar",
      "city": "Bangalore",
      "pCode": 560056
    },
    {
      "locality_1": "Rajiv Gandhi Nagar",
      "area_1": "Rajiv Gandhi Nagar",
      "area_2": "Rajiv Gandhi Nagar Rajiv Gandhi Nagar",
      "city": "Bangalore",
      "pCode": 560091
    },
    {
      "locality_1": "HAL Layout",
      "area_1": "HAL Layout",
      "area_2": "HAL Layout HAL Layout",
      "city": "Bangalore",
      "pCode": 560008
    },
    {
      "locality_1": "AECS Layout A Block",
      "area_1": "AECS Layout",
      "area_2": "AECS Layout AECS Layout A Block",
      "city": "Bangalore",
      "pCode": 560066
    },
    {
      "locality_1": "Parappana Agrahara",
      "area_1": "Parappana Agrahara",
      "area_2": "Parappana Agrahara Parappana Agrahara",
      "city": "Bangalore",
      "pCode": 560100
    }
  ]
};
     $(function () {        

        // addr_list = getAddrList();
        // buildSearchgAlgo();


         var lat = 0,
             lng = 0,
             latlng = new google.maps.LatLng(lat, lng),
             image = 'http://www.google.com/intl/en_us/mapfiles/ms/micons/blue-dot.png';

         //zoomControl: true,
         //zoomControlOptions: google.maps.ZoomControlStyle.LARGE,

         var mapOptions = {
             center: new google.maps.LatLng(lat, lng),
             zoom: 13,
             mapTypeId: google.maps.MapTypeId.ROADMAP,
             panControl: true,
             panControlOptions: {
                 position: google.maps.ControlPosition.TOP_RIGHT
             },
             zoomControl: true,
             zoomControlOptions: {
                 style: google.maps.ZoomControlStyle.LARGE,
                 position: google.maps.ControlPosition.TOP_left
             }
         };

         var map = new google.maps.Map(document.getElementById('map_canvas'), mapOptions);

         var input = document.getElementById('searchTextField');
         var autocomplete = new google.maps.places.Autocomplete(input, {
             types: ["geocode"]
         });

         autocomplete.bindTo('bounds', map);
         var infowindow = new google.maps.InfoWindow();

         google.maps.event.addListener(autocomplete, 'place_changed', function (event) {
             // infowindow.close();
             var place = autocomplete.getPlace();

             console.log("place.name");
             console.log(place);
             var mylat = place.geometry.location.lat();
             var mylon = place.geometry.location.lng();
             getLocality(mylat,mylon);
         });


         $("#searchTextField").focusin(function () {
             $(document).keypress(function (e) {
                 if (e.which == 13) {
                     return false;
                     infowindow.close();
                     var firstResult = $(".pac-container .pac-item:first").text();
                     var geocoder = new google.maps.Geocoder();
                     geocoder.geocode({
                         "address": firstResult
                     }, function (results, status) {
                         if (status == google.maps.GeocoderStatus.OK) {
                             var lat = results[0].geometry.location.lat(),
                                 lng = results[0].geometry.location.lng(),
                                 placeName = results[0].address_components[0].long_name,
                                 latlng = new google.maps.LatLng(lat, lng);

                             moveMarker(placeName, latlng);
                             $("input").val(firstResult);
                             console.log("firstResult");
                             console.log(firstResult);
                         }
                     });
                 }
             });
         });

         function moveMarker(placeName, latlng) {
             marker.setIcon(image);
             marker.setPosition(latlng);
             infowindow.setContent(placeName);
             //infowindow.open(map, marker);
         }

      
     });

function buildSearchgAlgo(data,key_list){
    var searchG_ele = [];

    $.each(data.results, function(i,val){
            // alert(JSON.stringify(val.address_components)));

            $.each(val.address_components, function(k, resultants){
                searchG_ele.push(resultants.long_name.toLowerCase());
            });
    });
  //  console.log(searchG_ele);
    //console.log(key_list);


    var getFlag = false;
    $.each(searchG_ele,function(ind,search_ele){
        // console.log("ele--"+JSON.stringify(search_ele));
        $.each(key_list.address,function(keyind,search_Key){
            // console.log("key--"+JSON.stringify(search_Key));
            if(search_Key.pCode == search_ele){
                console.log("ele--"+JSON.stringify(search_ele));
                console.log("key--"+JSON.stringify(search_Key));
                getFlag = true;
                return false;
            }
        });
    })
    console.log(getFlag);
    /*if(key){
        if ($.inArray(key.toLowerCase(), searchG_ele) != -1)    {
            $("#demo1").html(searchG_ele);
        }else{
            $("#demo1").html("No result found;");
        }
    }*/



}

function getAddrList(){
    $.ajax({
        url:"jscript/address.json",
        type:"GET",
        contentType: "application/json",
        success:function(data){
            console.log(data);
            return data;
        },
        error:function(XHR, textStatus, errorThrown){

        }
    });
   
}



         function getLocality(mylat,mylon){
            $.ajax({
                url:"http://maps.google.com/maps/api/geocode/json?latlng="+mylat+","+mylon,
                type:"GET",
                success:function(data){
                    console.log('res');
                    console.log(data);
                    console.log(addr_list);
                    buildSearchgAlgo(data,addr_list);
                    // $("#demo1").html(JSON.stringify(data));
                },
                error:function(XHR, textStatus, errorThrown){

                }
            });
         }


        function getLocation() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(showPosition);
            } else { 
                x.innerHTML = "Geolocation is not supported by this browser.";
            }
        }

        function showPosition(position) {
// var x = document.getElementById("demo");
        /*  var returnObj = {};
            returnObj.latitude = position.coords.latitude;
            returnObj.longitude = position.coords.longitude;

             x.innerHTML = "Latitude: " + position.coords.latitude + 
            "<br>Longitude: " + position.coords.longitude;   
            return returnObj;
        */    
            var mylat = position.coords.latitude;
            var mylon = position.coords.longitude;
            console.log(mylat+","+mylon);
            getLocality(mylat,mylon);

        }   

</script>
 
</script>
</html>