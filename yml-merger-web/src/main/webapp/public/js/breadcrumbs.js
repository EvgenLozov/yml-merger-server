"use strict";
APP.BreadcrumbsView = Backbone.View.extend({

    tagName: 'ol',
    className : 'breadcrumb',

    events : {
        'click .parentLink' : 'back'
    },

    initialize: function(){
        this.template = _.template($('#breadcrumbs').html());

        this.collection.bind('all', this.render, this);
    },

    back : function(e){
        e.preventDefault();

    },

    render : function(){
        var renderedContent = this.template({collection : this.collection.toJSON()});
        $(this.el).html(renderedContent);
        return this;
    }
});