<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Upload Answers</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles -->
    <style>
        body {
            background-color: #f8f9fa;
            font-family: Arial, sans-serif;
        }
        .container {
            max-width: 600px;
            margin-top: 50px;
            border-radius: 8px;
            box-shadow: 0px 0px 15px 0px rgba(0,0,0,0.1);
            background-color: #fff;
            padding: 30px;
        }
        .upload-form {
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: #f1f1f1;
        }
        .upload-title {
            text-align: center;
            margin-bottom: 30px;
            color: #007bff;
        }
        .custom-file-label {
            overflow: hidden;
            white-space: nowrap;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="upload-form">
            <h1 class="upload-title">Upload Answers</h1>
            
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
            
            <form action="{{ route('answers.store') }}" method="POST" enctype="multipart/form-data" class="needs-validation" novalidate>
                @csrf
                <div class="form-group">
                    <label for="answer_file">Choose Excel File (xls, xlsx):</label>
                    <div class="custom-file">
                        <input type="file" class="custom-file-input" id="answer_file" name="answer_file" accept=".xls,.xlsx" required>
                        <label class="custom-file-label" for="answer_file">Select file</label>
                        <div class="invalid-feedback">Please select an Excel file (.xls or .xlsx).</div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary btn-block">Upload File</button>
            </form>
        </div>
    </div>

    <!-- Bootstrap JS and dependencies -->
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
