"use strict";

APP.ReplaceModel = Backbone.Model.extend({

    defaults : {
        "replacement" : "",
        "wordsToReplace" : []
    },

    validate: function (attrs) {
        var errors = {};

        //if (!attrs.replacement || !attrs.replacement.trim() ||
        //    !attrs.wordsToReplace || attrs.wordsToReplace.length == 0)
        //        errors.replaces = "Укажите слово/фразу для замены";

        _.each(attrs.wordsToReplace, function(word){
            if (!word.trim())
                errors.replaces = "Пустая строка недопустима в списке слов на замену";
        });

        if (!_.isEmpty(errors)) {
            return errors;
        }
    }
});

APP.ReplaceCollection = Backbone.Collection.extend({

    model : APP.ReplaceModel

});