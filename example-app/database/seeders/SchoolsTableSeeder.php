<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class SchoolsTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('schools')->insert([
            [
                'school_name' => 'Green Valley High School',
                'district' => 'North District',
                'registration_number' => 'GV123456',
                'representative_email' => 'rep1@greenvalleyhs.edu',
                'representative_name' => 'John Doe',
                'created_at' => now(),
                'updated_at' => now(),
            ],
            [
                'school_name' => 'Sunnydale Elementary',
                'district' => 'East District',
                'registration_number' => 'SD789012',
                'representative_email' => 'rep2@sunnydale.edu',
                'representative_name' => 'Jane Smith',
                'created_at' => now(),
                'updated_at' => now(),
            ],
            [
                'school_name' => 'Riverside Middle School',
                'district' => 'West District',
                'registration_number' => 'RM345678',
                'representative_email' => 'rep3@riversidemiddle.edu',
                'representative_name' => 'Alice Johnson',
                'created_at' => now(),
                'updated_at' => now(),
            ],
        ]);
    }
}
