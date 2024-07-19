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
            <div class="card">
                <div class="card-header">
                    Top Performing Participants
                </div>
                <div class="card-body">
                    <ul class="list-group">
                        @foreach ($topStudents as $challengeNo => $students)
                            @foreach ($students as $student)
                                <li class="list-group-item">
                                    <strong>Name:</strong> {{ $student['name'] }} <br>
                                    <strong>School:</strong> {{ $student['school'] }} <br>
                                    <strong>Score:</strong> {{ $student['score'] }} <br>
                                </li>
                            @endforeach
                        @endforeach
                    </ul>
                </div>
            </div>
        @else
            <div class="alert alert-warning" role="alert">
                No top students data available.
            </div>
        @endif
    
</body>
</html>
