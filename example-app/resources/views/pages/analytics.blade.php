@extends('layouts.app', ['activePage' => 'dashboard', 'title' => 'Matheletics Challenge', 'navName' => 'Analytics', 'activeButton' => 'laravel'])

@section('content')
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Analytics Dashboard</title>
</head>
<body>
    <div class="container">
        <br>

    @if (!empty($challenges))

    <h3> <u>Top participants per challenge</u> </h3>

        <table border="1" width="50%">
            <thead>
                <tr>
                    <th>Challenge Name</th>
                    <th>Participant Name</th>
                    <th>School Name</th>
                    <th>Score</th>
                </tr>
            </thead>
            <tbody>
                @foreach ($challenges as $challengeNo => $challenge)
                    @foreach ($challenge['participants'] as $key => $participant)
                        <tr>
                            @if ($key === 0)
                                <td rowspan="{{ count($challenge['participants']) }}">{{ $challenge['challenge_name'] }}</td>
                            @endif
                            <td>{{ $participant['participant_name'] }}</td>
                            <td>{{ $participant['school_name'] }}</td>
                            <td>{{ $participant['score'] }}</td>
                        </tr>
                    @endforeach
                @endforeach
            </tbody>
        </table>

    @else
        <div class="alert alert-warning" role="alert">
            No data available.
        </div>
    @endif
    <br>

    
    <div>
        @if (!empty($validchallenges))
            <h3> <u>Valid challenges and time left</u> </h3>

            @foreach ($validchallenges as $challenge)
                <div class="challenge-item">
                    <h6>{{ $challenge->challenge_name }}</h6>
                    <p>
                        Time left: {{ $challenge->remainingTime['days'] }} days
                        {{ $challenge->remainingTime['hours'] }} hours
                        {{ $challenge->remainingTime['minutes'] }} minutes
                        {{ $challenge->remainingTime['seconds'] }} seconds
                    </p>
                </div>
            @endforeach
        @else
            <div class="alert alert-warning" role="alert">
                No valid challenges available.
            </div>
        @endif
    </div>
    
</body>
</html>
@endsection