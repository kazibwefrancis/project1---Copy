<?php

namespace App\Http\Controllers;

   use Illuminate\Http\Request;
   use App\Models\Answer;

   class AnswerController extends Controller
   {
       public function create()
       {
           return view('uploadanswers');
       }

       public function store(Request $request)
       {
           $request->validate([
               'answer_file' => 'required|mimes:xls,xlsx|max:2048', // max size in KB
           ]);

           $file = $request->file('answer_file');

           // Logic to handle file upload and processing (e.g., saving to database) goes here

           return back()->with('success', 'Answers uploaded successfully!');
       }
   }
   
