
const express = require('express');
const app = express();
const fs = require("fs");
const port = [PORT];

app.get('/api', (req, res) => {

   fs.readFile( __dirname + '/res/json/projectb.json', 'utf8', function (err, data) {
       res.send(data);
       res.end( data );
   });

});

app.get('/api/version', (req, res) => {

   fs.readFile( __dirname + '/res/txt/version.index', 'utf8', function (err, data) {
       res.send(data);
       res.end( data );
   });

});

var server = app.listen(port, () => {

  var host = server.address().address
  var port = server.address().port

  console.log("api running on: [IP]:" + port);

});
