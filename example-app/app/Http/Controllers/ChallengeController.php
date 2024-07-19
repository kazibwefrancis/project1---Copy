<?php

// app/Http/Controllers/ChallengeController.php
namespace App\Http\Controllers;

use App\Imports\AnswerImport;
use App\Imports\QuestionImport;
use Illuminate\Http\Request;
use App\Models\Challenge;
use Maatwebsite\Excel\Facades\Excel;

class ChallengeController extends Controller
{
    public function create()
    {
        return view('setChallengeParameters');
    }

    public function store(Request $request)
    {
        $request->validate([
            'challenge_name' => 'required|string',
            'start_date' => 'required|date',
            'end_date' => 'required|date|after:start_date',
            'duration' => 'required|integer|min:1',
            'number_of_questions' => 'required|integer|min:1',
        ]);

        Challenge::create([
            'challenge_name' => $request->challenge_name,
            'start_date' => $request->start_date,
            'end_date' => $request->end_date,
            'challenge_duration' => $request->duration,
            'number_of_questions' => $request->number_of_questions,
        ]);
        //get the challenge_no of the challenge created from the model
        $challenge_no = Challenge::where('challenge_name', $request->challenge_name)->first()->challenge_no;

        $this->uploadFiles($request, $challenge_no);
        return back()->with('success', 'Update successful!');

    }
    public function uploadFiles(Request $request, $challenge_no)
    {
        $request->validate([
            'question_file' => 'required|file|mimes:xlsx',
            'answer_file' => 'required|file|mimes:xlsx',
        ]);

        if ($request->hasFile('question_file') && $request->hasFile('answer_file')) {
            Excel::import(new QuestionImport($challenge_no), request()->file('question_file'));
            Excel::import(new AnswerImport, request()->file('answer_file'));

            return back()->with('success', 'Files uploaded successfully.');
        }

        return back()->with('error', 'Please upload both files.');
    }

}
