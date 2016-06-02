
"use strict";
APP.ConfigNewView = Backbone.View.extend({

  events: {
    "click button.save": "save",
    "click button.addReplace": "addReplace",
    "click button.addCategoryIdsPair": "addCategoryIdsPair"
  },

  initialize: function (options) {
    this.template = _.template($('#formTemplate').html());

    this.config = options.config;
    this.configs = options.configs;
    this.config.bind('invalid', this.showErrors, this);

    this.replaces = new APP.ReplaceCollection();
    this.replacesView = new APP.ReplaceViewCollection({collection: this.replaces});

    this.parentIds = new APP.CategoryIdsPairCollection();
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

  save: function (event) {
    event.stopPropagation();
    event.preventDefault();

    var currencies = [];
    $.each($("input[name='currency']:checked"), function () {
      currencies.push($(this).val());
    });

    var filterParameter = {};
    filterParameter.currencies = [];
    $.each($("input[name='filterCurrency']:checked"), function(){
      filterParameter.currencies.push($(this).val());
    });
    if (filterParameter.currencies.length != 0) {
      filterParameter.image = $("input[name='filterImage']").prop('checked');
      filterParameter.description = $("input[name='filterDescription']").prop('checked');
      filterParameter.available = $("input[name='filterAvailable']").prop('checked');
    } else {
      filterParameter = null;
    }

    this.config.set({
      name: this.$el.find('#name').val(),
      user: this.$el.find('#user').val(),
      psw: btoa(this.$el.find('#psw').val()),
      encoding: this.$el.find('#encoding').val(),
      currencies: currencies,
      filterParameter: filterParameter,
      oldPrice: this.$el.find('#oldPrice').val() / 100,
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

    if (!this.$el.find('#notAllowedWords').val() || !this.$el.find('#notAllowedWords').val().trim())
      this.config.set({
        notAllowedWords: []
      });
    else{
      var words = this.$el.find('#notAllowedWords').val().split(',');
      for (var i = 0; i < words.length; i++) {
        words[i] = words[i].trim();
      }
      this.config.set({
        notAllowedWords: words
      });
    }

    var autoMerge = this.$el.find('#autoMerge').prop('checked');

    this.config.set({
      autoMerge: autoMerge
    });

    if (autoMerge){
      this.config.set({
        period: this.$el.find('#period').val(),
        time: this.$el.find('#time').val(),
        periodInHours: this.$el.find('#periodInHours').val()
      });
    }

    if (this.config.isValid()){
      var newConfig = this.config.attributes;
      var configs = this.configs;
      this.config.save(null,
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
    }
  },

  render: function () {
    this.$el.html(this.template(this.config.toJSON()));

    this.$el.find("#parentIdsTable").append(this.parentIdsView.$el);
    this.parentIdsView.render();

    this.$el.find("#replacesTable").append(this.replacesView.$el);
    this.replacesView.render();

    return this;
  },

  addReplace: function () {
    var replacement = this.$el.find('#replacement').val();
    var wordsToReplace = this.$el.find('#wordsToReplace').val().split(",");
    wordsToReplace = wordsToReplace.filter(function (e) {
      return e.trim()
    });

    _.each(wordsToReplace, function (word) {
      word.trim();
    });

    var replace = new APP.ReplaceModel();
    replace.set({replacement: replacement, wordsToReplace: wordsToReplace});
    if (!replace.isValid()) {
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
