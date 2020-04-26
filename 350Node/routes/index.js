// set up Express

var express = require('express');
var app = express();

var mongoose = require('mongoose');
mongoose.set('useFindAndModify', false);
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

var Admin = require('./Admin.js');
var User = require('./User.js');
var Claim = require('./Claim.js');
var userReport = require('./userReport.js');

/***************************************/

// route for adding a new item
app.get('/addItem', itemDBRoutes.addItem);
app.get('/showItemsDB', itemDBRoutes.showItemsDB);
app.get('/showUsersDB', itemDBRoutes.showUsersDB);
app.get('/removeAll', itemDBRoutes.removeAll);
app.get('/showReportedItemsDB', itemDBRoutes.showReportedItemsDB);

// add points to user profile
app.use('/addPoints', (req, res) => {
    var author = req.query.author;
    var points = req.query.points;

    User.findOneAndUpdate( { "email" : author }, { $inc: { "points" : points } }, { new: true }, function(err, result) {
        if (err) {
            res.send(err);
        } else {
            console.log(result);
            res.send(result);
        }
    });
});

//claiming item
app.use('/claimItem', (req, res) => {
    var newClaim = new Claim ({
        name: req.query.name,
        location: req.query.location,
        status: req.query.status,
        userID: req.query.userID,
        contactInfo: req.query.contactInfo,
        description: req.query.description

    });

    Claim.exists ( {name: newClaim.name}, function(err) {
        if (err) {
            res.write('uh oh: ' + err);
            console.log(err);
            res.end();
        } else {
            newClaim.save( (err) => {
                if (err) {
                    res.send('uh oh: ' + err);
                }
                else {
                    res.send('Successfully claimed!');
                }
            });
        }
    });
});

// enable users to send reports when their item is already claimed
app.use('/userReport', (req, res) => {
    var newUserReport = new userReport ({
        name: req.query.name,
        location: req.query.location,
        status: req.query.status,
        userID: req.query.userID,
        contactInfo: req.query.contactInfo,
        description: req.query.description,
        extraMsg: req.query.extraMsg

    });

    userReport.exists ( {name: newUserReport.name}, function(err) {
        if (err) {
            res.write('uh oh: ' + err);
            console.log(err);
            res.end();
        } else {
            newUserReport.save( (err) => {
                if (err) {
                    res.send('uh oh: ' + err);
                }
                else {
                    res.send('Successfully reported!');
                }
            });
        }
    });
});

//updating item status
app.use('/updateStatus', (req, res) => {
    var name = req.query.name;
    var location = req.query.location;

    Item.findOneAndUpdate( { "title" : name , "location" : location}, { $set: { "isClaimed" : true } }, { new: true }, function(err, result) {
        if (err) {
            res.send(err);
        } else {
            console.log(result);
            res.send(result);
        }
    });
});

// -- MOBILE --
app.use('/adduser', (req, res) => {
	var newUser = new User ({
		email: req.query.email,
		password: req.query.pw,
		contact: req.query.contact,
		points: req.query.points
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

// --MOBILE -- 
app.use('/editusercontact', (req, res) => {
	var useremail = req.query.email;
	var newcontact = req.query.contact;
	User.findOne( {email: useremail}, (err, user) => {
		if (err) {
			res.send('uh oh: ' + err);
		} else if (!user) {
			res.send('uh oh: user does not exist');
		} else {
			user.contact = newcontact;
			user.save( (err) => {
				if (err) {
					res.send('uh oh: ' + err);
				} else {
					res.send('Your contact information updated successfully!');
				}
			});
		}
	});
});

// getting lost items from db to show on the search bar
app.use('/fetchItem', (req, res) => {
	Item.find( {}, (err, items) => {
		if (err) {
			console.log('uh oh' + err);
		    res.send(err);
		}
		else {
			res.send(items);
		}
	});
});

//getting lost items from db to show on the search bar
app.use('/fetchClaim', (req, res) => {
	Claim.find( {}, (err, claims) => {
		if (err) {
			console.log('uh oh' + err);
		    res.send(err);
		}
		else {
			res.send(claims);
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

//Deleting item in web (Admin)
app.use('/delete', (req, res) => {
	Item.remove( { title: req.query.title}, (err, item) => {
		if (err){
			res.type('html').status(200);
		    console.log('uh oh' + err);
		    res.send(err);
		} else if (!item) {
			res.type('html').status(200);
			res.send('Item does not exist');
		} else {
			res.redirect('/main');
		
		}
	});
});

//Removing user in web (Admin)
app.use('/removeUser', (req, res) => {
	User.remove( { email: req.query.email}, (err, user) => {
		if (err){
			res.type('html').status(200);
		    console.log('uh oh' + err);
		    res.send(err);
		} else if (!user){
			res.type('html').status(200);
			res.send('User does not exist');
		} else {
			res.redirect('/main');
			

		}
	});
});

// WEB MAIN PAGE
app.use('/main', (req, res) => { res.redirect('/public/main.html'); } );
//app.use('/mapView', (req, res) => { res.redirect('/public/mapView.html'); });



/*************************************************/
app.use('/public', express.static('../public'));

app.use('/', (req, res) => { res.redirect('/public/login.html'); } );

var server = app.listen(3000, function () {
	var port = server.address().port
	console.log("Node app listening at http://127.0.0.1:%s", port)

});

