define(["jquery", "backbone", "app", "entities/config/model", "backbone.nested"],
function($, Backbone, ConfigManager, ConfigModel){

    var Configs = Backbone.Collection.extend({
        model: ConfigModel,
        url: "configs"
    });

    var API = {
        getConfigEntities: function(){
            var configs = new Configs();
            var defer = $.Deferred();

            configs.fetch({
                success: function (data) {
                    defer.resolve(data);
                }
            });

            return defer.promise();
        }
    };

    ConfigManager.reqres.setHandler("config:entities", function(){
        return API.getConfigEntities();
    });

    return Configs;
});