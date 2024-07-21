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

        @if (!empty($topStudents))
            <table>
                <thead>
                <tr>
                    <th>Challenge No</th>
                    <th>Participant ID</th>
                    <th>Max Score</th>
                </tr>
                </thead>
            <tbody>
                @foreach ($topParticipants as $challengeNo => $participants)
                    @foreach ($participants as $participant)
                        <tr>
                            @if ($loop->first)
                                <td rowspan="{{ count($participants) }}">{{ $challengeNo }}</td>
                            @endif
                            <td>{{ $participant['participant_id'] }}</td>
                            <td>{{ $participant['max_score'] }}</td>
                        </tr>
                    @endforeach
                @endforeach
            </tbody>
            </table>
        @else
            <div class="alert alert-warning" role="alert">
                No top students data available.
            </div>
        @endif
    
</body>
</html>
@endsection