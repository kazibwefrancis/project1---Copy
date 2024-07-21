<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use App\Models\ChallengeAttempt;
use Illuminate\Support\Facades\DB;
use Illuminate\Http\Request;

class AnalyticsController extends Controller
{
    public function index()
    {
        $challenges = AnalyticsController::getTopTwoParticipants();
        return view('pages.analytics', compact('challenges'));
    }

    public function getTopTwoParticipants()
{
    // Subquery to get the max scores for each challenge
    $subquery = DB::table('challengeattempt')
        ->select('challenge_no', DB::raw('MAX(score) as max_score'))
        ->groupBy('challenge_no');

    // Main query to join with participant and schools to get the top two participants
    $topParticipants = DB::table('challengeattempt as ca')
        ->join('challenges as c', 'ca.challenge_no', '=', 'c.id')
        ->join('participant as p', 'ca.participant_id', '=', 'p.id')
        ->join('schools as s', 'p.school_reg_no', '=', 's.registration_number')
        ->select('ca.challenge_no', 'c.challenge_name', 's.school_name', 'p.name as participant_name', 'ca.score')
        ->whereRaw('(ca.challenge_no, ca.score) IN (' . $subquery->toSql() . ')')
        ->mergeBindings($subquery)
        ->orderBy('ca.challenge_no')
        ->orderByDesc('ca.score')
        ->get();

    $challenges = [];

    foreach ($topParticipants as $participant) {
        if (!isset($challenges[$participant->challenge_no])) {
            $challenges[$participant->challenge_no] = [
                'challenge_name' => $participant->challenge_name,
                'participants' => []
            ];
        }

        $challenges[$participant->challenge_no]['participants'][] = [
            'participant_name' => $participant->participant_name,
            'school_name' => $participant->school_name,
            'score' => $participant->score,
        ];

        if (count($challenges[$participant->challenge_no]['participants']) >= 2) {
            break; 
        }
    }

    return $challenges;
}
}
