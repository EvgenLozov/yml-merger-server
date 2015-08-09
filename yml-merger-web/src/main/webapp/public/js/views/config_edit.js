
"use strict";
APP.ConfigEditView = Backbone.View.extend({
  // functions to fire on events
  events: {
    "click button.save": "save",
    "click button.addReplace": "addReplace"
  },

  // the constructor
  initialize: function (options) {
    this.template = _.template($('#formTemplate').html());

    this.config  = options.config;
    this.config.bind('invalid', this.showErrors, this);

    this.replaces = new APP.ReplaceCollection(options.config.get('replaces'));
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
    // this keeps the form from submitting
    event.stopPropagation();
    event.preventDefault();

    // update our model with values from the form
    this.config.set({
      name: this.$el.find('#name').val(),
      user: this.$el.find('#user').val(),
      psw: btoa(this.$el.find('#psw').val()),
      encoding: this.$el.find('#encoding').val(),
      currency: this.$el.find('#currency').val(),
      oldPrice: this.$el.find('#oldPrice').val()/100,
      replaces: getReplaces(this.$el.find("#replacesTable").find('tbody').children())
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
    $(this.el).html(this.template(this.config.toJSON()));

    this.$el.find("#replacesTable").append(this.replacesView.$el);
    this.replacesView.render();

    return this;
  },

  addReplace: function(){
    var replacement = this.$el.find('#replacement').val();
    var wordsToReplace = this.$el.find('#wordsToReplace').val().split(",");
    wordsToReplace = wordsToReplace.filter(function(e){return e.trim()});

    _.each(wordsToReplace, function(word){
      word.trim();
    });

    var replace = new APP.ReplaceModel();
    replace.set({replacement : replacement, wordsToReplace: wordsToReplace});
    if (!replace.isValid()){
      this.showErrors(replace, replace.validationError);
      return;
    }

    this.replaces.add(replace);

    this.$el.find('#replacement').val("");
    this.$el.find('#wordsToReplace').val("");
  }
});

function getReplaces(rows){
  var replaces = [];

  _.each(rows, function(row){
    var replacement = row.children[1].textContent.trim();
    var wordsToReplace = row.children[0].textContent.trim().split(",");

    if (replacement == "[Пустая строка]")
      replaces.push({replacement: "", wordsToReplace: wordsToReplace});
    else
      replaces.push({replacement: replacement, wordsToReplace: wordsToReplace});
  });

  return replaces;
}