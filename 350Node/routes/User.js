var mongoose = require('mongoose');

// the host:port must match the location where you are running MongoDB
// the "myDatabase" part can be anything you like
mongoose.connect('mongodb://localhost:27017/users', { useNewUrlParser: true });
//mongoose.set('useCreateIndex', true);

var Schema = mongoose.Schema;

var userSchema = new Schema({
	email: String,
	password: String,
	contact: String,
	points: Number
    });

// export personSchema as a class called Person
module.exports = mongoose.model('User', userSchema);