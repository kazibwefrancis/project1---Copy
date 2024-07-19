<?php

namespace App\Imports;

use App\Models\Question; // Adjust the namespace according to your actual model
use Maatwebsite\Excel\Concerns\Importable;
use Maatwebsite\Excel\Concerns\ToModel;
use Maatwebsite\Excel\Concerns\WithHeadingRow;

class QuestionImport implements ToModel, WithHeadingRow
{
    use Importable;

    protected $challenge_no;

    public function __construct($challenge_no)
    {
        $this->challenge_no = $challenge_no;
    }

    public function model(array $row)
    {
        return new Question([
            'id' => $row['no'],
            'question_text' => $row['question'],
            'challenge_no' => $this->challenge_no,
        ]);
    }
}
