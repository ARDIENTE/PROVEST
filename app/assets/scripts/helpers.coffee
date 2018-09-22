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

  getLocationAndVicinity: ->
    url = '/location&vicinity/all'
    httpRequest(url, 'GET')


  httpRequest = (url, reqType) ->
    $.ajax(
      url: url
      type: reqType
      dataType: 'json'
      contentType: 'application/json; charset=utf-8'
    ).then ((res) ->
      return res
    ), (err) ->
      return  err
    

  # Set Page Title
  _companyTitle = 'PROVEST REAL ESTATE'
  setTitle: (obj) ->
    pageTitle = if _.has(obj, 'sub') then obj.title + ' - ' + obj.sub else obj.title

    $('title').text pageTitle + ' - ' + _companyTitle
    $('#title').text obj.title
