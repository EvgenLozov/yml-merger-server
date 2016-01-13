
"use strict";
APP.ConfigShowView = Backbone.View.extend({
  // the constructor
  initialize: function (options) {
    this.config = options.config;
  },

  // populate the html to the dom
  render: function () {
    this.$el.html(_.template($('#showTemplate').html(), this.config.toJSON()));
    return this;
  }
});

