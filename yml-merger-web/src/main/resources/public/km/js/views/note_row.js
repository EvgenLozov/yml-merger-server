"use strict";
APP.NoteRowView = Backbone.View.extend({
  // the wrapper defaults to div, so only need to set this if you want something else
  // like in this case we are in a table so a tr
  tagName: "tr",
  // functions to fire on events
  events: {
    "click a.merge": "merge",
    "click a.delete": "destroy"
  },

  // the constructor
  initialize: function (options) {
    // model is passed through
    this.note  = options.note;
    this.notes = options.notes;

    this.note.on('remove', this.render, this)
  },

  // populate the html to the dom
  render: function () {
    this.$el.html(_.template($('#rowTemplate').html(), this.note.toJSON()));
    return this;
  },

  merge: function (event) {
    event.preventDefault();
    event.stopPropagation();

    var configId = this.note.id;

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

  // delete the model
  destroy: function (event) {
    event.preventDefault();
    event.stopPropagation();

    var that  = this;

    this.note.destroy({
      success: function() {
        that.notes.remove(that.note);
        that.$el.remove();
        alert("Note has been removed successfully");
      },
      error: function() {
        alert("Something went wrong");
      }
    });
  }
});
