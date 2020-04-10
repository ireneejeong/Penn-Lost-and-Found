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

//
var Admin = require('./Admin.js');
var User = require('./User.js');

/***************************************/

// route for adding a new item
app.get('/addItem', itemDBRoutes.addItem);
app.get('/showItemsDB', itemDBRoutes.showItemsDB);
app.get('/removeAll', itemDBRoutes.removeAll);


// -- WEB -- 
app.use('/signup', (req, res) => {
	
	var newAdmin = new Admin ({
		name: req.body.name,
		password: req.body.password,
		email: req.body.email
	    });
	Admin.findOne ( {name: req.body.name}, (err, user) => {
		if (err) {
			res.type('html').status(200);
		    res.write('uh oh: ' + err);
		    console.log(err);
		    res.end();
		} else if (user) {
			res.type('html').status(200);
			res.write('User already exists');
		    res.end();
		} else {
			var pw = newAdmin.password;
		
			if (pw.search(/[a-z]/) < 0) {
				res.send('Your password must include a lowercase letter');
			} else if (pw.search(/[A-Z]/) < 0) {
				res.send('Your password must include a uppercase letter');
			} else if (pw.length < 8 || pw.length > 16) {
				res.send('Your password must be 8~16 characters long');
			} else if (pw.search(/[0-9]/) < 0) {
				res.send('Your password must include a number');
			} else {
				newAdmin.save( (err) => { 
					if (err) {
					    res.type('html').status(200);
					    res.send('uh oh: ' + err);
					} else {
						res.redirect('/public/login.html');
					}
				}); 
			}
		}
    });
});

// -- WEB --
app.use('/login', (req, res) => {
	Admin.findOne( { name: req.body.name}, (err, user) => {
		if (err){
			res.type('html').status(200);
		    console.log('uh oh' + err);
		    res.send(err);
		} else if (user && user.password === req.body.password){
			res.redirect('/main');
		} else {
			res.type('html').status(200);
			res.send('Password Incorrect');
		}
	});
});

// -- MOBILE -- 
app.use('/adduser', (req, res) => {
	var newUser = new User ({
		email: req.query.email,
		password: req.query.pw,
		contact: req.query.contact
	});
	
	User.exists ( {email: newUser.email}, function(err, user) {
		if (err) {
		    res.write('uh oh: ' + err);
		    console.log(err);
		    res.end();
		} else if (user) {
			res.send('User already exists');
		} else {
			newUser.save( (err) => { 
				if (err) {
				    res.send('uh oh: ' + err);
				} 
				else {
					res.send('Correctly signed up!');
				}
			}); 
		}
    });
});

// -- MOBILE -- 
app.use('/getuser', (req, res) => {
	User.findOne( { email: req.query.email}, (err, user) => {
		if (err){
		    console.log('uh oh' + err);
		    res.send(err);
		} else if (user){
			res.send(user);
		} else {
			res.send('Information is not correct');
		}
	}); 
});


// WEB MAIN PAGE
app.use('/main', (req, res) => { res.redirect('/public/main.html'); } );



/*************************************************/
app.use('/public', express.static('../public'));

app.use('/', (req, res) => { res.redirect('/public/login.html'); } );

var server = app.listen(3000, function () {
	var port = server.address().port
	console.log("Node app listening at http://127.0.0.1:%s", port)

});

