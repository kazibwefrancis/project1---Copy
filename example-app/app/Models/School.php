<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
   use Illuminate\Database\Eloquent\Model;

   class School extends Model
   {
       use HasFactory;

       protected $fillable = [
           'school_name',
           'district',
           'registration_number',
           'representative_email',
           'representative_name',
       ];
   }
