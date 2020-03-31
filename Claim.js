var mongoose = require('mongoose');

// the host:port must match the location where you are running MongoDB
// the "myDatabase" part can be anything you like
mongoose.connect('mongodb://localhost:27017/claim', { useNewUrlParser: true });
//mongoose.set('useCreateIndex', true);

var Schema = mongoose.Schema;

var claimSchema = new Schema({
    name: String,
    location: String,
    status: String,
    userID: String,
    contactInfo: String,
    description: String
    });

// export personSchema as a class called Person
module.exports = mongoose.model('Claim', claimSchema);

//personSchema.methods.standardizeName = function() {
//    this.name = this.name.toLowerCase();
//    return this.name;
//}
