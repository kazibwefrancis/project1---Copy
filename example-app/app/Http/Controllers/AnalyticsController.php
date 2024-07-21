<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use App\Models\ChallengeAttempt;
use Illuminate\Support\Facades\DB;
use Illuminate\Http\Request;

class AnalyticsController extends Controller
{
    public function getTopTwoParticipants()
    {
        $topParticipants = DB::select("
            SELECT challenge_no, participant_id, MAX(score) AS max_score
            FROM challengeattempt
            GROUP BY challenge_no, participant_id
            ORDER BY challenge_no ASC, max_score DESC
            LIMIT 2
        ");

        return $topParticipants;
        return view('pages.analytics', compact('topParticipants'));
    }
}
