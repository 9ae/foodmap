var app = angular.module('tofu', ['ngMaterial']);

var map;
var currentLocation = {'lat': 0, 'lon': 0};

function initMap() {

  if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
        	currentLocation.lat = position.coords.latitude;
        	currentLocation.lon = position.coords.longitude;
        	  map = new google.maps.Map(document.getElementById('map'), {
			    center: {lat: position.coords.latitude, lng: position.coords.longitude},
			    scrollwheel: false,
			    zoom: 15
			  });
        });
    } else { 
        alert("Geolocation is not supported by this browser.");
    }
    
}

app.directive('ngEnter', function() {
        return function(scope, element, attrs) {
            element.bind("keydown keypress", function(event) {
                if(event.which === 13) {
                    scope.$apply(function(){
                        scope.$eval(attrs.ngEnter, {'event': event});
                    });

                    event.preventDefault();
                }
            });
        };
    });

app.controller('SearchMapper', function($scope) {
	$scope.tag = '';
	$scope.onSearch = false;
	
	$scope.doSearch = function(){
		$scope.onSearch = true;
	};
	
	$scope.restartSearch = function(){
		$scope.tag = '';
		$scope.onSearch = false;
	};
});
