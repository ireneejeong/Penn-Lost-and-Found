var mongoose = require('mongoose');

// the host:port must match the location where you are running MongoDB
mongoose.connect('mongodb://localhost:27017/lostItems', { useNewUrlParser: true });

var Schema = mongoose.Schema;

var itemSchema = new Schema({
	title: String,
	description: String,
	time: String,
	location: String
});

// export itemSchema as a class called Item
module.exports = mongoose.model('Item', itemSchema);
