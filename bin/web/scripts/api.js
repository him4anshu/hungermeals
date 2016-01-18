var app = angular.module('fidloo', ['ui.router', 'ui.bootstrap', 'ngCookies']);
app.config(function($stateProvider, $urlRouterProvider) {
    login = {
        name: 'login',
        url: '/login',
        views: {
            'HeaderView': {
                templateUrl: 'templates/header.html',
                controller: 'LoginCntrl'
            },
            'MainView': {
                templateUrl: 'templates/login.html',
                controller: 'LoginCntrl'

            }
        },
        authenticate: false
    };
    home = {
        name: 'home',
        url: '/home',
        views: {
            'HeaderView': {
                templateUrl: 'templates/homeHeader.html',
                controller: 'LoginCntrl'

            },
            'MainView': {
                templateUrl: 'templates/home.html',
                controller: 'LoginCntrl'
            }
        },
        authenticate: true
    };
    $stateProvider
        .state(login)
        .state(home);
});

app.run(function($rootScope, $state, $cookies) {
    $rootScope.$on("$stateChangeStart", function(event, toState, toParams, fromState, fromParams) {
        if ($cookies.UserInfo == undefined && toState.authenticate == true) {
            $state.go('login');
            event.preventDefault();
        } else if ($cookies.UserInfo != undefined && toState.authenticate == false) {
            $state.go('home');
            event.preventDefault();
        }
    });

});

app.controller('LoginCntrl', function($scope, $state, $modal, $cookies, authFactory) {
    $scope.login = function($scope) {
        authFactory.LogOnCheck();
    }
    $scope.loginmodal = function() {
        var loginmodalInstance = $modal.open({
            templateUrl: 'modal/login-modal.html',
            //size: "sm"

        });
        loginmodalInstance.result.then(function() {
            console.log('close');

        }, function() {
            console.log('Modal dismissed at: ' + new Date());
        });

    }
    $scope.logout = function() {

        delete $cookies["UserInfo"];
        $state.go('login');
    }

});
app.factory('authFactory', function($cookies, $state) {
    var authService = {};
    authService.LogOnCheck = function() {
        $cookies.UserInfo = "cookie found";
        $state.go('home');

    }
    return authService;

});