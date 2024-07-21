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
        <div class="container">
        <h1>Top Two Participants Per Challenge</h1>

        <ul>
            @foreach ($topParticipants as $participant)
                <li>Challenge No: {{ $participant->challenge_no }}, Participant ID: {{ $participant->participant_id }}, Score: {{ $participant->max_score }}</li>
            @endforeach
        </ul>
    </div>
        @else
            <div class="alert alert-warning" role="alert">
                No top students data available.
            </div>
        @endif
    
</body>
</html>
