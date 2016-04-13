
"use strict";
APP.NoteModel = Backbone.Model.extend({
  urlRoot: '/configs',

  // you can set any defaults you would like here
  defaults: {
    "name" : "Новый список",
    "user" : "test",
    "psw" : "test",
    "urls" : [ ],
    "files" : [ ],
    "encoding" : "windows-1251",
    "currencies" : ["RUR"],
    "outputFile" : "",
    "categoryIds" : [],
    "oldPrice" : 0.01,
    "replaces": [],
    "notAllowedWords" : [],
    "autoMerge" : false,
    "period" : 1,
    "periodInHours" : 0,
    "time" : "00:00"
  },

  validate: function (attrs) {
    var errors = {};
    if (!attrs.name) errors.name = "Hey! Give this thing a title.";
    if (!attrs.urls) errors.urls = "You gotta write a urls, duh!";

    if (!_.isEmpty(errors)) {
      return errors;
    }
  }
});

APP.NoteCollection = Backbone.Collection.extend({
  // Reference to this collection's model.
  model: APP.NoteModel,
  url: '/configs'
});
