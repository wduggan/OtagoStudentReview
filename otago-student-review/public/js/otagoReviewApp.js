/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var module = angular.module('OtagoReviewApp', ['ngResource', 'ngStorage']);

module.factory('registerAPI', function ($resource) {
    return $resource('/api/register');
});

module.factory('signInAPI', function ($resource) {
    return $resource('/api/students/:studentID');
});

module.controller('StudentController', function (registerAPI, $window, signInAPI, $sessionStorage) {
    // If student exists, then we can call it's first name for the message
    if ($sessionStorage.student) {
        this.welcome = "Welcome " + $sessionStorage.student.firstName;
    }

    this.registerStudent = function (student) {
        registerAPI.save(null, student,
                // success callback
                        function () {
                            $window.location = 'signIn.html';
                        },
                        // error callback
                                function (error) {
                                    console.log(error);
                                }
                        );
                    };

// alias 'this' so that we can access it inside callback functions
            let ctrl = this;
            this.signIn = function (studentId, password) {
                // get student from web service
                signInAPI.get({'studentId': studentId},
                                // success callback
                                function (student) {
                                    // also store the retrieved student
                                    $sessionStorage.student = student;
                                    // redirect to home
                                    $window.location = '../signedIn/signedInIndex.html';
                                },
                                // fail callback
                                        function () {
                                            ctrl.signInMessage = 'Sign in failed. Please try again.';
                                        }
                                );
                            };

            this.signOut = function () {
                $sessionStorage.$reset();
                // Redirect to home
                $window.location = 'index.html';
            };

            this.checkSignIn = function () {
                // has the student been added to the session?
                if ($sessionStorage.student) {
                    this.signedIn = true;
                    this.welcome = "Welcome " + $sessionStorage.student.firstName;
                } else {
                    this.signedIn = false;
                }
            };

        });
