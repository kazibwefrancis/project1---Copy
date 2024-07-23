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
    }