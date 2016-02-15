ConfigManager.module("Entities", function(Entities, ConfigManager,  Backbone, Marionette, $, _){

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

            setTimeout(function(){
                configs.fetch({
                    success: function (data) {
                        defer.resolve(data);
                    }
                });
            }, 1000);

            return defer.promise();
        },

        getConfigEntity: function(id){
            var config = new Entities.Config({id: id});
            var defer = $.Deferred();

            setTimeout(function(){
                config.fetch({
                    success: function(data){
                        defer.resolve(data);
                    },
                    error: function(data){
                        defer.resolve(undefined);
                    }
                });
            }, 1000);

            return defer.promise();
        }
    };

    ConfigManager.reqres.setHandler("config:entities", function(){
        return API.getConfigEntities();
    });

    ConfigManager.reqres.setHandler("config:entity", function(id){
        return API.getConfigEntity(id);
    });

});