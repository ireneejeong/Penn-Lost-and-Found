// set up Express
var express = require('express');
var app = express();

var mongoose = require('mongoose');
mongoose.connect('mongodb+srv://sisabel:pleasework@cis350cluster-n7ht0.mongodb.net/users?retryWrites=true&w=majority', { useNewUrlParser: true });


//app.use(express.cookieParser());
//app.use(express.session({secret: 'thisIsMySecret'}));

// set up EJS
app.set('view engine', 'ejs');

// set up BodyParser
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: true }));

var Claim = require('./Claim.js');

/***************************************/


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


/*************************************************/

app.use('/public', express.static('public'));

app.use('/main', (req, res) => { res.render('main'); });


app.listen(3000,  () => {
    console.log('Listening on port 3000');
    })
