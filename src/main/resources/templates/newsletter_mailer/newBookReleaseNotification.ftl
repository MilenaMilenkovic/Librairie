<!DOCTYPE html>
<html>
  <head>
    <meta content='text/html; charset=UTF-8' http-equiv='Content-Type' />
  </head>
  <body>
    <div>
      <h3>Book ${title}<h3>
      <h5>by ${author}<b5>

      now available at: <b> Bookstore </b>

      <div>
        <#if cover_image_url??>
       	 <img src="http://localhost:8080/${cover_image_url}">
        </#if>
      </div>

    </div>
  </body>
</html>
