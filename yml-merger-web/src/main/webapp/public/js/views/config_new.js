
"use strict";
APP.ConfigNewView = Backbone.View.extend({

  events: {
    "click button.save": "save",
    "click button.addReplace": "addReplace"
  },

  initialize: function (options) {
    this.config  = options.config;
    this.configs = options.configs;
    this.config.bind('invalid', this.showErrors, this);

    this.replaces = new APP.ReplaceCollection({});
    this.replacesView = new APP.ReplaceViewCollection({collection : this.replaces});
  },

  showErrors: function (config, errors) {
    this.$el.find('.error').removeClass('error');
    this.$el.find('.alert').html(_.values(errors).join('<br>')).show();
    // highlight the fields with errors
    _.each(_.keys(errors), _.bind(function (key) {
      this.$el.find('*[name=' + key + ']').parent().addClass('error');
    }, this));
  },

  save: function (event) {
    event.stopPropagation();
    event.preventDefault();

    // update our model with values from the form
    this.config.set({
      name: this.$el.find('#name').val(),
      user: this.$el.find('#user').val(),
      psw: this.$el.find('#psw').val(),
      encoding: this.$el.find('#encoding').val(),
      currency: this.$el.find('#currency').val(),
      oldPrice: this.$el.find('#oldPrice').val()/100,
      replaces: getReplaces(this.$el.find('#replaces').val(), this.$el.find('#removes').val())
    });

    if (!this.$el.find('#urls').val() || !this.$el.find('#urls').val().trim())
      this.config.set({
        urls: []
      });
    else
      this.config.set({
        urls: this.$el.find('#urls').val().split(',')
      });

    if (!this.$el.find('#files').val() || !this.$el.find('#files').val().trim())
      this.config.set({
        files: []
      });
    else
      this.config.set({
        files: this.$el.find('#files').val().split(',')
      });

    if (!this.$el.find('#categoryIds').val() || !this.$el.find('#categoryIds').val().trim())
      this.config.set({
        categoryIds: []
      });
    else
      this.config.set({
        categoryIds: this.$el.find('#categoryIds').val().split(',')
      });

    var autoMerge = this.$el.find('#autoMerge').attr('checked');

    this.config.set({
      autoMerge: autoMerge
    });

    if (autoMerge){
      this.config.set({
        period: this.$el.find('#period').val(),
        time: this.$el.find('#time').val()
      });
    }

    if (this.config.isValid()){
      var newConfig = this.config.attributes;
      var configs = this.configs;
      this.config.save(null,
          {
            success: function(model) {
                        configs.add(model);
                        window.location.hash = "configs/index";
                      },
            error: function(){
                      alert("Ошибка при сохранении")
                    },
            wait: true});
    }
  },

  render: function () {
    this.$el.html(_.template($('#formTemplate').html(), this.config.toJSON()));
    return this;
  },

  addReplace: function(event){
    event.stopPropagation();
    event.preventDefault();

    var replacement = this.$el.find('#replacement').val();
    var wordsToReplace = this.$el.find('#wordsToReplace').val();

    var replace = new APP.ReplaceModel({replacement : replacement, wordsToReplace: wordsToReplace});
    this.replaces.add(replace);

    this.$el.find('#replacement').val("");
    this.$el.find('#wordsToReplace').val("");

    this.$el.find('#replacesTable').empty();
    this.$el.find('#replacesTable').html(this.replacesView.render().el);
  }

});

function generateUUID() {
  var d = new Date().getTime();
  var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
    var r = (d + Math.random()*16)%16 | 0;
    d = Math.floor(d/16);
    return (c=='x' ? r : (r&0x3|0x8)).toString(16);
  });
  return uuid;
};