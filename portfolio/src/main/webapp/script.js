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
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

function darkMode() {
  var element = document.body;
  element.classList.toggle("light-mode");
  const modeToggle = document.getElementById('modeToggle');
  modeToggle.innerText = modeToggle.innerText === "Dark" ? "Light" : "Dark";
}

async function getDetails() {
  const response = await fetch('/data');
  const detail = await response.text();
  console.log(response);
  console.log(detail);
  document.getElementById('details-json').innerText = detail;
}

async function getList() {
  const response = await fetch('/list');
  let detail = await response.text();
  console.log(response);
  console.log(detail);
  document.getElementById('get-list').innerHTML = detail;
}

function genMap()
{
  if( navigator.geolocation ) {
    navigator.geolocation.getCurrentPosition( success, fail );
  }
  else {
    alert("Sorry, your browser does not support geolocation services.");
  }
}

function success(position)
{
  const lng = position.coords.longitude;
  const lat = position.coords.latitude;
  document.getElementById('lat').innerHTML = lat.toString(10);
  document.getElementById('lng').innerHTML = lng.toString(10);
  const map = new google.maps.Map(
      document.getElementById('map'),
      {center: {lat: lat, lng: lng}, zoom: 16});
}

function fail()
{
  alert("Failed to load location information.");
}
