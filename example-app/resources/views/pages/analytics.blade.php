@extends('layouts.app')

@section('content')
    <div class="container">
        <h1>Analytics Dashboard</h1>

        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header">
                        Top Performing Participants
                    </div>
                    <div class="card-body">
                        <ul class="list-group">
                            @foreach ($topStudents as $topStudent)
                                <li class="list-group-item">
                                    <strong>Name:</strong> {{ $topStudent['name'] }} <br>
                                    <strong>School:</strong> {{ $topStudent['school'] }} <br>
                                    <strong>Score:</strong> {{ $topStudent['score'] }} <br>
                                </li>
                            @endforeach
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
@endsection
