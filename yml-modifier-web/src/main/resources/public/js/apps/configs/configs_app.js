ConfigManager.module("ConfigsApp", function(ConfigsApp, ConfigManager,  Backbone, Marionette, $, _){

    ConfigsApp.Router = Marionette.AppRouter.extend({
       appRoutes: {
           "configs" : "listConfigs",
           "configs/:id": "showConfig"
       }
   });

    var API = {
        listConfigs: function(){
            ConfigsApp.List.Controller.listConfigs();
        },

        showConfig: function(id){
            ConfigsApp.Show.Controller.showConfig(id);
        }
    };

    ConfigManager.on("configs:list", function(){
        ConfigManager.navigate("configs");
        API.listConfigs();

    });

    ConfigManager.on("config:show", function(id){
        ConfigManager.navigate("configs/" + id);
        API.showConfig(id);
    });

    ConfigManager.addInitializer(function(){
        new ConfigsApp.Router({
            controller: API
        });
    });

});