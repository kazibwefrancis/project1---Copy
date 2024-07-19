<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\DB;
use App\Models\Participant;

class ChallengeAttempt extends Model
{
    protected $table = 'challengeattempt';

    protected $fillable = [
        'participant_id',
        'challenge_no',
        'score',
        'start_time',
        'end_time',
    ];

    public function participant()
    {
        return $this->belongsTo(Participant::class, 'participant_id', 'id');
    }

    public static function getTopTwoStudentsPerChallenge()
    {
        // Retrieve top two students per challenge
        $topStudents = DB::table('challengeattempt')
            ->select('challenges.challenge_no', 'participant.name', 'participant.school_reg_no', 'ChallengeAttempt.score')
            ->join('participant', 'ChallengeAttempt.participant_id', '=', 'participant.id')
            ->join('challenges', 'ChallengeAttempt.challenge_no', '=', 'challenges.id')
            ->orderBy('challenges.id')
            ->orderByDesc('challengeattempt.score')
            ->groupBy('challenges.id', 'participant.id')
            ->get();

        $formattedTopStudents = [];

        foreach ($topStudents as $student) {
            $challengeNo = $student->challenge_no;

            if (!isset($formattedTopStudents[$challengeNo])) {
                $formattedTopStudents[$challengeNo] = [];
            }

            if (count($formattedTopStudents[$challengeNo]) < 2) {
                $formattedTopStudents[$challengeNo][] = [
                    'name' => $student->name,
                    'school' => $student->school_reg_no,
                    'score' => $student->score,
                ];
            }
        }

        return $formattedTopStudents;
    }
}
