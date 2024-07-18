<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use App\Models\ChallengeAttempt;
use Illuminate\Http\Request;

class ChallengeAttemptController extends Controller
{
    public function index()
    {
        $attempts = ChallengeAttempt::all();
        return view('challenge_attempts.index', compact('attempts'));
    }

    public function show($id)
    {
        $attempt = ChallengeAttempt::findOrFail($id);
        return view('challenge_attempts.show', compact('attempt'));
    }

}

