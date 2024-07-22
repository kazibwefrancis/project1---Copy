<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use Carbon\Carbon;
use App\Models\Challenge;
use Illuminate\Support\Facades\DB;
use Illuminate\Http\Request;

class AnalyticsController extends Controller
{
    public function index()
    {
        $challenges = AnalyticsController::getTopTwoParticipants();
        $validchallenges = AnalyticsController::displayChallenges();
        return view('pages.analytics', compact('challenges','validchallenges'));
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

    public function displayChallenges()
    {
        $validchallenges = DB::table('challenges')
        ->select('id', 'challenge_name', 'start_date', 'end_date')
        ->where('end_date', '>', now())
        ->orderBy('end_date')
        ->get();

        $validchallenges = Challenge::where('end_date', '>', now())->orderBy('end_date')->get();

        $validchallenges->each(function ($challenge) {
            $now = now();
            $endTime = Carbon::parse($challenge->end_date);
            $diffInSeconds = max($endTime->diffInSeconds($now), 0);

            $days = floor($diffInSeconds / 86400);
            $diffInSeconds -= $days * 86400;
            $hours = floor($diffInSeconds / 3600);
            $diffInSeconds -= $hours * 3600;
            $minutes = floor($diffInSeconds / 60);
            $seconds = $diffInSeconds % 60;

            // Add remaining time to the challenge object
            $challenge->remainingTime = [
                'days' => $days,
                'hours' => $hours,
                'minutes' => $minutes,
                'seconds' => $seconds,
            ];
        });

        return $validchallenges;
    }
}