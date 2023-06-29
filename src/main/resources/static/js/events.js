/*
const eventoInput = document.getElementById('eventoInput');
const resultadosBusqueda = document.getElementById('resultadosBusqueda');

eventoInput.addEventListener('input', () => {
    const query = eventoInput.value.trim();
    if (query.length > 0) {
        buscarEventos(query);
    } else {
        resultadosBusqueda.innerHTML = '';
    }
});

function buscarEventos(query) {
    fetch(`/evento/searchEvent?search=${encodeURIComponent(query)}`)
        .then(response => response.json())
        .then(data => {
            resultadosBusqueda.innerHTML = '';

            // Mostrar los resultados en la vista
            data.forEach(evento => {
                const cardEvento = document.createElement('div');
                cardEvento.classList.add('card');
                cardEvento.innerHTML = `
                    <img class="card-img img-fluid rounded-start"
                        src="/evento/uploads/${evento.media}"
                        alt="${evento.media}" />
                    <h2 class="card-title">${evento.nombre}</h2>
                    <h5 class="card-title mb-3"><i class="bi bi-alarm"></i>
                        <strong>${evento.horaInicio}</strong>
                    </h5>
                    <h5 class="card-title mb-3"><i class="bi bi-calendar-event"></i>
                        <strong>${evento.fechaEvento}</strong>
                    </h5>
                    <button>Comprar</button>
                `;
                resultadosBusqueda.appendChild(cardEvento);
            });
        })
        .catch(error => {
            console.error('Error al buscar eventos:', error);
            resultadosBusqueda.innerHTML = 'Error al buscar eventos';
        });
}*/
