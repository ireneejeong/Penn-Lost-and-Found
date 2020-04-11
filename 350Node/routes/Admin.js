var mongoose = require('mongoose');

// the host:port must match the location where you are running MongoDB
// the "myDatabase" part can be anything you like
mongoose.connect('mongodb://localhost:27017/admins', { useNewUrlParser: true });
//mongoose.set('useCreateIndex', true);

var Schema = mongoose.Schema;

var adminSchema = new Schema({
	name: {type: String, required: true, unique: true},
	password: {type: String, required: true},
	email: {type: String}
    });

// export adminSchema as a class called Admin
module.exports = mongoose.model('Admin', adminSchema);