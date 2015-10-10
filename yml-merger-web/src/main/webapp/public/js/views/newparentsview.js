"use strict";

APP.NewParentsCategoriesView = Backbone.View.extend({
    tagName: 'select',
    className: 'form-control newparents',

    initialize: function(){
        this.template = _.template($('#newParentsTpl').html());

        this.collection.bind('all', this.render, this);
    },

    render: function () {
        var renderedContent = this.template({collection : this.collection.toJSON()});
        $(this.el).html(renderedContent);
        return this;
    }


});