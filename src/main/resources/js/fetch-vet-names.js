document.addEventListener('DOMContentLoaded', function () {
    fetchData();

    function fetchData() {
        fetch('/vets/all')
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Network response was not ok: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                displayVetNames(data);
            })
            .catch(error => {
                console.error('Error fetching vet names:', error);
            });
    }

    function displayVetNames(vetNames) {
        console.log('Received vet names:', vetNames);

        const vetNamesDiv = document.getElementById('vet-names');
        vetNamesDiv.innerHTML = '<h3>Vet Names:</h3>';
        if (vetNames.length === 0) {
            vetNamesDiv.innerHTML += '<p>No vets found.</p>';
        } else {
            vetNames.forEach(name => {
                vetNamesDiv.innerHTML += `<p>${name}</p>`;
            });
        }
    }
});
