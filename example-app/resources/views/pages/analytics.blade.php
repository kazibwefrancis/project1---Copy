@extends('layouts.app', ['activePage' => 'dashboard', 'title' => 'Matheletics Challenge', 'navName' => 'Dashboard', 'activeButton' => 'laravel'])

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
    <h2>Analytics Dashboard</h2>

    @if (!empty($challenges))

        <table border="1">
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
</div>
    
</body>
</html>
@endsection