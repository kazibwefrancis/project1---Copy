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
        <h1 class="mb-4">Set Challenge Parameters</h1>
        
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
        
        <form action="{{ route('challenges.store') }}" method="POST" class="needs-validation" novalidate>
            @csrf
            <div class="form-group">
                <label for="start_date">Start Date:</label>
                <input type="datetime-local" name="start_date" id="start_date" class="form-control" required>
                <div class="invalid-feedback">
                    Please enter the start date.
                </div>
            </div>
            <div class="form-group">
                <label for="end_date">End Date:</label>
                <input type="datetime-local" name="end_date" id="end_date" class="form-control" required>
                <div class="invalid-feedback">
                    Please enter the end date.
                </div>
            </div>
            <div class="form-group">
                <label for="challenge_duration">Challenge Duration (in minutes):</label>
                <input type="number" name="challenge_duration" id="challenge_duration" class="form-control" required>
                <div class="invalid-feedback">
                    Please enter the challenge duration.
                </div>
            </div>
            <div class="form-group">
                <label for="number_of_questions">Number of Questions:</label>
                <input type="number" name="number_of_questions" id="number_of_questions" class="form-control" required>
                <div class="invalid-feedback">
                    Please enter the number of questions.
                </div>
            </div>
            <button type="submit" class="btn btn-primary">Set Parameters</button>
        </form>
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
