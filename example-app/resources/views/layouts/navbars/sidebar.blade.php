<div class="sidebar" data-color="black" data-image="{{ asset('light-bootstrap/img/sidebar-4.jpg') }}">
    <!--
Tip 1: You can change the color of the sidebar using: data-color="purple | blue | green | orange | red"

Tip 2: you can also add an image using data-image tag
-->
    <div class="sidebar-wrapper">
        <div class="logo">
            <a href="http://www.creative-tim.com" class="simple-text">
                {{ __("MATHEMATICS CHALLENGE") }}
            </a>
        </div>
        <ul class="nav">
            <li class="nav-item @if($activePage == 'dashboard') active @endif">
                <a class="nav-link" href="{{route('dashboard')}}">
                    <i class="nc-icon nc-chart-pie-35"></i>
                    <p>{{ __(" Admin Dashboard") }}</p>
                </a>
            </li>


            <li class="nav-item">
                <a class="nav-link active  bg-info" href="{{route('page.index', 'uploadschools')}}">
                    
                    <p>{{ __("UPLOAD SCHOOLS ") }}</p>
                </a>


            </li>



            <li class="nav-item">
                <a class="nav-link active  bg-info" href="{{route('page.index', 'setChallengeParameters')}}">
                    
                    <p>{{ __("SET CHALLENGE PARAMETERS ") }}</p>
                </a>


            </li>



            <li class="nav-item">
                <a class="nav-link active  bg-info" href="{{route('page.index', 'uploadquestions')}}">
                    
                    <p>{{ __("UPLOAD QUESTIONS ") }}</p>
                </a>


            </li>


            <li class="nav-item">
                <a class="nav-link active  bg-info" href="{{route('page.index', 'uploadanswers')}}">
                    
                    <p>{{ __("UPLOAD ANSWERS ") }}</p>
                </a>


            </li>

            <li class="nav-item">
                <a class="nav-link active  bg-info" href="{{route('analytics.index', 'analytics')}}">
                    
                    <p>{{ __("ANALYTICS") }}</p>
                </a>


            </li>



        </ul>
    </div>
</div>
