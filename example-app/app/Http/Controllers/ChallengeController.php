<?php

// app/Http/Controllers/ChallengeController.php
namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Challenge;

class ChallengeController extends Controller
{
    public function create()
    {
        return view('setChallengeParameters');
    }

    public function store(Request $request)
    {
        $request->validate([
            'start_date' => 'required|date',
            'end_date' => 'required|date|after:start_date',
            'challenge_duration' => 'required|integer|min:1',
            'number_of_questions' => 'required|integer|min:1',
        ]);

        Challenge::create([
            'start_date' => $request->start_date,
            'end_date' => $request->end_date,
            'challenge_duration' => $request->challenge_duration,
            'number_of_questions' => $request->number_of_questions,
        ]);

        return back()->with('success', 'Challenge parameters set successfully!');
    }
}
