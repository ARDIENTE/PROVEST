<header>
  <nav class="navbar fixed-top navbar-expand-lg navbar-info bg-info fixed-top">
      <div class="container">
        <a class="navbar-brand" href="/dashboard">
          <h3 class="masthead-brand">
            ASCENDLAND LOGO
            <!-- <img src="https://www.modexl.net/media/logo/image/demo/loremipsum-logo.png" class="img-fluid" alt="Responsive image" width="200"> -->
          </h3>
        </a>
        <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
          <ul class="navbar-nav ml-auto">
            <li class="nav-item">
              <a class="nav-link { active: opts.sub === 'dashboard' }" href="/dashboard">Home</a>
            </li>
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle { active: opts.sub === 'about-us' }" href="#" id="navbarDropdownPortfolio" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                About Us
              </a>
              <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownPortfolio">
                <a class="dropdown-item" href="/about-us">We Build With You in Mind</a>
                <a class="dropdown-item" href="/about-us">History</a>
                <a class="dropdown-item" href="/about-us">Our Leaders</a>
              </div>
            </li>
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle { active: opts.sub === 'features' }" href="#" id="navbarDropdownBlog" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Projects
              </a>
              <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownBlog">
                <h6 class="dropdown-header">Current Projects</h6>
                <a class="dropdown-item" href="/projects">Project 1</a>
                <a class="dropdown-item" href="/projects">Project 2</a>
                <a class="dropdown-item" href="/projects">Project 3</a>
              </div>
            </li>
            <li class="nav-item">
              <a class="nav-link { active: opts.sub === 'contact' }" href="/contact">Contact Us</a>
            </li>
            <!-- <li class="nav-item">
              <a class="nav-link" href="#" onclick={ signIn }>Sign In</a>
            </li> -->
          </ul>
        </div>
      </div>
    </nav>

  <script>
    signIn(ev) {
      location.href="/auth";
    }
  </script>
</header>
