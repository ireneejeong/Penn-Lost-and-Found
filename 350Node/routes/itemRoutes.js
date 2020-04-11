
var express = require('express');
var app = express();

// set up BodyParser
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: true }));

// import the Item class from Item.js
var Item = require('./Item.js')

/***************************************/

// route for adding a new item
// this is the action of the "add new item" form
var addItem = function(req, res) {
	console.log('time: ' + req.query.date);
	var date = new Date(req.query.date);
	console.log(date);

	var newItem = new Item ( {
		title: req.query.title,
		description: req.query.description,
		date: date,
		location: req.query.location,
		author: req.query.author,
		isClaimed: req.query.isClaimed,
		isFound: req.query.isFound
	});

	console.log(newItem.author);


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
				newItem.save( (err) => { 
					if (err) {
						console.log(err);
						res.send('Error in saving.');		    
					} else {
						res.send('Item reported!');
					}
				});
			}
		}
	});
}

var showItemsDB = function(req, res) {
	// show all in DB
	Item.find( {}, (err, items) => {
		if (err) {
		    res.type('html').status(200);
		    console.log('uh oh' + err);
		    res.write(err);
		}
		else {
		    if (items.length == 0) {
				res.type('html').status(200);
				res.write('There are no items.');
				res.end();
				return;
		    }

		    res.render('all', { items: items });

		}
	});
}

var removeAll = function(req, res) {
	// remove everything in DB
	Item.remove( {}, function (err) {
		if (err) {
                console.log(err)
        } else {
        	res.redirect('/showItemsDB');
        }
	});
}

var routes = {
	addItem: addItem,
	showItemsDB: showItemsDB,
	removeAll: removeAll
};

module.exports = routes


