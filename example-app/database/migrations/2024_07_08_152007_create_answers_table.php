<?php

use Illuminate\Database\Migrations\Migration;
   use Illuminate\Database\Schema\Blueprint;
   use Illuminate\Support\Facades\Schema;

   class CreateAnswersTable extends Migration
   {
       public function up()
       {
           Schema::create('answers', function (Blueprint $table) {
               $table->id();
            //    $table->foreignId('question_id')->constrained()->onDelete('cascade');
               $table->string('ANS NO');
               $table->string('ANS TEXT');
               $table->timestamps();
           });
       }

       public function down()
       {
           Schema::dropIfExists('answers');
       }
   };
   
