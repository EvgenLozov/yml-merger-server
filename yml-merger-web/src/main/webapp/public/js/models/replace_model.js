"use strict";

APP.ReplaceModel = Backbone.Model.extend({

    defaults : {
        "replacement" : "",
        "wordsToReplace" : ""
    },

    validate: function (attrs) {
        var errors = {};

        if (!_.isEmpty(errors)) {
            return errors;
        }
    }
});

APP.ReplaceCollection = Backbone.Collection.extend({

    model : APP.ReplaceModel

});