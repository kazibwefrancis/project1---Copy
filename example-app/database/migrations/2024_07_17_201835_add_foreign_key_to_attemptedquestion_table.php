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
        Schema::table('attemptedquestion', function (Blueprint $table) {
            $table->bigInteger('question_no')->unsigned()->change();
            
            // Add foreign key constraint
            $table->foreign('question_no')
                  ->references('id')
                  ->on('questions');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::table('attemptedquestion', function (Blueprint $table) {
            //
        });
    }
};
