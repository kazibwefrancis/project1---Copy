<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use App\Models\ChallengeAttempt;
use Illuminate\Http\Request;

class AnalyticsController extends Controller
{

    public function showAnalytics()
    {
        // Retrieve top two students per challenge
        $topStudents = ChallengeAttempt::getTopTwoStudentsPerChallenge();

        return view('analytics', compact('topStudents'));
    }

}
