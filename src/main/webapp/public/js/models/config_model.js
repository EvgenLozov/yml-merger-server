
"use strict";
APP.ConfigModel = Backbone.Model.extend({

  defaults: {
    "user" : "",
    "psw" : "",
    "urls" : [ ],
    "files" : [ ],
    "encoding" : "utf-8",
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
