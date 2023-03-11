const {romanice} = Romanice;
const standardConverter = romanice();

let saveMessage = document.createElement("p");
saveMessage.className = "save-message";
let requestSuccessMessage = "Pomyślnie zaktualizowano";
let requestErrorMessage = "Błąd aktualizacji";
let wrongDataMessage = "Wprowadzono złe dane";

let leaguesDivision = document.getElementsByClassName("league-division");
let roundHeaders = document.getElementsByClassName("round");
configureRoundHeaders();
convertLeaguesDivisionsToRoman();

let saveButtons = document.getElementsByClassName("save-button");

for (let i = 0; i < saveButtons.length; i++) {
    saveButtons[i].addEventListener('click', saveGame);
}

function saveGame(event) {
    let gameId = event.currentTarget.value;
    let parentDiv = event.currentTarget.parentElement.parentElement;
    let gameDiv = parentDiv.parentElement.parentElement;
    let teamAScore = parentDiv.querySelector(".teamAScore").value;
    let teamBScore = parentDiv.querySelector(".teamBScore").value;
    let teamASmallPoints = parentDiv.querySelector(".teamASmallPoints").value;
    let teamBSmallPoints = parentDiv.querySelector(".teamBSmallPoints").value;

    if (checkScoreValid(teamAScore) && checkScoreValid(teamBScore) && checkSmallPointsValid(teamASmallPoints) && checkSmallPointsValid(teamBSmallPoints)) {
        sendUpdateGameRequest(gameId, teamAScore, teamBScore, teamASmallPoints, teamBSmallPoints)
            .then(isUpdateSuccessful => {
                if(isUpdateSuccessful){
                    saveMessage.textContent = requestSuccessMessage;
                    saveMessage.style.color = "green";
                    gameDiv.insertBefore(saveMessage, gameDiv.firstChild);
                } else {
                    saveMessage.textContent = requestErrorMessage;
                    saveMessage.style.color = "red";
                    gameDiv.insertBefore(saveMessage, gameDiv.firstChild);
                }
            })
            .catch(error => {
                saveMessage.textContent = requestErrorMessage;
                saveMessage.style.color = "red";
                gameDiv.insertBefore(saveMessage, gameDiv.firstChild);
            })
    } else {
        saveMessage.textContent = wrongDataMessage;
        saveMessage.style.color = "red";
        gameDiv.insertBefore(saveMessage, gameDiv.firstChild);
    }
}

function sendUpdateGameRequest(gameId, teamAScore, teamBScore, teamASmallPoints, teamBSmallPoints){
    return fetch("/api/game/update", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({
            gameId: gameId,
            teamAScore: teamAScore,
            teamBScore: teamBScore,
            teamASmallPoints: teamASmallPoints,
            teamBSmallPoints: teamBSmallPoints
        }),
        credentials: 'include'
    })
        .then(response => {
            if(response.ok){
                return true;
            } else {
                return false;
            }
        })
        .catch(error => {
            return false;
        });
}

function checkScoreValid(score) {
    console.log(score)
    if (score === null || score === '') {
        return false;
    }
    const numScore = Number(score);
    return !isNaN(numScore) && numScore >= 0 && numScore <= 3;
}

function checkSmallPointsValid(smallPoints) {
    console.log(smallPoints)
    if (smallPoints === null || smallPoints === '') {
        return false;
    }
    const numSmallPoints = Number(smallPoints);
    return !isNaN(numSmallPoints) && numSmallPoints >= 0;
}

function configureRoundHeaders() {
    for (let i = 0; i < roundHeaders.length; i++) {
        let spanArrow = document.createElement("span")
        spanArrow.className = "arrow";

        roundHeaders[i].appendChild(spanArrow);
        roundHeaders[i].addEventListener('click', toggleData);
        roundHeaders[i].click();
    }
}

function convertLeaguesDivisionsToRoman() {
    for (let i = 0; i < leaguesDivision.length; i++) {
        leaguesDivision[i].textContent = standardConverter.toRoman(parseInt(leaguesDivision[i].textContent)) + " liga";
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