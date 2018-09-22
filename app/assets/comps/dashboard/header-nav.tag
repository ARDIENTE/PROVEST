<header>
  <nav class="navbar fixed-top navbar-expand-lg navbar-dark bg-dark fixed-top">
      <div class="container">
        <a class="navbar-brand" href="/dashboard">
          <h3 class="masthead-brand">
            <img src="https://www.modexl.net/media/logo/image/demo/loremipsum-logo.png" class="img-fluid" alt="Responsive image" width="200">
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
                <a class="dropdown-item" href="/about-us">We Build Churva</a>
                <a class="dropdown-item" href="/about-us">History</a>
                <a class="dropdown-item" href="/about-us">Our Leaders</a>
              </div>
            </li>
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle { active: opts.sub === 'features' }" href="#" id="navbarDropdownBlog" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Features
              </a>
              <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownBlog">
                <a class="dropdown-item" href="/features">Project 1</a>
                <a class="dropdown-item" href="/features">Project 2</a>
                <a class="dropdown-item" href="/features">Project 3</a>
              </div>
            </li>
            <li class="nav-item">
              <a class="nav-link { active: opts.sub === 'contact' }" href="/contact">Contact</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#" onclick={ signIn }>Sign In</a>
            </li>
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
