<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use App\Models\ChallengeAttempt;
use Illuminate\Http\Request;

class AnalyticsController extends Controller
{
    public function showAnalytics()
    {
        $topStudents = ChallengeAttempt::getTopTwoStudentsPerChallenge();

        return view('pages.analytics', compact($topStudents));
    }
}
