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

    var configs = this.configs;
    var config = this.config;
    var name = this.config.get('name');
    var el = this.$el;

    this.config.destroy({
      success: function() {
        configs.remove(config);
        el.remove();
        alert("Конфиг " + name + " успешно удален");
      },
      error: function() {
        alert("Ошибка при удалении");
      }
    });
  },

  merge: function (event) {
    event.preventDefault();
    event.stopPropagation();

    $.ajax({
      type: "POST",
      url : "/pricelists/" + configId + "/merge",
      success: function(){
        console.log("Merge " + configId);
        alert("Процесс запущен");
      },
      error : function(){
        alert("Ошибка при попытке объединения прайсов");
      }
    });
  },

  download: function (event) {
    event.preventDefault();
    event.stopPropagation();

    var configId = this.config.id;
    var currency = event.currentTarget.getAttribute('currencyVal');
    console.log("Download for name: " + this.config.get('name') + ", currency: " + currency);

    window.location = "/pricelists/" + configId + "/download" + "?currency=" + currency;

    this.$el.find('#downloadMenu').dropdown('toggle');
  }
});
