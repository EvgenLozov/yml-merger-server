ConfigManager.module("ConfigsApp", function(ConfigsApp, ConfigManager,  Backbone, Marionette, $, _){

    ConfigsApp.Router = Marionette.AppRouter.extend({
       appRoutes: {
           "configs" : "listConfigs",
           "configs/:id": "showConfig",
           "configs/:id/edit": "editConfig"
       }
   });

    var API = {
        listConfigs: function(){
            ConfigsApp.List.Controller.listConfigs();
        },

        showConfig: function(id){
            ConfigsApp.Show.Controller.showConfig(id);
        },

        editConfig: function(id){
            ConfigsApp.Edit.Controller.editConfig(id);
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


    ConfigManager.on("config:edit", function(id){
        ConfigManager.navigate("configs/" + id + "/edit");
        API.editConfig(id);
    });

    ConfigManager.addInitializer(function(){
        new ConfigsApp.Router({
            controller: API
        });
    });

});