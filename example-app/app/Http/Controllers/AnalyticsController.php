<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use App\Models\ChallengeAttempt;
use Illuminate\Support\Facades\DB;
use Illuminate\Http\Request;

class AnalyticsController extends Controller
{
    public static function getTopTwoParticipants()
    {
        // Using raw SQL to select top two participants per challenge based on their scores
        $topParticipants = DB::select("
            SELECT challenge_no, participant_id, MAX(score) AS max_score
            FROM challengeattempt
            GROUP BY challenge_no, participant_id
            ORDER BY challenge_no ASC, max_score DESC
        ");

        // Organize the data by challenge_no for easier display
        $challenges = [];
        foreach ($topParticipants as $participant) {
            $challenges[$participant->challenge_no][] = [
                'participant_id' => $participant->participant_id,
                'max_score' => $participant->max_score,
            ];
        }
        
        return view('pages.analytics', compact('challenges'));
    }
}
