riot.route.base '/'
riot.route.stop()
riot.route.start true

riot.route '/', ->
  riot.mount 'header, footer', { sub: 'dashboard' }
  riot.mount '#content', 'dashboard'

riot.route '/*', (sub) ->
  riot.mount 'header, footer', { sub: sub }
  switch sub
    when 'dashboard'
      riot.mount '#content', 'dashboard'
    when 'contact'
      riot.mount '#content', 'contact'
    when 'features'
      riot.mount '#content', 'features'
    when 'auth'
      riot.mount '#content', 'sign-in'
    when 'main'
      riot.mount '#content', 'main-page'

riot.route '/auth/*', (sub) ->
  switch sub
    when 'sign-up'
      riot.mount '#content', 'sign-up'
    when 'sign-in'
      riot.mount '#content', 'sign-in'
