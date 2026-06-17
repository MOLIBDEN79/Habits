const anyBox = document.querySelectorAll(".box");
const resetButton = document.querySelector(".resetbutton");
const testButton = document.querySelector(".testbutton");
const threePanels = document.querySelectorAll(".panels");
const panelContainer = document.querySelector(".container");
const winScreen = document.querySelector(".win-screen");
const moveCounterBox = document.querySelectorAll(".move-counter");
const easyButton = document.querySelector(".easymode");
const normalButton = document.querySelector(".normalmode");
const hardButton = document.querySelector(".hardmode");
const mobileInstructions = document.querySelector(".mobile-instructions");
const mobileDropdown = document.querySelector(".instructions-dropdown");
var cheater = false;
var selectedBox;
var selectedPanel;
var moveCounter = 0;
var bestOutcome = 31;
var gameDifficulty = 2;


addBoxTargets();
setDifficultyNormal();


function addBoxTargets() {
    for (let i = 0; i < 6; i++)
        anyBox[i].addEventListener("click", function(evt) {
            evt.preventDefault();
            if (!selectedBox) {
                selectedBox = evt.target;
                if (selectedBox.nextElementSibling === null) {
                    anyBox[i].style.margin = "0px 0px 10px 0px";
                    evt.stopPropagation();
                    addPanelTargets();
                } else {
                    selectedBox = null;
                    addBoxTargets();
                }
            }
        });
}


function addPanelTargets() {
    for (let i = 0; i < 3; i++) {
        threePanels[i].addEventListener("click", function(evt) {
            evt.preventDefault();
            if (!selectedBox) return;
            selectedPanel = threePanels[i];
            if (
                checkSizing() === false ||
                selectedPanel === selectedBox.parentElement
            ) {
                selectedBox.style.margin = "2px 0px";
                evt.target.removeEventListener("click", evt);
                selectedBox = null;
                addBoxTargets();
                return;
            }
            threePanels[i].appendChild(selectedBox);
            moveCounter++;
            moveCounterBox[0].innerHTML = moveCounter;
            moveCounterBox[1].innerHTML = moveCounter;
            selectedBox.style.margin = "2px 0px";
            evt.target.removeEventListener("click", evt);
            selectedBox = null;
            addBoxTargets();
            winConditionMet();
        });
    }
}


function checkSizing() {
    if (selectedPanel.firstElementChild === null) {
        return;
    } else if (
        selectedBox.dataset.length > selectedPanel.lastElementChild.dataset.length
    ) {
        return false;
    }
}


resetButton.addEventListener("click", function(evt) {
    evt.preventDefault();
    if (gameDifficulty === 1) {
        setDifficultyEasy();
    }
    if (gameDifficulty === 2) {
        setDifficultyNormal();
    }
    if (gameDifficulty === 3) {
        setDifficultyHard();
    }
});


testButton.addEventListener("click", function(evt) {
    evt.preventDefault();
    cheater = true;
    while (threePanels[2].firstChild) {
        threePanels[1].appendChild(threePanels[2].firstChild);
    }
    for (let i = 0; i < 5; i++) {
        threePanels[2].appendChild(anyBox[i]);
        anyBox[i].style.margin = "2px 0px";
        moveCounterBox[0].innerHTML = moveCounter;
        moveCounterBox[1].innerHTML = moveCounter;
    }
});


easyButton.addEventListener("click", function(evt) {
    evt.preventDefault();
    setDifficultyEasy();
});

normalButton.addEventListener("click", function(evt) {
    evt.preventDefault();
    setDifficultyNormal();
});

hardButton.addEventListener("click", function(evt) {
    evt.preventDefault();
    setDifficultyHard();
});


function winConditionMet() {
    if (threePanels[2].childElementCount === 6) {
        panelContainer.style.display = "none";
        winScreen.style.display = "block";
        if (cheater === true) {
            winScreen.innerHTML =
                "Поздравляем! Ты смухлевал! Наименьшее возможное количество ходов было " +
                bestOutcome +
                ".";
        } else if (moveCounter === bestOutcome) {
            winScreen.innerHTML =
                "Поздравляем! Вы выиграли в " +
                moveCounter +
                " . Это минимально возможное количество ходов, отличная работа!";
        } else {
            winScreen.innerHTML =
                "Поздравляем! Вы выиграли в " +
                moveCounter +
                ". Наименьшее возможное количество ходов было " +
                bestOutcome +
                ".";
        }
        easyButton.style.display = "none";
        normalButton.style.display = "none";
        hardButton.style.display = "none";
        resetButton.style.display = "block";
        testButton.style.display = "none";
    }
}

function resetGame() {
    cheater = false;
    moveCounter = 0;
    resetButton.style.display = "none";
    easyButton.style.display = "block";
    normalButton.style.display = "block";
    hardButton.style.display = "block";
    testButton.style.display = "block";
    while (threePanels[0].firstChild) {
        threePanels[1].appendChild(threePanels[0].firstChild);
    }
    while (threePanels[2].firstChild) {
        threePanels[1].appendChild(threePanels[2].firstChild);
    }
    for (let i = 0; i < 6; i++) {
        threePanels[0].appendChild(anyBox[i]);
        anyBox[i].style.margin = "2px 0px";
    }
    panelContainer.style.display = "flex";
    winScreen.style.display = "none";
    moveCounter = 0;
    moveCounterBox[0].innerHTML = moveCounter;
    moveCounterBox[1].innerHTML = moveCounter;
}


mobileInstructions.addEventListener("click", function(evt) {
    evt.preventDefault();
    mobileDropdown.classList.toggle("toggle-visibility");
});


function setDifficultyEasy() {
    resetGame();
    bestOutcome = 7;
    gameDifficulty = 1;
    anyBox[0].style.display = "none";
    anyBox[1].style.display = "none";
    anyBox[2].style.display = "none";
    threePanels[2].appendChild(anyBox[0]);
    threePanels[2].appendChild(anyBox[1]);
    threePanels[2].appendChild(anyBox[2]);
}
function setDifficultyNormal() {
    resetGame();
    bestOutcome = 31;
    gameDifficulty = 2;
    anyBox[0].style.display = "none";
    anyBox[1].style.display = "block";
    anyBox[2].style.display = "block";
    threePanels[2].appendChild(anyBox[0]);
}
function setDifficultyHard() {
    resetGame();
    bestOutcome = 63;
    gameDifficulty = 3;
    if (threePanels[0].childElementCount === 5) {
        threePanels[0].appendChild(anyBox[0]);
    }
    anyBox[0].style.display = "block";
    anyBox[1].style.display = "block";
    anyBox[2].style.display = "block";
}
