/**
 * Main AngularJS Web Application
 */
var app = angular.module('citygisApp', []);

app.controller('formController', ['$scope', '$http', function ($scope, $http) {
    console.log("Starting Ng APP");
    $scope.user = {
        username: "",
        firstName: "",
        lastName: "",
        email: "",
        createdDate: null,
        lastAccessed: null,
        isActive: null
    };

    $scope.submitUser = function () {

        $http.put('/user/add', $scope.user).then(function (data, status, headers, config) {

            console.log("Server responded" + JSON.stringify(data));



        }, function (data, status, headers, config) {
            console.log("A problem occured.JSON:" + JSON.stringify(data));
        });
    };

  $scope.reset = function() {
    $scope.user.username = "";
    $scope.user.firstName = "";
    $scope.user.lastName = "";
    $scope.user.email = "";
   };


}]);


/*app.controller('formController', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
 console.log("Init scores controller.")
 $scope.getScores = function () {
 $timeout(function () {
 console.log("Retrieving scores:");
 $http.get('/rest/getScores').then(function (data, status, headers, config) {

 console.log("Server responded" + JSON.stringify(data));

 $scope.Scores = angular.fromJson(data.data);
 $scope.getScores();

 }, function (data, status, headers, config) {
 console.log("A problem occured.JSON:" + JSON.stringify(data));
 });
 }, 2000, false);
 };
 $scope.Scores = [{"id": 1, "score": 97897, "spelerName": "Henk"}];

 $scope.getScores();

 }]);

 app.controller('checkController', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
 console.log("Init checkController.");
 $scope.currentLevel = {
 nextTurn: 0,
 winner: -1,
 playerToken: 0,
 playerName: '',
 level: [-1, -1, -1, -1, -1, -1, -1, -1, -1]
 };

 $scope.currentScore = 100000;

 //innitial view state
 $scope.showGame = false;
 $scope.showName = true;

 $scope.model = {userName: 'Henk'};


 $scope.nextMove = function () {
 $timeout(function () {
 if ($scope.currentLevel.winner == -1) {
 if (!$scope.player) {

 $http.put('/rest/executeTurn', $scope.currentLevel).then(function (data, status, headers, config) {

 console.log("Server responded" + JSON.stringify(data));
 $scope.currentLevel = angular.fromJson(data.data);

 var nextTurn = $scope.currentLevel.nextTurn;
 //  $scope.updateCurrentLevel();
 $scope.timedOutMove(nextTurn);

 }, function (data, status, headers, config) {
 console.log("A problem occured.JSON:" + JSON.stringify(data));
 });
 }
 } else {
 console.log("There is a winner:" + $scope.currentLevel.winner);
 if (confirm('We have a winner:'+$scope.currentLevel.winner + '!')) {
 $http.put('/rest/submitScore', $scope.currentLevel).then(function (data, status, headers, config) {

 console.log("Server responded" + JSON.stringify(data));
 $scope.currentLevel = angular.fromJson(data.data);

 var nextTurn = $scope.currentLevel.nextTurn;
 //  $scope.updateCurrentLevel();
 $scope.timedOutMove(nextTurn);

 }, function (data, status, headers, config) {
 console.log("A problem occured.JSON:" + JSON.stringify(data));
 });

 $scope.startGame();
 }else{
 $scope.startGame();
 }

 }
 }, 5, false);
 }

 //follow proper digest track. Zero timeout -> next digest cycle
 $scope.timedOutMove = function (position) {
 $timeout(function () {
 var labels = document.getElementsByTagName('label');
 //uneven moves are pc moves
 if ($scope.moves % 2 != 0) {
 labels[position].click(labels[position]);
 }
 }, 0, false);
 };

 //follow proper digest track. Zero timeout -> next digest cycle
 $scope.timedFunction = function () {
 $timeout(function () {
 var now = new Date().getTime();
 //correction with 1 start second
 var diff = (now - $scope.startTime) + 1000;
 $scope.currentLevel.score = ($scope.currentLevel.score - diff) - ($scope.moves / 2) * 100;

 if ($scope.currentLevel.score > 0) {
 $scope.timedFunction();
 } else {
 $scope.currentLevel.score = 0;
 }
 //TODO Deal with thymeleaf problem. Workaround: Use JQuery selector instead of using angular {{ }} databinding
 $("#time").empty().append($scope.currentLevel.score);
 }, 1000, false);
 };

 $scope.startGame = function () {
 console.log("(Re)starting game...:"+$scope.model.userName);
 $scope.currentLevel = {
 nextTurn: 0,
 winner: -1,
 playerToken: 0,
 playerName: '',
 level: [-1, -1, -1, -1, -1, -1, -1, -1, -1],
 score: 0
 };
 $scope.currentLevel.playerName = $scope.model.userName;
 $scope.currentLevel.winner = -1;

 $scope.showName = false;
 $scope.showGame = true;
 $scope.currentLevel.score = 100000;
 $scope.moves = 0;
 $scope.player = true;

 $scope.currentLevel.playerName = $scope.userName;

 $scope.startTime = new Date().getTime();
 $scope.timedFunction();

 //Wait for reload
 $timeout(function () {
 var labels = document.getElementsByTagName('label');

 $scope.boxes = $("input");
 $scope.pcMove = false;

 for (var i = 0; i < $scope.boxes.length; i++) {
 $scope.boxes[i].indeterminate = true;
 $scope.boxes[i].disabled = false;
 $scope.boxes[i].checked = false;

 $scope.boxes[i].onclick = function (e) {
 var changeVar = this.id.charAt(1);

 if ($scope.player) {
 $scope.currentLevel.level[changeVar] = 1;
 } else {
 $scope.currentLevel.level[changeVar] = 0;
 }
 this.checked = $scope.player;
 this.disabled = true;
 $scope.player = !$scope.player;
 $scope.moves++;
 };
 }
 }, 0, false);
 };

 }]);*/