<?php

use Illuminate\Database\Migrations\Migration;
   use Illuminate\Database\Schema\Blueprint;
   use Illuminate\Support\Facades\Schema;

   class CreateSchoolsTable extends Migration
   {
       public function up()
       {
           Schema::create('schools', function (Blueprint $table) {
               $table->id();
               $table->string('school_name');
               $table->string('district');
               $table->string('registration_number');
               $table->string('representative_email');
               $table->string('representative_name');
               $table->timestamps();
           });
       }

       public function down()
       {
           Schema::dropIfExists('schools');
       }
   };

