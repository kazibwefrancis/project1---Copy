<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Question;

class QuestionController extends Controller
{
    public function create()
    {
        return view('uploadquestions');
    }

    public function store(Request $request)
    {
        $request->validate([
            'question_file' => 'required|mimes:xls,xlsx|max:2048', // max size in KB
        ]);

        $file = $request->file('question_file');

        // Logic to handle file upload and processing (e.g., saving to database) goes here

        return back()->with('success', 'Questions uploaded successfully!');
    }
}
