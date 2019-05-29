const express = require('express');
const app = express();
const fs = require("fs");


app.get('/api', (req, res) => {

   fs.readFile( __dirname +'/json/projectb' +".json", 'utf8', function (err, data) {
       res.send(data);
       res.end( data );
   });

});


var server = app.listen(PORT, () => {

  var host = server.address().address
  var port = server.address().port

  console.log("server running on: IP:PORT/api")

})
