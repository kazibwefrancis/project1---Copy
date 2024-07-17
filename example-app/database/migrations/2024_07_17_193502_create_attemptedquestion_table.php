<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('attemptedquestion', function (Blueprint $table) {
            $table->id();
            $table->string('challenge_no');
            $table->string('participant_id');
            $table->integer('question_no');
            $table->date('start_time');
            $table->string('status');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('attemptedquestion');
    }
};
