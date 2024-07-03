<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Mathematics Competition Dashboard</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/chart.js"></link>
    <style>
        .container {
            width: 90%;
            margin: auto;
            padding-top: 20px;
        }
        .chart-container {
            margin-bottom: 20px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            border-radius: 8px;
            overflow: hidden;
        }
        canvas {
            width: 100% !important;
            height: auto !important;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 style="text-align: center;">Mathematics Competition Dashboard</h1>
        
        <div class="chart-container">
            <h2 style="text-align: center;">Performance Over the Years</h2>
            <canvas id="lineChart"></canvas>
        </div>
        
        <div class="chart-container">
            <h2 style="text-align: center;">Performance of Each Math Challenge</h2>
            <canvas id="barChart"></canvas>
        </div>
        
        <div class="chart-container">
            <h2 style="text-align: center;">Number of Students from Each School</h2>
            <canvas id="pieChart"></canvas>
        </div>
        
        <div class="chart-container">
            <h2 style="text-align: center;">Completion Status of Students</h2>
            <canvas id="doughnutChart"></canvas>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>
        // Function to update line chart data
        function updateLineChart(data) {
            lineChart.data.datasets[0].data = data;
            lineChart.update();
        }

        // Function to update bar chart data
        function updateBarChart(data) {
            barChart.data.datasets[0].data = data;
            barChart.update();
        }

        // Function to update pie chart data
        function updatePieChart(data) {
            pieChart.data.datasets[0].data = data;
            pieChart.update();
        }

        // Function to update doughnut chart data
        function updateDoughnutChart(data) {
            doughnutChart.data.datasets[0].data = data;
            doughnutChart.update();
        }

        // Initial data for charts (dummy data)
        var lineChartData = [65, 59, 80, 81, 56, 55];
        var barChartData = [12, 19, 3, 5];
        var pieChartData = [300, 50, 100, 75];
        var doughnutChartData = [120, 80];

        // Line chart for performance over the years
        var ctx1 = document.getElementById('lineChart').getContext('2d');
        var lineChart = new Chart(ctx1, {
            type: 'line',
            data: {
                labels: ['2018', '2019', '2020', '2021', '2022', '2023'],
                datasets: [{
                    label: 'Performance',
                    data: lineChartData,
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 2,
                    fill: false
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });

        // Bar chart for performance of each math challenge
        var ctx2 = document.getElementById('barChart').getContext('2d');
        var barChart = new Chart(ctx2, {
            type: 'bar',
            data: {
                labels: ['Algebra', 'Geometry', 'Calculus', 'Statistics'],
                datasets: [{
                    label: 'Performance',
                    data: barChartData,
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)'
                    ],
                    borderColor: [
                        'rgba(255, 99, 132, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });

        // Pie chart for number of students from each school
        var ctx3 = document.getElementById('pieChart').getContext('2d');
        var pieChart = new Chart(ctx3, {
            type: 'pie',
            data: {
                labels: ['School A', 'School B', 'School C', 'School D'],
                datasets: [{
                    label: 'Number of Students',
                    data: pieChartData,
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)'
                    ],
                    borderColor: [
                        'rgba(255, 99, 132, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)'
                    ],
                    borderWidth: 1
                }]
            }
        });

        // Doughnut chart for completion status of students
        var ctx4 = document.getElementById('doughnutChart').getContext('2d');
        var doughnutChart = new Chart(ctx4, {
            type: 'doughnut',
            data: {
                labels: ['Completed', 'Not Completed'],
                datasets: [{
                    label: 'Completion Status',
                    data: doughnutChartData,
                    backgroundColor: [
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(255, 99, 132, 0.2)'
                    ],
                    borderColor: [
                        'rgba(75, 192, 192, 1)',
                        'rgba(255, 99, 132, 1)'
                    ],
                    borderWidth: 1
                }]
            }
        });

        // AJAX request to update charts with real-time data
        function updateCharts() {
            // Example AJAX call with jQuery
            $.ajax({
                url: 'your-backend-endpoint.php',
                method: 'GET',
                success: function(response) {
                    // Assuming your response format matches the chart data structures
                    updateLineChart(response.lineChartData);
                    updateBarChart(response.barChartData);
                    updatePieChart(response.pieChartData);
                    updateDoughnutChart(response.doughnutChartData);
                },
                error: function(xhr, status, error) {
                    console.error('Error fetching data:', error);
                }
            });
        }

        // Call updateCharts() to initiate the data update process
        updateCharts();
    </script>
</body>
</html>
