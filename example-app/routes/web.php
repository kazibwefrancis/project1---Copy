<?php

use Illuminate\Support\Facades\Route;

Route::get('/', function () {
    return view('welcome');
});

Route::get('/matheletics', function () {
    return view('matheletics');
});

Route::get('/login-10', function () {
    return view('login-10');
});

Route::get('/rules', function () {
    return view('rules');
});

Route::get('/stat', function () {
    return view('stat');
});