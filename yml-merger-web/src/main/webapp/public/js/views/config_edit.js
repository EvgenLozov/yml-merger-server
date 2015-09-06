
"use strict";
APP.ConfigEditView = Backbone.View.extend({
  // functions to fire on events
  events: {
    "click button.save": "save",
    "click button.addReplace": "addReplace",
    "click button.addCategoryIdsPair": "addCategoryIdsPair",
    "click #categories": "showCategories"
  },

  // the constructor
  initialize: function (options) {
    this.template = _.template($('#formTemplate').html());

    this.config  = options.config;
    this.config.bind('invalid', this.showErrors, this);

    this.replaces = new APP.ReplaceCollection(options.config.get('replaces'));
    this.replacesView = new APP.ReplaceViewCollection({collection : this.replaces});

    this.parentIds = new APP.CategoryIdsPairCollection(options.config.get('parentIds'));
    this.parentIdsView = new APP.CategoryIdsPairViewCollection({collection: this.parentIds});
  },

  showErrors: function (config, errors) {
    this.$el.find('.error').removeClass('error');
    this.$el.find('.alert').html(_.values(errors).join('<br>')).show();
    // highlight the fields with errors
    _.each(_.keys(errors), _.bind(function (key) {
      this.$el.find('*[name=' + key + ']').parent().addClass('error');
    }, this));
  },

  showCategories : function(e){
    e.preventDefault();

    this.fillConfigByFormData();

    var self = this;

    if (this.config.isValid()){
      this.config.save(null,
          {
            success: function() {
              router.navigate("config/" + self.config.id + "/categories", {trigger: true});
            },
            error: function(){
              alert("Ошибка при сохранении")
            }}
      )}
  },

  save: function (event) {
    // this keeps the form from submitting
    event.stopPropagation();
    event.preventDefault();

    this.fillConfigByFormData();

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

    this.$el.find("#parentIdsTable").append(this.parentIdsView.$el);
    this.parentIdsView.render();

    this.$el.find("#replacesTable").append(this.replacesView.$el);
    this.replacesView.render();

    var currencies = this.config.get('currencies');
    $.each(this.$el.find("input[name='currency']"), function(){
      if (_.contains(currencies, $(this).val())){
        $(this).prop('checked', true);
      }
    });

    return this;
  },

  fillConfigByFormData : function(){
    var currencies = [];
    $.each($("input[name='currency']:checked"), function(){
      currencies.push($(this).val());
    });

    this.config.set({
      name: this.$el.find('#name').val(),
      user: this.$el.find('#user').val(),
      psw: btoa(this.$el.find('#psw').val()),
      encoding: this.$el.find('#encoding').val(),
      currencies: currencies,
      oldPrice: this.$el.find('#oldPrice').val()/100,
      replaces: getReplaces(this.$el.find("#replacesTable").find('tbody').children()),
      parentIds : getParentIds(this.$el.find("#parentIdsTable").find('tbody').children())
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

    var autoMerge = this.$el.find('#autoMerge').prop('checked');

    this.config.set({
      autoMerge: autoMerge
    });

    if (autoMerge){
      this.config.set({
        period: this.$el.find('#period').val(),
        time: this.$el.find('#time').val()
      });
    }
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
  },

  addCategoryIdsPair: function () {
    var categoryId = this.$el.find("#categoryId").val().trim();
    var parentId = this.$el.find("#parentId").val().trim();

    var pair = new APP.CategoryIdsPair();
    pair.set({ categoryId : categoryId, parentId : parentId});
    if (!pair.isValid()){
      this.showErrors(pair, pair.validationError);
      return;
    }

    this.parentIds.add(pair);

    this.$el.find('#categoryId').val("");
    this.$el.find('#parentId').val("");
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

function getParentIds(rows){
  var parentIds = [];

  _.each(rows, function(row){
    var categoryId = row.children[0].textContent.trim();
    var parentId = row.children[1].textContent.trim();

    parentIds.push({categoryId: categoryId, parentId : parentId});
  });

  return parentIds;
}