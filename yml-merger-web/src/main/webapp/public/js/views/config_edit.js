
"use strict";
APP.ConfigEditView = Backbone.View.extend({
  // functions to fire on events
  events: {
    "click button.save": "save"
  },

  // the constructor
  initialize: function (options) {
    this.config  = options.config;
    this.config.bind('invalid', this.showErrors, this);
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
      oldPrice: this.$el.find('#oldPrice').val()/100,
      replaces: getReplaces(this.$el.find('#replaces').val())
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

    if (this.config.isValid()){
      this.config.save(null,
          {
            success: function() {
                         window.location.hash = "configs/index"
                      },
            error: function(){
                      alert("Ошибка при сохранении")
                    }}
          );
    }
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