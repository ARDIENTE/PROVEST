class window.Helpers

  _instance = null
  @factory: ->
    if _instance is null then _instance = new Helpers()
    else _instance

  # Path Wrapper
  pathWrapper: (path) =>
    '/' + path

  # Assets path wrapper
  assetsWrapper: (path) ->
    '/assets/' + path

  # Set Page Title
  _companyTitle = 'My Website'
  setTitle: (obj) ->
    pageTitle = if _.has(obj, 'sub') then obj.title + ' - ' + obj.sub else obj.title

    $('title').text pageTitle + ' - ' + _companyTitle
    $('#title').text obj.title
