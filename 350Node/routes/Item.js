
var mongoose = require('mongoose');

// the host:port must match the location where you are running MongoDB
mongoose.connect('mongodb+srv://sisabel:pleasework@cis350cluster-n7ht0.mongodb.net/lostItems?retryWrites=true&w=majority', { useNewUrlParser: true });
// mongoose.connect('mongodb://localhost:27017/lostItems', { useNewUrlParser: true });

var Schema = mongoose.Schema;

var itemSchema = new Schema({
	title: String,
	description: String,
	date: Date,
	location: String,
	author: String,
	isClaimed: Boolean,
	isFound: Boolean
});

// export itemSchema as a class called Item
module.exports = mongoose.model('Item', itemSchema);
