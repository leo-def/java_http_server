function initialize() {
	if (pontos.length == 0) {
		return;
	}
	
	var l = new google.maps.LatLng(pontos[0][1],pontos[0][2]);

	var mapOptions = {
		zoom : 15,
		center : l 
	}

	var map = new google.maps.Map(document.getElementById('map'), mapOptions);

	for (var i = 0; i < pontos.length; i++) {
		var l =  new google.maps.LatLng(pontos[i][1], pontos[i][2]);
		var marker = new google.maps.Marker({
			position : l,
			map : map,
			title : pontos[i][0]
		});
	}

}

google.maps.event.addDomListener(window, 'load', initialize);
