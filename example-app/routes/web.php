<?php

use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});

use App\Http\Controllers\AnalyticsController;

Route::get('/analytics', [AnalyticsController::class,'index'])->name('analytics.index');


Auth::routes();

Route::get('/home', [App\Http\Controllers\HomeController::class, 'index'])->name('home');
Auth::routes();

Route::get('/home', 'App\Http\Controllers\HomeController@index')->name('dashboard');

Route::group(['middleware' => 'auth'], function () {
	Route::resource('user', 'App\Http\Controllers\UserController', ['except' => ['show']]);
	Route::get('profile', ['as' => 'profile.edit', 'uses' => 'App\Http\Controllers\ProfileController@edit']);
	Route::patch('profile', ['as' => 'profile.update', 'uses' => 'App\Http\Controllers\ProfileController@update']);
	Route::patch('profile/password', ['as' => 'profile.password', 'uses' => 'App\Http\Controllers\ProfileController@password']);
});

Route::group(['middleware' => 'auth'], function () {
	Route::get('{page}', ['as' => 'page.index', 'uses' => 'App\Http\Controllers\PageController@index']);
});


 use App\Http\Controllers\SchoolController;

 Route::get('/uploadschools', [SchoolController::class, 'create'])->name('schools.create');
 Route::post('/uploadschools', [SchoolController::class, 'store'])->name('schools.store');
 

use App\Http\Controllers\ChallengeController;

Route::get('/setChallengeParameters', [ChallengeController::class, 'create'])->name('challenges.create');
Route::post('/setChallengeParameters', [ChallengeController::class, 'store'])->name('challenges.store');


use App\Http\Controllers\QuestionController;

Route::get('/uploadquestions', [QuestionController::class, 'create'])->name('questions.create');
Route::post('/uploadquestions', [QuestionController::class, 'store'])->name('questions.store');


use App\Http\Controllers\AnswerController;

Route::get('/uploadanswers', [AnswerController::class, 'create'])->name('answers.create');
Route::post('/uploadanswers', [AnswerController::class, 'store'])->name('answers.store');


use App\Http\Controllers\ChallengeAttemptController;

Route::get('/challenge-attempts', [ChallengeAttemptController::class, 'index'])->name('challenge-attempts.index');
Route::get('/challenge-attempts/{id}', [ChallengeAttemptController::class, 'show'])->name('challenge-attempts.show');


use App\Http\Controllers\ParticipantController;

Route::get('/participants', [ParticipantController::class, 'index'])->name('participants.index');
Route::get('/participants/{id}', [ParticipantController::class, 'show'])->name('participants.show');
