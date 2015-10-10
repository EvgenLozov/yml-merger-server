
"use strict";
APP.ConfigEditCategoriesView = Backbone.View.extend({
    // functions to fire on events
    events: {
        "click .backToConfig" : function(e){e.preventDefault(); window.history.back();},
        "click .updateCache" : "updateCache",
        "click .showCategory" : "showCategory",
        "click .moveCategories" : "moveCategories"
    },

    // the constructor
    initialize: function (options) {
        this.config = options.config;
        this.parentId = 0;

        this.categories = new APP.CategoryCollection([],{configId : this.config.id, parentId : this.parentId});
        this.categoriesView = new APP.CategoriesView({collection : this.categories});

        this.breadcrumbsCollection = new APP.CategoryCollection({id : '0', 'name' : 'Корневые категории'});
        this.breadcrumbs = new APP.BreadcrumbsView({ collection : this.breadcrumbsCollection });

        this.newParentsCategories = new APP.CategoryCollection();
        this.newParentsSelectView = new APP.NewParentsCategoriesView({collection : this.newParentsCategories});

        this.listenTo(this.categories, 'change', this.saveConfig);
        this.listenTo(this.categories, 'select', this.fetchChildren);
        this.listenTo(this.categories, 'select', this.pushCategoryToBreadcrumbs);

        this.listenTo(this.breadcrumbs, 'select', this.fetchChildren)
    },

    showCategory: function(){
        this.breadcrumbsCollection.reset({id : '0', 'name' : 'Корневые категории'});

        this.$el.append(this.breadcrumbs.$el);
        this.$el.append(this.categoriesView.$el);
        this.breadcrumbs.render();
        this.categoriesView.render();

        this.$el.find('#newparents').append(this.newParentsSelectView.$el);
        this.newParentsSelectView.render();

        this.categories.url = "/configs/" + this.config.id + "/categories/" + this.parentId +"/children";
        this.categories.fetch({reset: true});

        this.newParentsCategories.url = "/configs/" + this.config.id + "/categories/0/children";
        this.newParentsCategories.fetch({reset: true});
    },

    render: function () {
        this.$el.html(_.template($('#editCategoriesTpl').html(), this.config.toJSON()));
        return this;
    },

    fetchChildren: function(category){
        this.categories.url = "/configs/" + this.config.id + "/categories/" + category.id +"/children";
        this.categories.fetch({reset: true});
    },

    pushCategoryToBreadcrumbs: function(category){
        this.breadcrumbsCollection.push(category);
    },

    saveConfig: function(model){
        if (model.get('checked') == true){
            this.config.get('categoryIds').push(model.id);
            this.config.save();
        } else {
            var indexToRemove = this.config.get('categoryIds').indexOf(model.id);
            if (indexToRemove > -1) {
                this.config.get('categoryIds').splice(indexToRemove, 1);
                this.config.save();
            }
        }
    },

    updateCache : function(e){
        e.preventDefault();
        $.ajax({
            type: "POST",
            url: "/configs/" + this.config.id + "/categories",
            success: function(){
                alert("Процес обновления кеша запущен");
            },
            error: function(){
                alert("Произошла ошибка");
            }
        });
    },

    moveCategories: function(){
        var newParentId = this.$el.find(".newparents option:selected").attr('myId');

        var selectedCategories = [];

        var that = this;

        _.each(that.categories.models, function(category){
            if (category.get('isSelectedToMove') == true)
                selectedCategories.push(category);
        });

        _.each(selectedCategories, function(selectedCategory){
            that.config.get('parentIds').push({categoryId: selectedCategory.id, parentId : newParentId});
        });

        this.parentId = 0;
        this.showCategory();
    }

});

APP.CategoryView = Backbone.View.extend({
    tagName : "tr",

    events : {
        'change .categoryCheck' : 'categoryCheck',
        'change .selectToMove' : 'selectToMove',
        'click .category' : function(e){e.preventDefault(); this.model.trigger('select', this.model)}
    },

    render: function () {
        this.$el.html(_.template($('#categoryTpl').html(), this.model.toJSON()));
        this.$el.find('.categoryCheck').prop('checked', this.model.get('checked'));
        return this;
    },

    categoryCheck : function(e){
        var isChecked = $(e.currentTarget).prop('checked');
        this.model.checked(isChecked);
    },

    selectToMove : function(e){
        var isSelected = $(e.currentTarget).prop('checked');
        this.model.selectToMove(isSelected);
    }
});

APP.CategoriesView = Backbone.View.extend({
    tagName: 'table',
    className: 'table table-striped table-hover',

    initialize: function () {
        this.collection.bind('reset', this.render, this);
        this.collection.bind('add', this.addOne, this);
    },

    // populate the html to the dom
    render: function () {
        this.addAll();
        return this;
    },

    addAll: function () {
        // clear out the container each time you render index
        this.$el.children().remove();
        _.each(this.collection.models, $.proxy(this, 'addOne'));
    },

    addOne: function (model) {
        var view = new APP.CategoryView({model : model});
        this.$el.append(view.render().el);
    }
});
