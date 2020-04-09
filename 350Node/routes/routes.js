var express = require('express');
var app = express();

// set up EJS
app.set('view engine', 'ejs');

// set up BodyParser
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: true }));

// import the Item class from Item.js
var Item = require('./Item.js')


/***************************************/

// route for adding a new item
// this is the action of the "add new item" form
var addItem = function(req, res) {
	var newItem = new Item ( {
		title: req.query.title,
		description: req.query.description,
		time: req.query.time,
		location: req.query.location
	});

	// check if exists in DB .. by TITLE and LOCATION
	Item.exists( { title: newItem.title, location: newItem.location }, function(err, result) {
		if (err) {
			res.send(err);
		} else {
			var doesItemExist = result;

			// if newItem exists, return error message
			if (doesItemExist) {
				console.log('Item exists');
				res.send('Item was already reported.');

			} else {
				// else, save new item 
				console.log('waeeee');

				newItem.save( (err) => { 
					if (err) {
						console.log(err);
						res.send('Error in saving.');		    
					} else {
						console.log('?????');
						res.redirect('/showDB');
					}
				});
			}
		}
	});
}

var showDB = function(req, res) {
	res.send('hi');
}

var routes = {
	addItem: addItem,
	showDB: showDB
};

module.exports = routes


