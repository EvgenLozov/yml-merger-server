define(["jquery", "backbone", "backbone.nested"], function($, Backbone){

    var Replace = Backbone.NestedAttributesModel.extend({});

    var ReplaceCollection = Backbone.Collection.extend({
        model: Replace
    });

    return {
        Replace : Replace,
        ReplaceCollection: ReplaceCollection
    }
});