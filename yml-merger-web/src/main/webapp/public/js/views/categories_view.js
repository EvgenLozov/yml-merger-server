
"use strict";
APP.ConfigEditCategoriesView = Backbone.View.extend({
    // functions to fire on events
    events: {
        "click a.parentLink" : "fetchChildrenAndParents"
    },

    // the constructor
    initialize: function (options) {
        this.config = options.config;
        this.parentId = 0;

        this.categories = new APP.CategoryCollection([],{configId : this.config.id, parentId : this.parentId});
        this.categoriesView = new APP.CategoriesView({collection : this.categories});
        this.categories.url = "/configs/" + this.config.id + "/categories/" + this.parentId +"/children";
        this.categories.fetch({reset: true});

        this.breadcrumbsCollection = new APP.CategoryCollection({id : '0', 'name' : 'Root'});
        this.breadcrumbs = new APP.BreadcrumbsView({ collection : this.breadcrumbsCollection });

        this.listenTo(this.categories, 'change', this.saveConfig);
        this.listenTo(this.categories, 'select', this.fetchChildren);
        this.listenTo(this.breadcrumbsCollection, 'select', this.fetchChildren)
    },

    // populate the html to the dom
    render: function () {
        this.$el.html(_.template($('#editCategoriesTpl').html(), this.config.toJSON()));
        this.$el.append(this.breadcrumbs.$el);
        this.$el.append(this.categoriesView.$el);
        this.breadcrumbs.render();
        this.categoriesView.render();

        return this;
    },

    fetchChildren: function(category){
        this.categories.url = "/configs/" + this.config.id + "/categories/" + category.id +"/children";
        this.categories.fetch({reset: true});

        this.breadcrumbsCollection.push(category);
    },

    showParentCategory : function(category){
        this.categories.url = "/configs/" + this.config.id + "/categories/" + categoryId +"/children";
        this.categories.fetch({reset: true});


    },

    saveConfig: function(model){
        if (model.get('checked') == true){
            this.config.get('categoryIds').push(model.id);
        } else {
            var indexToRemove = this.config.get('categoryIds').indexOf(model.id);
            if (indexToRemove > -1) {
                this.config.get('categoryIds').splice(indexToRemove, 1);
            }
        }
        this.config.save();
    }

});

APP.CategoryView = Backbone.View.extend({
    tagName : "tr",

    events : {
        'change .categoryCheck' : 'categoryCheck',
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

function getChildren(parentId){
    if (parentId == "0")
        return [{id : "10", name : "parent", configId : "b840c767-35e3-4479-b857-943a9b0c17c5", parentId : "0", checked: true}];
    else
        return [{id : "11", name : "child", configId : "b840c767-35e3-4479-b857-943a9b0c17c5", parentId : "10"}];
}

function getParents(categoryId){
    if (categoryId == "0")
        return [{id : "0", name : "ROOT", configId : "b840c767-35e3-4479-b857-943a9b0c17c5"}];
    else if (categoryId == "10")
        return [{id : "0", name : "ROOT", configId : "b840c767-35e3-4479-b857-943a9b0c17c5"}];
    else
        return [{id : "0", name : "ROOT", configId : "b840c767-35e3-4479-b857-943a9b0c17c5"},
                {id : "10", name : "parent", configId : "b840c767-35e3-4479-b857-943a9b0c17c5", parentId : "0", checked: true}];
}