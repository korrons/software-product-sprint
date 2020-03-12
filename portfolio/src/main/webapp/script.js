// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random fact to the page.
 */
function addRandomFact() {
  const facts =
      ['I have an older sister who is a nurse.', 'I like to play basketball.', 'My favorite color is black.', 'I like to eat pizza.'];

  // Pick a random fact.
  const fact = facts[Math.floor(Math.random() * facts.length)];

  // Add it to the page.
  const factContainer = document.getElementById('fact-container');
  factContainer.innerText = fact;
}

function fetchData() {
    console.log('Getting JSON from data')
    fetch('/data').then(response => response.json()).then((data) => {
        const element = document.getElementById('commentsList');
        data.forEach((line) => { 
        element.appendChild(createListElement(line));
        });
    });
}

function createListElement(input) {
  const listElement = document.createElement('li');
  listElement.innerText = input;
  return listElement;
}
