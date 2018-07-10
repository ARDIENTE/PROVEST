<sign-up>
  <form  method="POST" action="../auth/user/create">
    <sign-logo></sign-logo>

    <div class="form-label-group">
      <input type="text" id="account" name="account_name" class="form-control" placeholder="Account" required>
      <label for="account">Account Name</label>
    </div>

    <div class="form-label-group">
      <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Email" required>
      <label for="inputPassword">Password</label>
    </div>

    <div class="form-label-group">
      <input type="email" id="inputEmail" name="email" class="form-control" placeholder="Email address" required autofocus>
      <label for="inputEmail">Email address</label>
    </div>

    <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    <p class="mt-5 mb-3 text-muted text-center">
      <span class="nav-link" > Already on Websitename?
        <a id="register-link" href="/auth/sign-in">Sign in</a>
      </span>
      &copy; 2017-2018
    </p>
  </form>
  <script>
  var self = this
    helper = window.Helpers.factory()

    this.on('mount', function() {
      helper.setTitle({ title: 'Sign up' })
    })
  </script>
</sign-up>
