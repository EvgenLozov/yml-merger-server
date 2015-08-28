"use strict";
APP.BreadcrumbsCollection = Backbone.Collection.extend({

    model: APP.CategoryModel,

    initialize: function(models, options) {
        options || (options = {});
        if (options.configId) {
            this.configId = options.configId;
        };
        if (options.categoryId) {
            this.categoryId = options.categoryId;
        };
    },

    setCategoryId : function(categoryId){
        this.categoryId = categoryId;
    },

    url: function(){
        return "config/" + this.configId + "/categories/" + this.categoryId + "/parents";
    }

});

APP.BreadcrumbsView = Backbone.View.extend({

    tagName: 'ol',
    className : 'breadcrumb',

    initialize: function(){
        this.template = _.template($('#breadcrumbs').html());

        this.collection.bind('reset', this.render, this);
    },

    render : function(){
        var renderedContent = this.template({collection : this.collection.toJSON()});
        $(this.el).html(renderedContent);
        return this;
    }
});