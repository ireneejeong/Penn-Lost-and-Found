// set up Express
var express = require('express');
var app = express();

var mongoose = require('mongoose');
mongoose.connect('mongodb+srv://sisabel:pleasework@cis350cluster-n7ht0.mongodb.net/lostItems?retryWrites=true&w=majority', { useNewUrlParser: true });

// set up EJS
app.set('views', '../views');
app.set('view engine', 'ejs');

// set up BodyParser
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: true }));


// set up routes
var itemDBRoutes = require('./itemRoutes.js');

// import the Item class from Item.js
var Item = require('./Item.js');

/***************************************/

// route for adding a new item
// this is the action of the "add new item" form

app.get('/addItem', itemDBRoutes.addItem);
app.get('/showDB', itemDBRoutes.showDB);
app.get('/removeAll', itemDBRoutes.removeAll);

// example
app.get('/hello', function (req, res) {
	res.send('hi');
});


/*************************************************/

//app.use('/public', express.static('public'));
app.use('/', (req, res) => { res.redirect('/hello') } );

//app.use('/', (req, res) => { res.redirect('/public/personform.html'); } );

var server = app.listen(3000, function () {
	var port = server.address().port
	console.log("Node app listening at http://127.0.0.1:%s", port)

});

