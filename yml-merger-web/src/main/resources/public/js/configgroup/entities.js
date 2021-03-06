ConfigGroupManager.module("Entities", function(Entities, ConfigGroupManager,  Backbone, Marionette, $, _) {

    Entities.Group = Backbone.Model.extend({

        defaults: {
            name: "",
            epochePeriod: 0,
            mergerConfigIds : []
        },

        urlRoot : "configsGroup",

        validate: function(attrs){
            var errors = {};
            if (!attrs.name || attrs.name == ""){
                errors.name = "Укажите название"
            }

            if (!attrs.epochePeriod || attrs.epochePeriod < 1){
                errors.epochePeriod = "Укажите период не меньше 1 мин."
            }

            if (!_.isEmpty(errors))
                return errors;
        }
    });

    Entities.Groups = Backbone.Collection.extend({
        url : "configsGroup"
    });

    Entities.Config = Backbone.Model.extend({
        urlRoot: "configs"
    });

    Entities.Configs = Backbone.Collection.extend({
        model: Entities.Config,
        url: "configs"
    });

    var API = {
        getConfigEntities: function(){
            var configs = new Entities.Configs();
            var defer = $.Deferred();

            configs.fetch({
                success: function (data) {
                    defer.resolve(data);
                }
            });

            return defer.promise();
        },

        getGroupEntities : function(){
            var groups = new Entities.Groups();
            var defer = $.Deferred();

            groups.fetch({
                success: function (data) {
                    defer.resolve(data);
                }
            });

            return defer.promise();
        }
    };

    ConfigGroupManager.reqres.setHandler("config:entities", function(){
        return API.getConfigEntities();
    });

    ConfigGroupManager.reqres.setHandler("group:entities", function(){
        return API.getGroupEntities();
    });

});
