<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
   use App\Models\School;

   class SchoolController extends Controller
   {
       public function create()
       {
           return view('uploadschools');
       }

       public function store(Request $request)
       {
           $request->validate([
               'school_name' => 'required|string|max:255',
               'district' => 'required|string|max:255',
               'registration_number' => 'required|string|max:255',
               'representative_email' => 'required|email|max:255',
               'representative_name' => 'required|string|max:255',
           ]);

           School::create([
               'school_name' => $request->school_name,
               'district' => $request->district,
               'registration_number' => $request->registration_number,
               'representative_email' => $request->representative_email,
               'representative_name' => $request->representative_name,
           ]);

           return back()->with('success', 'School details uploaded successfully!');
       }
   }
