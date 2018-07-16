<sign-in>
  <form  method="POST" action="../auth/user/login">

    <div class="form-label-group">
      <sign-logo></sign-logo>
    </div>
    <div class="form-label-group" if={opts.mess != ''}>
      <div class="alert alert-danger" role="alert"> {opts.mess} </div>
    </div>

    <div class="form-label-group">
      <input
          type="text"
          name="account_name"
          id="inputUser"
          class="form-control"
          placeholder="Account name"
          required
          autofocus>
      <label for="inputUser">Account name</label>
    </div>
    <div class="form-label-group">
      <input
          type="password"
          name="password"
          id="inputPassword"
          class="form-control"
          placeholder="Password"
          required>
      <label for="inputPassword">Password</label>
    </div>

    <br>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    <p class="mt-5 mb-3 text-muted text-center">
      <span class="nav-link" > Not yet Registered?
        <a id="register-link" href="/auth/sign-up">Sign up</a>
      </span>
      &copy; 2017-{new Date().getFullYear()}
    </p>
  </form>
  <script>
  var self = this,
    helper = window.Helpers.factory()

    this.on('mount', function() {
      helper.setTitle({ title: 'Sign in' })

    })
  </script>
</sign-in>
