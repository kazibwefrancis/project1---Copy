<?php

namespace App\Imports;

use App\Models\Question; // Adjust the namespace according to your actual model
use Maatwebsite\Excel\Concerns\ToModel;
use Maatwebsite\Excel\Concerns\WithHeadingRow;

class AnswerImport implements ToModel, WithHeadingRow
{
    public function model(array $row)
    {
        $question = Question::where('id', $row['no'])->first();


        Question::updateOrCreate(
            ['id' => $row['no']], // Attributes to find the model
            ['answer' => $row['answer'], 'marks' => $row['marks']] // Values to update or create
        );

    }
}
