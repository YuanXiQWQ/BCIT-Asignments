// Get header.html
document.addEventListener("DOMContentLoaded", () => {
    fetch("/get-header")
        .then(response => response.text())
        .then(html => {
            document.getElementById("headerContainer").innerHTML = html;
        })
        .catch(error => console.error("Error loading the header:", error));
});

// Get navbar.html
document.addEventListener("DOMContentLoaded", () => {
    fetch("/get-navbar")
        .then(response => response.text())
        .then(html => {
            document.getElementById("navbarContainer").innerHTML = html;
        })
        .catch(error => console.error("Error loading the navbar:", error));
});

// Get footer.html
document.addEventListener("DOMContentLoaded", () => {
    fetch("/get-footer")
        .then(response => response.text())
        .then(html => {
            document.getElementById("footerContainer").innerHTML = html;
        })
        .catch(error => console.error("Error loading the footer:", error));
});

// Change language
document.addEventListener("DOMContentLoaded", function () {
    // Get the available language list.
    fetch("/languages")
        .then(response => response.json())
        .then(languages => {
            const select = document.getElementById("header-btns-language");
            languages.forEach(lang => {
                const option = new Option(lang.name, lang.code);
                select.add(option);
            });
        })
        .catch(error => console.error("Error loading languages:", error));
});

let currentLanguage = "en-UK";

function changeLanguage(language) {
    currentLanguage = language;
    fetch(`/languages/${language}`)
        .then(response => response.json())
        .then(data => {
            // Use content from Strings{}
            let strings = data.Strings;
            document.querySelectorAll('[id^="text-"]').forEach(element => {
                // Get key
                let key = element.id;
                if (strings[key]) {
                    element.textContent = strings[key];
                } else {
                    console.error(`Key not found: ${key}`);
                }
            });
        })
        .catch(error => console.error("Error loading the language file:", error));
}

document.addEventListener("DOMContentLoaded", () => {
    changeLanguage(currentLanguage);
});

document.addEventListener("change", Event => {
    if (Event.target.id === "header-btns-language") {
        currentLanguage = Event.target.value;
        changeLanguage(currentLanguage);
    }
});

/*construction.html
* */

// Fetch and display the Construction list
function fetchAndDisplayConstructions() {
    fetch('/get-constructionList-data')
        .then(response => response.json())
        .then(data => {
            const listContainer = document.getElementById('constructionListContainer');
            listContainer.innerHTML = '';
            data.buildings.forEach(building => {
                const buildingHTML = `
                    <div class="construction-item">
                        <h2>${building.Name}</h2>
                        <p>Status: ${building.Status}</p>
                        <p>Registration Number: ${building.RegistrationNumber}</p>
                        <p>Registration Event: ${building.RegistrationEvent}</p>
                        <p>Location: ${building.Location}</p>
                    </div>
                `;
                listContainer.innerHTML += buildingHTML;
            });
        })
        .catch(error => console.error('Error loading construction data:', error));
}

document.addEventListener('DOMContentLoaded', () => {
    fetchAndDisplayConstructions();
});

document.addEventListener('DOMContentLoaded', () => {
    fetchAndDisplayConstructions();
});

// Add user input
document.getElementById('addConstructionForm').addEventListener('submit', e => {
    e.preventDefault();

    // Collect form data
    const formData = {
        Name: document.getElementById('name').value,
        Status: document.getElementById('status').value,
        RegistrationNumber: document.getElementById('registrationNumber').value,
        RegistrationEvent: document.getElementById('registrationEvent').value,
        Location: document.getElementById('location').value,
    };

    // Post data
    fetch('/add-construction', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
    })
        .then(response => response.text())
        .then(responseText => {
            console.log(responseText);
            fetchAndDisplayConstructions();
        })
        .catch(error => console.error('Error adding construction:', error));
});

function loadAndInsertConstructionList() {
    fetch('/get-constructionList')
        .then(response => response.text())
        .then(html => {
            document.getElementById('constructionListContainer').innerHTML = html;
        })
        .catch(error => console.error('Error loading construction list:', error));
}

document.addEventListener('DOMContentLoaded', () => {
    loadAndInsertConstructionList();
});