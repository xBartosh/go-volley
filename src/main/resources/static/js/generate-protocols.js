const { romanice } = Romanice;
const standardConverter = romanice();

let roundHeaders = document.getElementsByClassName("round");
configureRoundHeaders();
let leagueCheckboxes = document.getElementsByClassName('league-checkbox');
let roundCheckboxes = document.getElementsByClassName('checkbox-round-input');
addEventListenersToCheckboxes();
let leaguesDivision = document.getElementsByClassName("league-division");
convertLeaguesDivisionsToRoman();

let generateButton = document.getElementById("generate-button");
generateButton.addEventListener('click', generateProtocols)

function generateProtocols(){
    let checkedCheckboxes = Array.from(document.querySelectorAll('input[type="checkbox"].game-id:checked'));
    checkedCheckboxes.forEach(checkedCheckboxes => console.log(checkedCheckboxes.value))
    let gameIdsFromCheckboxes = checkedCheckboxes.map(checkbox => checkbox.value);


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
        .then(response => response.blob())
        .then(blob => {
            const url = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = url;
            link.download = 'protokoly.pdf';
            link.style.display = 'none';
            document.body.appendChild(link);
            link.click();
            window.URL.revokeObjectURL(url);
        })
        .catch(error => {
            console.error(error)
        })

}


function addEventListenersToCheckboxes(){
    for (let i = 0; i < roundCheckboxes.length; i++) {
        roundCheckboxes[i].addEventListener('click', checkAllInside);
    }

    for (let i = 0; i < leagueCheckboxes.length; i++) {
        leagueCheckboxes[i].addEventListener('click', checkAllInside);
    }
}

function convertLeaguesDivisionsToRoman(){
    for (let i = 0; i < leaguesDivision.length; i++) {
        leaguesDivision[i].textContent = standardConverter.toRoman(parseInt(leaguesDivision[i].textContent)) + " liga";
    }
}

function configureRoundHeaders(){
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

function checkAllInside(event){
    const checkboxes = event.target.parentElement.parentElement.querySelectorAll('input[type="checkbox"]');
    event.stopPropagation();
    // loop through each checkbox and check it
    if(event.target.checked){
        checkboxes.forEach(checkbox => {
            checkbox.checked = true;
        });
    }else {
        checkboxes.forEach(checkbox => {
            checkbox.checked = false;
        });
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





