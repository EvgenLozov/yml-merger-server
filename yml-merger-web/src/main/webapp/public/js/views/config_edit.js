
"use strict";
APP.ConfigEditView = Backbone.View.extend({
  // functions to fire on events
  events: {
    "click button.save": "save"
  },

  // the constructor
  initialize: function (options) {
    this.config  = options.config;
  },

  save: function (event) {
    // this keeps the form from submitting
    event.stopPropagation();
    event.preventDefault();

    // update our model with values from the form
    this.config.set({
      name: this.$el.find('#name').val(),
      user: this.$el.find('#user').val(),
      psw: this.$el.find('#psw').val(),
      encoding: this.$el.find('#encoding').val(),
      currency: this.$el.find('#currency').val(),
      urls: this.$el.find('#urls').val().split(","),
      outputFile: this.$el.find('#outputFile').val(),
      files: this.$el.find('#files').val().split(","),
      categoryIds: this.$el.find('#categoryIds').val().split(","),
      oldPrice: this.$el.find('#oldPrice').val()/100,
      replaces: getReplaces(this.$el.find('#replaces').val())
    });
    // we would save to the server here with
    this.config.save();
    // redirect back to the index
    window.location.hash = "configs/index";
  },

  // populate the html to the dom
  render: function () {
    this.$el.html(_.template($('#formTemplate').html(), this.config.toJSON()));
    return this;
  }
});

function getReplaces(replacesValue){
  var replaces = [];
  var replacesArray = replacesValue.split(";");

  replacesArray.forEach(function(replaceString){
    if (!replaceString.trim()) {
      return;
    }

    var replacement = replaceString.split("-")[0].trim();
    var wordsToReplaceString = replaceString.split("-")[1].trim();
    var wordsToReplace = wordsToReplaceString.split(",");

    var newReplace = { replacement : replacement,
      wordsToReplace : wordsToReplace };

    replaces.push(newReplace);
  });

  return replaces;
}