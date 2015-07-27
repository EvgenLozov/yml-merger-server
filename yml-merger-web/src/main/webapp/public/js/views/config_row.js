"use strict";
APP.ConfigRowView = Backbone.View.extend({
  // the wrapper defaults to div, so only need to set this if you want something else
  // like in this case we are in a table so a tr
  tagName: "tr",
  // functions to fire on events
  events: {
    "click a.merge": "merge",
    "click a.download": "download",
    "click a.delete": "destroy"
  },

  // the constructor
  initialize: function (options) {
    // model is passed through
    this.config  = options.config;
    this.configs = options.configs;
  },

  // populate the html to the dom
  render: function () {
    this.$el.html(_.template($('#rowTemplate').html(), this.config.toJSON()));
    return this;
  },

  // delete the model
  destroy: function (event) {
    event.preventDefault();
    event.stopPropagation();
    // we would call
    this.config.destroy();
    // which would make a DELETE call to the server with the id of the item
    this.configs.remove(this.config);
    this.$el.remove();
  },

  merge: function (event) {
    event.preventDefault();
    event.stopPropagation();

    var configId = this.config.id;

    $.ajax({
      type: "POST",
      url : "/pricelists/" + configId + "/merge",
      success: function(){
        console.log("Merge " + configId);
        alert("Объединение прайсов запущено");
      },
      error : function(){
        alert("Ошибка при попытке объединения пайсов");
      }
    })
  },

  download: function (event) {
    event.preventDefault();
    event.stopPropagation();

    var configId = this.config.id;

    $.ajax({
      type: "POST",
      url : "/pricelists/" + configId + "/download",
      success: function(){
        console.log("Download " + configId);
      },
      error : function(){
        alert("Ошибка при попытке объединения пайсов");
      }
    })
  }
});
