
"use strict";
APP.ConfigModel = Backbone.Model.extend({

  defaults: {
    "name" : "Конфиг " + getCurrentDate(),
    "user" : "",
    "psw" : "",
    "urls" : [ ],
    "files" : [ ],
    "encoding" : "utf-8",
    "currency" : "RUR",
    "outputFile" : "",
    "categoryIds" : []
  },


  urlRoot: "/configs",

  validate: function (attrs) {
    var errors = {};

    //todo validation

    if (!_.isEmpty(errors)) {
      return errors;
    }
  }
});

APP.ConfigCollection = Backbone.Collection.extend({

  model: APP.ConfigModel,

  url: "/configs"

});

function getCurrentDate(){
  var today = new Date();
  var hh = today.getHours();
  var MM = today.getMinutes();
  var dd = today.getDate();
  var mm = today.getMonth()+1; //January is 0!
  var yyyy = today.getFullYear();

  if(dd<10) {
    dd='0'+dd
  }

  if(mm<10) {
    mm='0'+mm
  }

  today = dd+'/'+mm+'/'+yyyy + " " + hh + ":" + MM;

  return today;
}
