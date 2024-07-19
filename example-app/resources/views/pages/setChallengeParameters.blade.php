<!DOCTYPE html>
<html>
<head>
    <title>Set Challenge Parameters</title>
    <!-- Include Bootstrap CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* Optional: Custom CSS for additional styling */
        body {
            padding: 20px;
        }
        .form-group {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="mb-4">Setup Challenge</h1>

        @if(session('success'))
            <div class="alert alert-success">
                {{ session('success') }}
            </div>
        @endif

        @if($errors->any())
            <div class="alert alert-danger">
                @foreach($errors->all() as $error)
                    <p>{{ $error }}</p>
                @endforeach
            </div>
        @endif

        <div class="card">
            <div class="card-body">
                <form action="{{route('challenges.store')}}" method="POST" enctype="multipart/form-data">
                    @csrf
                    <div class="form-group mb-3">
                        <label for="challenge_name">Challenge Name</label>
                        <input type="text" class="form-control" id="challenge_name" name="challenge_name" required>
                    </div>

                    <div class="form-group mb-3">
                        <label for="start_date">Start Date</label>
                        <input type="date" class="form-control" id="start_date" name="start_date" required>
                    </div>

                    <div class="form-group mb-3">
                        <label for="end_date">End Date</label>
                        <input type="date" class="form-control" id="end_date" name="end_date" required>
                    </div>

                    <div class="form-group mb-3">
                        <label for="duration">Duration (in minutes)</label>
                        <input type="number" class="form-control" id="duration" name="duration" required>
                    </div>

                    <div class="form-group mb-3">
                        <label for="number_of_questions">Number of Questions</label>
                        <input type="number" class="form-control" id="number_of_questions" name="number_of_questions" required>
                    </div>

                    <div class="form-group mb-3">
                        <label for="question_file">Upload Questions File (questions.xlsx)</label>
                        <input type="file" class="form-control" id="question_file" name="question_file" accept=".xlsx" required>
                    </div>

                    <div class="form-group mb-3">
                        <label for="answer_file">Upload Answers File (answers.xlsx)</label>
                        <input type="file" class="form-control" id="answer_file" name="answer_file" accept=".xlsx" required>
                    </div>

                    <button type="submit" class="btn btn-primary">Set Challenge</button>
                </form>
            </div>
        </div>
    </div>

    <!-- Include Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <!-- Custom JS to handle Bootstrap validation -->
    <script>
        (function() {
            'use strict';
            window.addEventListener('load', function() {
                var forms = document.getElementsByClassName('needs-validation');
                var validation = Array.prototype.filter.call(forms, function(form) {
                    form.addEventListener('submit', function(event) {
                        if (form.checkValidity() === false) {
                            event.preventDefault();
                            event.stopPropagation();
                        }
                        form.classList.add('was-validated');
                    }, false);
                });
            }, false);
        })();
    </script>
</body>
</html>
