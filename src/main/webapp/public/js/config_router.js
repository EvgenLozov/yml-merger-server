"use strict";
window.APP = window.APP || {};
APP.ConfigRouter = Backbone.Router.extend({
  routes: {
    "config/new": "create",
    "configs/index": "index",
    "config/:id/edit": "edit",
    "config/:id/view": "show"
  },

  initialize: function (options) {
    this.configs = options.configs;
    this.index();
  },

  create: function () {
    this.currentView = new APP.ConfigNewView({
      configs: this.configs, config: new APP.ConfigModel()
    });

    $('#primary-content').html(this.currentView.render().el);
  },

  edit: function (id) {
    var config = this.configs.get(id);
    this.currentView = new APP.ConfigEditView({config: config});
    $('#primary-content').html(this.currentView.render().el);
  },

  show: function (id) {
    var config = this.configs.get(id);
    this.currentView = new APP.ConfigShowView({
      config: config
    });
    $('#primary-content').html(this.currentView.render().el);
  },

  index: function () {
    this.currentView = new APP.ConfigIndexView({
      configs: this.configs
    });
    $('#primary-content').html(this.currentView.render().el);
    // we would call to the index with
    // this.configs.fetch()
    // to pull down the index json response to populate our collection initially
  }
});
