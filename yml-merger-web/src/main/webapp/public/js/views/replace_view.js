
"use strict";
APP.ReplaceView = Backbone.View.extend({
    tagName : "tr",

    initialize: function (options) {
    },

    events: {
        "click a.delete": "destroy"
    },

    // delete the model
    destroy: function (event) {
        event.preventDefault();
        event.stopPropagation();

        this.model.destroy();
    },

    // populate the html to the dom
    render: function () {
        this.$el.html(_.template($('#replaceTemplate').html(), this.model.toJSON()));
        return this;
    }
});

APP.ReplaceViewCollection = Backbone.View.extend({
    tagName : "tbody",

    initialize: function (options) {
        this.collection.bind('add', this.addOne, this);
        this.collection.bind('destroy', this.render, this);
    },

    addOne : function ( replace ) {
        var replaceView = new APP.ReplaceView({ model: replace });
        this.$el.append(replaceView.render().el);
    },

    render : function(){
        this.$el.empty();

        this.collection.each(this.addOne, this);
        return this;
    }
});