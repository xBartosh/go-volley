const {romanice} = Romanice;
const standardConverter = romanice();

let roundHeaders = document.getElementsByClassName("round");
configureRoundHeaders();
let leagueCheckboxes = document.getElementsByClassName('league-checkbox');
let roundCheckboxes = document.getElementsByClassName('checkbox-round-input');
addEventListenersToCheckboxes();
let leaguesDivision = document.getElementsByClassName("league-division");
convertLeaguesDivisionsToRoman();

let generateButton = document.getElementById("generate-button");
generateButton.addEventListener('click', generateProtocols);

const popup = document.getElementById("popup");

let gameIdCheckboxes = document.querySelectorAll('input[type="checkbox"].game-id');
for (let i = 0; i < gameIdCheckboxes.length; i++) {
    gameIdCheckboxes[i].addEventListener("click", checkOutside);
}

let generationMessage = document.getElementById("generation-message");


function generateProtocols() {
    let checkedCheckboxes = Array.from(document.querySelectorAll('input[type="checkbox"].game-id:checked'));
    let gameIdsFromCheckboxes = checkedCheckboxes.map(checkbox => checkbox.value);
    hideGenerationMessage();
    showLoadingPopup();
    window.location.hash = "#bottom";

    fetch("/api/generate-protocols", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({
            gameIds: gameIdsFromCheckboxes
        }),
        credentials: 'include'
    })
        .then(response => {
            if (response.ok) {
                response.blob().then(blob => {
                    hideLoadingPopup();
                    const url = window.URL.createObjectURL(blob);
                    const link = document.createElement('a');
                    link.href = url;
                    link.download = 'protokoly.pdf';
                    link.style.display = 'none';
                    document.body.appendChild(link);
                    link.click();
                    window.URL.revokeObjectURL(url);
                    generationMessage.textContent = "Pomyślnie wygenerowano protokół!";
                    generationMessage.style.color = "green";
                    showGenerationMessage();
                });
            } else {
                generationMessage.textContent = "Generacja protokołu nie powiodła się. Spróbuj ponownie";
                generationMessage.style.color = "red";
                showGenerationMessage();
            }
        })
        .catch(error => {
            console.error(error)
        })

}


function addEventListenersToCheckboxes() {
    for (let i = 0; i < roundCheckboxes.length; i++) {
        roundCheckboxes[i].addEventListener('click', checkAllInside);
    }

    for (let i = 0; i < leagueCheckboxes.length; i++) {
        leagueCheckboxes[i].addEventListener('click', checkAllInside);
    }
}

function convertLeaguesDivisionsToRoman() {
    for (let i = 0; i < leaguesDivision.length; i++) {
        leaguesDivision[i].textContent = standardConverter.toRoman(parseInt(leaguesDivision[i].textContent)) + " liga";
    }
}

function configureRoundHeaders() {
    for (let i = 0; i < roundHeaders.length; i++) {
        let spanArrow = document.createElement("span")
        spanArrow.className = "arrow";
        let checkboxInput = document.createElement("input")
        checkboxInput.type = "checkbox";
        checkboxInput.className = "checkbox-round-input";

        roundHeaders[i].appendChild(checkboxInput);
        roundHeaders[i].appendChild(spanArrow);
        roundHeaders[i].addEventListener('click', toggleData);
        roundHeaders[i].click();
    }
}

function checkOutside(event) {
    let leagueCheckbox = event.target.parentElement.parentElement.querySelector('input[type="checkbox"].league-checkbox');
    let otherCheckboxes = event.target.parentElement.parentElement.querySelectorAll('input[type="checkbox"].game-id:checked');
    let roundCheckbox = event.target.parentElement.parentElement.parentElement.parentElement.querySelector('input[type="checkbox"].checkbox-round-input');
    let allCheckboxes = Array.from(roundCheckbox.parentElement.parentElement.querySelectorAll('input[type="checkbox"]:not(.checkbox-round-input)'));
    console.log(allCheckboxes);
    console.log(roundCheckbox);
    if (event.target.checked) {
        if (otherCheckboxes.length === 3) {
            leagueCheckbox.checked = true;
        }
        if (allCheckboxes.every(function (checkbox) {
            return checkbox.checked;
        })) {
            roundCheckbox.checked = true;
        }
    } else {
        leagueCheckbox.checked = false;
        roundCheckbox.checked = false;
    }
}


function checkAllInside(event) {
    const checkboxes = event.target.parentElement.parentElement.querySelectorAll('input[type="checkbox"]');
    let roundCheckbox = event.target.parentElement.parentElement.parentElement.parentElement.querySelector('input[type="checkbox"].checkbox-round-input');
    let allCheckboxes = Array.from(roundCheckbox.parentElement.parentElement.querySelectorAll('input[type="checkbox"]:not(.checkbox-round-input)'));

    event.stopPropagation();
    // loop through each checkbox and check it
    if (event.target.checked) {
        checkboxes.forEach(checkbox => {
            checkbox.checked = true;
        });
        if (allCheckboxes.every(function (checkbox) {
            return checkbox.checked;
        })) {
            roundCheckbox.checked = true;
        }
    } else {
        checkboxes.forEach(checkbox => {
            checkbox.checked = false;
        });
        roundCheckbox.checked = false;
    }
}

function toggleData(event) {
    let leaguesData = event.currentTarget.parentElement.querySelector('.leagues-data');
    if (leaguesData.style.display === 'none') {
        leaguesData.style.display = 'flex';
    } else {
        leaguesData.style.display = 'none';
    }
}

function showLoadingPopup() {
    popup.style.display = "block";
}

function hideLoadingPopup() {
    popup.style.display = "none";
}

function showGenerationMessage() {
    generationMessage.style.display = "block";
}

function hideGenerationMessage() {
    generationMessage.style.display = "none";
}





