define(["app"],function(ConfigManager){
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
                require(["apps/configs/list/list_controller"], function(ListController){
                    ListController.listConfigs();
                });
            },

            showConfig: function(id){
                require(["apps/configs/show/show_controller"], function(ShowController){
                    ShowController.showConfig(id);
                });
            },

            editConfig: function(id){
                require(["apps/configs/edit/edit_controller"], function(EditController){
                    EditController.editConfig(id);
                });
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

    return ConfigManager.ConfigsApp;
});