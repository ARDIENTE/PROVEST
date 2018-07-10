<header>
  <div class="inner">
    <h3 class="masthead-brand">
      <img src="https://www.modexl.net/media/logo/image/demo/loremipsum-logo.png" class="img-fluid" alt="Responsive image" width="200">
    </h3>
    <nav class="nav nav-masthead justify-content-center">
      <a class="nav-link { active: opts.sub === 'dashboard' }" href="/dashboard">Home</a>
      <a class="nav-link { active: opts.sub === 'features' }" href="/features">Features</a>
      <a class="nav-link { active: opts.sub === 'contact' }" href="/contact">Contact us</a>
      <a class="nav-link" href="#" onclick={ signIn }> Sign in </a>
    </nav>
  </div>

  <script>
    signIn(ev) {
      location.href="/auth";
    }
  </script>
</header>
