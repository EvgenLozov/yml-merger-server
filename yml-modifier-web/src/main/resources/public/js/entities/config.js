ConfigManager.module("Entities", function(Entities, ConfigManager,  Backbone, Marionette, $, _){

    Entities.Config = Backbone.Model.extend();

    Entities.Configs = Backbone.Collection.extend({
        model: Entities.Config
    });

    var configs = new Entities.Configs([
        { id: 1, name: "Config One"},
        { id: 2, name: "Config Two"},
        { id: 3, name: "Config Three"}
    ]);

    var API = {
        getConfigList: function(){
            return configs;
        }
    };

    ConfigManager.reqres.setHandler("config:entities", function(){
        return API.getConfigList();
    });
});