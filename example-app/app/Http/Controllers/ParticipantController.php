<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use App\Models\Participant;
use Illuminate\Http\Request;

class ParticipantController extends Controller
{
    public function index()
    {
        $participants = Participant::all();
        return view('participants.index', compact('participants'));
    }

    public function show($id)
    {
        $participant = Participant::findOrFail($id);
        return view('participants.show', compact('participant'));
    }
}
