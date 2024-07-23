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
        @if (!empty($vchallenges))
            <h3> <u>Valid challenges and time left</u> </h3>

            <div class="container">
        <h6><u>Challenge Countdown</u></h6>
        @foreach ($vchallenges as $vchallenge)
            <div class="challenge">
                <h6>{{ $vchallenge['challengeid'] }}. {{ $vchallenge['challengename'] }}</h6>
                <p>Ends in: <span id="countdown-{{ $vchallenge['enddate'],$vchallenge['challengeid'] }}"></span></p>
            </div>
        @endforeach
    </div>
        @else
            <div class="alert alert-warning" role="alert">
                No valid challenges available.
            </div>
        @endif
    </div>
    
</body>
</html>
@endsection

@section('scripts')
    <script>
        var challenges = json_encode($vchallenges);

        function updateCountdown(endTime, elementId) {
            var now = new Date().getTime();
            var distance = endTime - now;

            var days = Math.floor(distance / (1000 * 60 * 60 * 24));
            var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
            var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
            var seconds = Math.floor((distance % (1000 * 60)) / 1000);

            var countdownElement = document.getElementById(elementId);
            if (countdownElement) {
                countdownElement.textContent = days + "d " + hours + "h "
                    + minutes + "m " + seconds + "s ";

                if (distance < 0) {
                    countdownElement.textContent = "Challenge expired";
                }
            }
        }

        function startCountdowns() {
            challenges.forEach(function(vchallenge) {
                var endTime = new Date(vchallenge.enddate).getTime();
                var elementId = "countdown-" + challenge.id;
                setInterval(function() {
                    updateCountdown(endTime, elementId);
                }, 1000);
            });
        }

        window.onload = startCountdowns;
    </script>
@endsection