let addPlayerDiv = document.getElementById("player-manage-add").cloneNode(true);
let playersList = document.getElementById("playersUl");
let deletedPlayersButtons = document.querySelectorAll(".delete-player");
let saveButton = document.querySelector("#save-changes");
let playerManageDiv = document.getElementById("player-manage-fullname") == null ? null : document.getElementById("player-manage-fullname").cloneNode(true);
const saveMessage = document.createElement("div");
let messageP = document.createElement("p");
saveMessage.className = "player-manage";
saveMessage.id = "player-manage-save";
saveMessage.appendChild(messageP);

if(playerManageDiv == null){
    playerManageDiv = document.createElement("div");
    playerManageDiv.className = "player-manage";
    let player = document.createElement("li");
    player.className = "player";
    let deletePlayerButton = document.createElement("button");
    deletePlayerButton.className = "delete-player";
    deletePlayerButton.textContent = "Usuń zawodnika"
    deletePlayerButton.addEventListener("click", deletePlayer);
    playerManageDiv.appendChild(player);
    playerManageDiv.appendChild(deletePlayerButton);
}

clearPlayerManage();

const teamNameNow = document.getElementById("team").textContent;

let addedPlayers = [];
let deletedPlayers = [];

deletedPlayersButtons.forEach(button => {
    button.addEventListener('click', deletePlayer)
})


// Select the paragraph element to observe
const teamNameParagraph = document.getElementById("team");

// Create a new MutationObserver instance
const observer = new MutationObserver(function(mutations) {
    mutations.forEach(function(mutation) {
        // Check if the text content of the paragraph has changed
        if (mutation.type === 'characterData') {
            saveButton.disabled = false;
        }
    });
});

// Configure the observer to watch for changes in the text content of the paragraph
const config = { characterData: true, subtree: true };
observer.observe(teamNameParagraph, config);

function deletePlayer() {
    const fullName = this.parentNode.querySelector(".player").textContent;
    const [firstName, lastName] = fullName.trim().split(' ');
    deletedPlayers.push({
        firstName: firstName,
        lastName: lastName
    })

    const parentDiv = this.parentNode;
    parentDiv.parentNode.removeChild(parentDiv);
    saveButton.disabled = false;
    addLeaveWarning();
}

function addPlayer() {
    let firstName = document.querySelector("#first-name").value.trim();
    let lastName = document.querySelector("#last-name").value.trim();
    let actualAddPlayerDiv = document.querySelector("#player-manage-add");
    let playerManageDiv = createPlayerManageDiv();
    let newAddPlayerDiv = createAddPlayerDiv();
    playerManageDiv.querySelector(".player").textContent = firstName + " " + lastName;
    playerManageDiv.querySelector(".delete-player").addEventListener('click', deletePlayer)
    addedPlayers.push({
        firstName: firstName,
        lastName: lastName
    })

    playersList.removeChild(actualAddPlayerDiv);
    playersList.insertBefore(playerManageDiv, playersList.lastChild.previousSibling);
    playersList.insertBefore(newAddPlayerDiv, playersList.lastChild.previousSibling);
    saveButton.disabled = false;
    addLeaveWarning();
}

function addLeaveWarning(){
    window.addEventListener('beforeunload', leaveWarning);
}

function leaveWarning(event) {
    event.preventDefault();
    event.returnValue = '';
}

function saveChanges(){
    let duplicates = findDuplicates();
    removeDuplicates(duplicates);
    let teamName = document.getElementById("team").textContent;

    let isAddSuccessful = true;
    let isDeleteSuccessful = true;
    let isTeamNameChangeSuccessful = true;

    if(addedPlayers.length > 0){
        addedPlayers.forEach(player => isAddSuccessful = sendAddPlayerRequest(player));
    }

    if(deletedPlayers.length > 0){
        deletedPlayers.forEach(player => {
            isDeleteSuccessful = sendDeletePlayerRequest(player);
        });
    }

    if(teamNameNow !== teamName){
        isTeamNameChangeSuccessful = sendUpdateTeamNameRequest(teamName);
    }

    if(isAddSuccessful && isDeleteSuccessful && isTeamNameChangeSuccessful){
        addedPlayers.length = 0;
        deletedPlayers.length = 0;
        appendPlayersListWithSaveMessage("Pomyślnie zapisano zmiany", "green");
        window.removeEventListener('beforeunload', leaveWarning);
    } else{
        appendPlayersListWithSaveMessage("Nie udało się zapisać zmian", "red");
    }
    window.scrollTo(0, document.body.scrollHeight);
}

function appendPlayersListWithSaveMessage(message, color){
    messageP.textContent = message;
    messageP.style.color = color;
    playersList.appendChild(saveMessage);
}

function sendAddPlayerRequest(player){
    return fetch(`/api/player/add`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({
            firstName: player.firstName,
            lastName: player.lastName,
            teamName: teamNameNow
        }),
        credentials: 'include'
    })
        .then(response => {
            if (response.ok) {
                console.log('Player added successfully');
                return true;
            } else {
                console.error('Failed to add player');
                return false;
            }
        })
        .catch(error => {
            console.error(error);
        });
}

function sendDeletePlayerRequest(player){
    return fetch(`/api/player/delete`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({
            firstName: player.firstName,
            lastName: player.lastName,
            teamName: teamNameNow
        }),
        credentials: 'include'
    })
        .then(response => {
            if (response.ok) {
                console.log('Player deleted successfully');
                return true;
            } else {
                console.error('Failed to delete player');
                return false;
            }
        })
        .catch(error => {
            console.error(error);
        });
}

function sendUpdateTeamNameRequest(teamNameChanged){
    return fetch("/api/team/changeTeamName", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({
            teamNameNow: teamNameNow,
            teamNameChanged: teamNameChanged
        }),
        credentials: 'include'
    })
        .then(response => {
            if(response.ok) {
                console.log("Team name changed successfully");
                return true;
            }else {
                console.error("Failed to change team name")
                return false;
            }
        })
        .catch(error => {
            console.error(error)
        })
}

function clearPlayerManage() {
    let li = playerManageDiv.querySelector(".player");
    li.textContent = "";
}

function checkInputs() {
    let fullName = Array.from(document.getElementsByClassName("player-name"));
    let button = document.getElementById("add-player");
    button.disabled = fullName.some(isEmpty);
}

function createPlayerManageDiv() {
    return playerManageDiv.cloneNode(true);
}

function createAddPlayerDiv() {
    return addPlayerDiv.cloneNode(true);
}

function isEmpty(input) {
    return input.value === "";
}

function findDuplicates(){
    return addedPlayers.filter((addedPlayer) => {
        return deletedPlayers.some((deletedPlayer) => {
            return (
                addedPlayer.firstName === deletedPlayer.firstName &&
                addedPlayer.lastName === deletedPlayer.lastName
            );
        });
    });
}

function removeDuplicates(duplicates) {
    addedPlayers = addedPlayers.filter((addedPlayer) => {
        return !duplicates.some((duplicate) => {
            return (
                addedPlayer.firstName === duplicate.firstName &&
                addedPlayer.lastName === duplicate.lastName
            );
        });
    });

    deletedPlayers = deletedPlayers.filter((deletedPlayer) => {
        return !duplicates.some((duplicate) => {
            return (
                deletedPlayer.firstName === duplicate.firstName &&
                deletedPlayer.lastName === duplicate.lastName
            );
        });
    });
}