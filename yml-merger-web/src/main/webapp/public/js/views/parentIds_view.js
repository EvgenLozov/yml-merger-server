"use strict";

APP.CategoryIdsPairView = Backbone.View.extend({
    tagName : "tr",

    initialize: function (options) {
    },

    events: {
        "click a.delete": "destroy"
    },

    destroy: function (event) {
        event.preventDefault();
        event.stopPropagation();

        this.model.destroy();
    },

    render: function () {
        this.$el.html(_.template($('#categoryPairTemplate').html(), this.model.toJSON()));
        return this;
    }
});

APP.CategoryIdsPairViewCollection = Backbone.View.extend({
    tagName : "tbody",

    initialize: function (options) {
        this.collection.bind('add', this.addOne, this);
        this.collection.bind('destroy', this.render, this);
    },

    addOne : function ( pair ) {
        var pairView = new APP.CategoryIdsPairView({ model: pair });
        this.$el.append(pairView.render().el);
    },

    render : function(){
        this.$el.empty();

        this.collection.each(this.addOne, this);
        return this;
    }
});