
"use strict";
APP.NoteEditView = Backbone.View.extend({
  save: function (event) {
    // this keeps the form from submitting
    event.stopPropagation();
    event.preventDefault();

    // update our model with values from the form
    this.note.set({
      name: this.$el.find('input[name=name]').val()
    });

    if (!this.$el.find('#urls').val() || !this.$el.find('#urls').val().trim())
    {
      this.note.set({
        urls: []
      });
    } else
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

    // we would save to the server here with
    this.note.save();
    // redirect back to the index
    window.location.hash = "configs/index";
  },

  // functions to fire on events
  events: {
    "click button.save": "save"
  },

  // the constructor
  initialize: function (options) {
    this.note  = options.note;
  },

  // populate the html to the dom
  render: function () {
    this.$el.html(_.template($('#formTemplate').html(), this.note.toJSON()));
    return this;
  }
});
