
"use strict";
APP.ConfigIndexView = Backbone.View.extend({
  // the constructor
  initialize: function (options) {
    // model is passed through
    this.configs = options.configs;
    this.configs.bind('reset', this.addAll, this);
    this.configs.bind('add', this.addAll, this);
    this.configs.bind('change', this.addAll, this);
  },

  // populate the html to the dom
  render: function () {
    this.$el.html($('#indexTemplate').html());
    this.addAll();
    return this;
  },

  addAll: function () {
    // clear out the container each time you render index
    this.$el.find('tbody').children().remove();
    _.each(this.configs.models, $.proxy(this, 'addOne'));
  },

  addOne: function (config) {
    var view = new APP.ConfigRowView({
      configs: this.configs, 
      config: config
    });
    this.$el.find("tbody").append(view.render().el);
  }
});

