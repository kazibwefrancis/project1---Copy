<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class ChallengeAttempt extends Model
{
    protected $table = 'challengeattempt';

    protected $fillable = [
        'participant_id',
        'challenge_no',
        'score',
    ];

    public function participant()
    {
        return $this->belongsTo(Participant::class, 'participant_id', 'id');
    }

    public static function getTopTwoStudentsPerChallenge()
    {
        $results = ChallengeAttempt::groupBy('challenge_no', 'participant_id')
            ->selectRaw('challenge_no, participant_id, score')
            ->orderByDesc('score')
            ->get();

        // Array to store top two students per challenge
        $topStudents = [];

        // Iterate through grouped results and get top two students per challenge
        foreach ($results as $result) {
            $challengeNo = $result->challenge_no;

            if (!isset($topStudents[$challengeNo])) {
                $topStudents[$challengeNo] = [];
            }

            if (count($topStudents[$challengeNo]) < 2) {
                // Get participant details (e.g., name, school) and add to top students array
                $participant = Participant::find($result->participant_id);
                if ($participant) {
                    $topStudents[$challengeNo][] = [
                        'participant_id' => $result->participant_id,
                        'name' => $result->name,
                        'score' => $result->score,
                    ];
                }
            }
        }

        return $topStudents;
    }
}
