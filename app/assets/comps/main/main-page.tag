<main-page>
  <div="text-center">
    <h1> Page Html</h1>
  </div>

  <script>
  var self = this
    helper = window.Helpers.factory()

    this.on('mount', function() {
      helper.setTitle({ title: 'page' })
    })
  </script>
</main-page>
