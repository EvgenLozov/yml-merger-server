"use strict";
APP.ConfigRowView = Backbone.View.extend({
  // the wrapper defaults to div, so only need to set this if you want something else
  // like in this case we are in a table so a tr
  tagName: "tr",
  // functions to fire on events
  events: {
    "click a.merge": "merge",
    "click a.download": "download",
    "click button.copy": "copy",
    "click a.logs": "logs",
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

  copy: function(){
    var originName = this.config.get('name');

    var copyConfig = this.config.clone();
    copyConfig.unset('id');
    copyConfig.set('name', originName + " (Копия)");

    var configs = this.configs;
    copyConfig.save(null,
        {
          success: function (model) {
            configs.add(model);
            window.location.hash = "configs/index";
          },
          error: function () {
            alert("Ошибка при сохранении")
          },
          wait: true
        });
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

  logs: function(){
    var win = window.open(window.location.origin + "/configs/" + this.config.id + "/log", '_blank');
    if(win){
      //Browser has allowed it to be opened
      win.focus();
    }else{
      //Broswer has blocked it
      alert('Please allow popups for this site');
    }
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
    var configName = this.config.get('name');
    var currency = event.currentTarget.getAttribute('currencyVal');
    console.log("Download for name: " + this.config.get('name') + ", currency: " + currency);

    window.location = "/pricelists/" + configId + "/download" +
                      "?name=" + configName + "&currency=" + currency;

    this.$el.find('#downloadMenu').dropdown('toggle');
  }
});
