var mongoose = require('mongoose');

// the host:port must match the location where you are running MongoDB
// the "myDatabase" part can be anything you like
mongoose.connect('mongodb://localhost:27017/userreport', { useNewUrlParser: true });

var Schema = mongoose.Schema;

var userReportSchema = new Schema({
    name: String,
    location: String,
    status: String,
    userID: String,
    contactInfo: String,
    description: String,
    extraMsg: String
    });

// export claimSchema as a class called Claim
module.exports = mongoose.model('userReport', userReportSchema);
