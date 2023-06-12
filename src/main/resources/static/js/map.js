let map;
let marker;

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 15,
        streetViewControl: false, // Oculta el control de la persona
        mapTypeControlOptions: {
            mapTypeIds: [] // Oculta los botones de alternancia entre mapas
        },
        zoomControlOptions: {
            style: google.maps.ZoomControlStyle.SMALL // Tamaño más pequeño de los botones de zoom
        },
        fullscreenControl: false, // Oculta el botón de pantalla completa
        size: new google.maps.Size(800, 600)
    });

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            const userLatLng = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };

            map.setCenter(userLatLng);
            addMarker(userLatLng);
        }, function () {
            alert('No se pudo obtener la ubicación actual');
        });
    } else {
        alert('Tu navegador no admite la geolocalización');
    }

    const input = document.getElementById('direccion');

    const autocomplete = new google.maps.places.Autocomplete(input);

    autocomplete.addListener('place_changed', () => {
        const place = autocomplete.getPlace();
        if (!place.geometry) {
            console.log('No se pudo encontrar la ubicación seleccionada');
            return;
        }
        // Centra el mapa en la ubicación seleccionada
        map.setCenter(place.geometry.location);
        addMarker(place.geometry.location);

    });

    // Obtén el valor de la dirección del formulario
    const direccion = input.value;

    // Geocodifica la dirección para obtener las coordenadas
    const geocoder = new google.maps.Geocoder();
    geocoder.geocode({ address: direccion }, function(results, status) {
        if (status === google.maps.GeocoderStatus.OK) {
            // Obtiene las coordenadas de la primera coincidencia
            const latitud = results[0].geometry.location.lat();
            const longitud = results[0].geometry.location.lng();

            // Crea un objeto LatLng con las coordenadas
            const latLng = new google.maps.LatLng(latitud, longitud);

            // Centra el mapa en las coordenadas y agrega un marcador
            map.setCenter(latLng);
            addMarker(latLng);
        } else {
            // Maneja el caso en el que la geocodificación falle
            console.error('Error de geocodificación: ' + status);
        }
    });
}

function addMarker(location) {
    if (marker) {
        marker.setMap(null);
    }

    marker = new google.maps.Marker({
        position: location,
        map: map
    });
}

function saveLocation() {
    var form = document.getElementById('myForm');
    var address = document.getElementById('direccion').value;
    //var media = document.getElementById('file').files[0]; // Obtener el archivo de imagen

    var formData = new FormData(form);
    formData.append('direccion', address); // Agregar la dirección al FormData
    //formData.append('file', media); // Agregar la imagen al FormData
    fetch('/evento/form', {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(result => {
            // Manejar la respuesta del backend si es necesario
            console.log(result);
        })
        .catch(error => {
            // Manejar el error si ocurre
            console.error('Error:', error);
        });
}


document.getElementById('direccion').addEventListener('keydown', function(event) {
    if (event.key === 'Enter') {
        event.preventDefault();
        event.stopPropagation();
    }
});

/*document.getElementById('myForm').addEventListener('submit', function(event) {
    event.preventDefault();
    saveLocation();
});*/



