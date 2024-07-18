<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Participant extends Model
{
    protected $table = 'participants'; 

    public function attempts()
    {
        return $this->hasMany(ChallengeAttempt::class, 'participant_id', 'id');
    }
}
