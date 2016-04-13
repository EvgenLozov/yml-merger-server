
"use strict";
APP.NoteNewView = Backbone.View.extend({
  // functions to fire on events
  events: {
    "click button.save": "save"
  },

  // the constructor
  initialize: function (options) {
    this.note  = options.note;
    this.notes = options.notes;
    this.note.bind('invalid', this.showErrors, this);
  },

  showErrors: function (note, errors) {
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
    this.note.set({
      name: this.$el.find('input[name=name]').val()
    });

    if (!this.$el.find('#urls').val() || !this.$el.find('#urls').val().trim())
      this.note.set({
        urls: []
      });
    else
    {
      var urls = this.$el.find('#urls').val().split(',');
      var trimedUrls = [];
      _.each(urls, function(url){
        var trimUrl = url.trim();
        trimUrl.replace("\n", "");
        trimedUrls.push(trimUrl);
      });
      this.note.set({
        urls: trimedUrls
      });
    }

    var that = this;

    if (this.note.isValid()){
      this.note.save(null,
          {
            success: function (model) {
              that.notes.add(model);
              window.location.hash = "configs/index";
            },
            error: function () {
              alert("Ошибка при сохранении")
            }
          });
    }
  },

  // populate the html to the dom
  render: function () {
    this.$el.html(_.template($('#formTemplate').html(), this.note.toJSON()));
    return this;
  }
});
