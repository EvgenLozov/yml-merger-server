"use strict";
APP.BreadcrumbsView = Backbone.View.extend({

    tagName: 'ol',
    className : 'breadcrumb',

    events : {
        'click .checkParent' : 'back'
    },

    initialize: function(){
        this.template = _.template($('#breadcrumbs').html());

        this.collection.bind('all', this.render, this);
    },

    back : function(e){
        e.preventDefault();
        var parentId = $(e.currentTarget).attr('myId');

        while(_.last(this.collection.models).id != parentId){
            this.collection.pop();
        }

        this.trigger('select', this.collection.get(parentId));
    },

    render : function(){
        var renderedContent = this.template({collection : this.collection.toJSON()});
        $(this.el).html(renderedContent);
        return this;
    }
});