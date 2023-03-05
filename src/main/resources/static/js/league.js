const { romanice } = Romanice;
const standardConverter = romanice();

let divisionButton = document.getElementById("division-button");
divisionButton.textContent = standardConverter.toRoman(parseInt(divisionButton.textContent)) + ' liga';






