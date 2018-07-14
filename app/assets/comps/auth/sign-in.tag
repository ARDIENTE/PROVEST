<sign-in>
  <form  method="POST" action="../auth/user/login">
    <sign-logo lists={lists}></sign-logo>
    <!-- <p each={list in lists}>
      <b>{ list.description }</b>
      <img src="{list.image_path}" width="100" class="img-fluid" alt="Responsive image">
    </p> -->
    <div
        class="alert alert-danger alert-dismissible fade show"
        role="alert"
        if={opts.mess !== ''}>
      {opts.mess}
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
    <button
        class="btn btn-lg btn-primary btn-block"
        id="btn-sign-in"
        type="submit">
        Sign in
    </button>

    <p class="mt-5 mb-3 text-muted text-center">
      <span class="nav-link" > Not yet Registered?
        <a id="register-link" href="/auth/sign-up">Sign up</a>
      </span>
      &copy; 2017-{new Date().getFullYear()}
    </p>
  </form>
  <script>
  var self = this,
    helper = window.Helpers.factory(),
    lists = [];

    autoClose() {
      window.setTimeout(function() {
          $(".alert").fadeTo(500, 0).slideUp(500, function(){
              self.opts.mess = '';
              $(this).remove();
          });
      }, 3000);
    }

    getVicinity() {
      helper.getLocationAndVicinity().then(function(res) {
        self.lists = res;
        self.update();
      });
    }

    this.on('mount', function() {
      self.getVicinity()
      helper.setTitle({ title: 'Sign in' })
    })

    this.on('update', () => {
      self.autoClose();
    })
  </script>
</sign-in>
