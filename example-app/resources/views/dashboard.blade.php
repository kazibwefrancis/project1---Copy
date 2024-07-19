
@extends('layouts.app', ['activePage' => 'dashboard', 'title' => 'Matheletics Challenge', 'navName' => 'Dashboard', 'activeButton' => 'laravel'])

@section('content')
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Analytics Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
        }
        .overview {
            display: flex;
            justify-content: space-around;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .metric {
            flex: 1;
            margin: 10px;
            padding: 20px;
            background-color: #ffffff;
            border: 1px solid #ddd;
            border-radius: 8px;
            text-align: center;
        }
        .charts {
            padding: 20px;
        }
        .tables {
            padding: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        .filters {
            display: flex;
            justify-content: space-between;
            padding: 20px;
            background-color: #f5f5f5;
            margin-bottom: 20px;
        }
        .filters label {
            margin-right: 10px;
        }
        .filters select, .filters input {
            margin-right: 20px;
        }
    </style>
</head>
<body>

    <div class="overview">
        <div class="metric">
            <h3>Most Correctly Answered Questions</h3>
            <p id="correct-questions">{{ $data['title']}}</p> <!-- Laravel: Display count of correctly answered questions -->
        </div>
        <div class="metric">
            <h3>Top Schools</h3>
            <p id="school-rankings">{{ $data['description'] }}</p> <!-- Laravel: Display count of top schools -->
        </div>
    </div>

    <div class="charts">
        <canvas id="performanceChart"></canvas>
        <canvas id="questionRepetitionChart"></canvas>
    </div>

    <div class="tables">
        <h3>Worst Performing Schools</h3>
        <table id="worst-schools">
            <thead>
                <tr>
                    <th>School</th>
                    <th>Score</th>
                </tr>
            </thead>
            <tbody>
                <!-- Laravel: Loop through worst performing schools and display each -->
{{--                @foreach($data['description'] as $school)--}}
{{--                    <tr>--}}
{{--                        <td>{{ $school->name }}</td>--}}
{{--                        <td>{{ $school->score }}</td>--}}
{{--                    </tr>--}}
{{--                @endforeach--}}
            </tbody>
        </table>

        <h3>Best Performing Schools</h3>
        <table id="best-schools">
            <thead>
                <tr>
                    <th>School</th>
                    <th>Score</th>
                </tr>
            </thead>
            <tbody>
                <!-- Laravel: Loop through best performing schools and display each -->
{{--                @foreach($data['description'] as $school)--}}
{{--                    <tr>--}}
{{--                        <td>{{ $school->name }}</td>--}}
{{--                        <td>{{ $school->score }}</td>--}}
{{--                    </tr>--}}
{{--                @endforeach--}}
            </tbody>
        </table>

        <h3>Participants with Incomplete Challenges</h3>
        <table id="incomplete-challenges">
            <thead>
                <tr>
                    <th>Participant</th>
                    <th>Challenge</th>
                </tr>
            </thead>
            <tbody>
                <!-- Laravel: Loop through participants with incomplete challenges and display each -->
{{--                @foreach($data['description'] as $participant)--}}
{{--                    <tr>--}}
{{--                        <td>{{ $participant->name }}</td>--}}
{{--                        <td>{{ $participant->challenges }}</td>--}}
{{--                    </tr>--}}
{{--                @endforeach--}}
            </tbody>
        </table>
    </div>

    <div class="filters">
        <label for="date-filter">Date:</label>
        <input type="date" id="date-filter" name="date-filter">

        <label for="category-filter">Category:</label>
        <select id="category-filter" name="category-filter">
            <option value="all">All</option>
            <option value="category1">Category 1</option>
            <option value="category2">Category 2</option>
        </select>

        <button id="apply-filters">Apply Filters</button>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>
        var ctx1 = document.getElementById('performanceChart').getContext('2d');
        var performanceChart = new Chart(ctx1, {
            type: 'line',
            data: {
                labels: [], // Fill with dates/years from Laravel
                datasets: [{
                    label: 'School Performance',
                    data: [], // Fill with performance data from Laravel
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    x: {
                        type: 'time',
                        time: {
                            unit: 'year'
                        }
                    },
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });

        var ctx2 = document.getElementById('questionRepetitionChart').getContext('2d');
        var questionRepetitionChart = new Chart(ctx2, {
            type: 'bar',
            data: {
                labels: [], // Fill with participants from Laravel
                datasets: [{
                    label: 'Question Repetition Percentage',
                    data: [], // Fill with repetition data from Laravel
                    backgroundColor: 'rgba(153, 102, 255, 0.2)',
                    borderColor: 'rgba(153, 102, 255, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });

        document.getElementById('apply-filters').addEventListener('click', function() {
            let date = document.getElementById('date-filter').value;
            let category = document.getElementById('category-filter').value;

            fetch('/analytics/filter', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': '{{ csrf_token() }}'
                },
                body: JSON.stringify({ date, category })
            })
            .then(response => response.json())
            .then(data => {
                // Update the page with filtered data
                document.getElementById('correct-questions').innerText = data.questions.length;
                document.getElementById('school-rankings').innerText = data.schools.length;

                performanceChart.data.labels = data.performance.labels;
                performanceChart.data.datasets[0].data = data.performance.data;
                performanceChart.update();

                questionRepetitionChart.data.labels = data.repetition.labels;
                questionRepetitionChart.data.datasets[0].data = data.repetition.data;
                questionRepetitionChart.update();

                const worstSchoolsTableBody = document.getElementById('worst-schools').getElementsByTagName('tbody')[0];
                worstSchoolsTableBody.innerHTML = '';
                data.worstSchools.forEach(school => {
                    const row = worstSchoolsTableBody.insertRow();
                    row.insertCell(0).innerText = school.name;
                    row.insertCell(1).innerText = school.score;
                });

                const bestSchoolsTableBody = document.getElementById('best-schools').getElementsByTagName('tbody')[0];
                bestSchoolsTableBody.innerHTML = '';
                data.bestSchools.forEach(school => {
                    const row = bestSchoolsTableBody.insertRow();
                    row.insertCell(0).innerText = school.name;
                    row.insertCell(1).innerText = school.score;
                });

                const incompleteChallengesTableBody = document.getElementById('incomplete-challenges').getElementsByTagName('tbody')[0];
                incompleteChallengesTableBody.innerHTML = '';
                data.incompleteChallenges.forEach(participant => {
                    const row = incompleteChallengesTableBody.insertRow();
                    row.insertCell(0).innerText = participant.name;
                    row.insertCell(1).innerText = participant.challenge;
                });
            });
        });
    </script>
</body>
@endsection
