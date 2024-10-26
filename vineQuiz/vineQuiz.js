const maxMark = 1;
let marks = 0;
const result = document.getElementById("result");
const submitBtn = document.getElementById("sumbit");

submitBtn.onclick = function(){

    if (document.querySelector("#q1c3").checked) {
        marks++;
    }

    result.textContent = `${marks}/${maxMark}`;
}