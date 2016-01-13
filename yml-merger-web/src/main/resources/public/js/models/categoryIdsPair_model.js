"use strict";

APP.CategoryIdsPair = Backbone.Model.extend({

    defaults : {
        "categoryId" : "-1",
        "parentId" : "-1"
    },

    validate: function (attrs) {
        var errors = {};

        if (!attrs.categoryId || !attrs.categoryId.trim())
            errors.categoryId = "Укажите ид категории";

        if (!attrs.parentId || !attrs.parentId.trim())
            errors.parentId = "Укажите ид родительской категории";

        if (!_.isEmpty(errors)) {
            return errors;
        }
    }
});

APP.CategoryIdsPairCollection = Backbone.Collection.extend({

    model : APP.CategoryIdsPair

});