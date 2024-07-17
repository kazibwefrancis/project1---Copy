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
}
