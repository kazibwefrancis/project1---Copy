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
        Schema::table('challengeattempt', function (Blueprint $table) {
            $table->bigInteger('challenge_no')->unsigned()->change();
            
            // Add foreign key constraint
            $table->foreign('challenge_no')
                  ->references('id')
                  ->on('challenges');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::table('challengeattempt', function (Blueprint $table) {
            //
        });
    }
};
