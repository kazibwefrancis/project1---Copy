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
       $topParticipants = DB::table('challengeattempt')
        ->join('challenges', 'challengeattempt.challenge_no', '=', 'challenges.id')
        ->join('participant', 'challengeattempt.participant_id', '=', 'participant.id')
        ->join('schools', 'participant.school_reg_no', '=', 'schools.registration_number')
        ->select(
            'challengeattempt.challenge_no',
            'challenges.challenge_name',
            'participant.name as participant_name',
            'schools.school_name',
            'challengeattempt.score'
            )
        ->orderBy('challengeattempt.challenge_no')
        ->orderByDesc('challengeattempt.score')
        ->get();

        $challenges = [];

        foreach ($topParticipants as $participant) {
            $challengeNo = $participant->challenge_no;
            if (!isset($challenges[$challengeNo])) {
                $challenges[$challengeNo] = [
                    'challenge_name' => $participant->challenge_name,
                    'participants' => [],
                ];
            }

            if (count($challenges[$challengeNo]['participants']) < 2) {
                $challenges[$challengeNo]['participants'][] = [
                    'participant_name' => $participant->participant_name,
                    'school_name' => $participant->school_name,
                    'score' => $participant->score,
                ];
            }
        }

        return $challenges;
    }
}