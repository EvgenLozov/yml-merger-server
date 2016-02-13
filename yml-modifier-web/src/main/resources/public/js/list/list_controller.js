ConfigManager.module("ConfigsApp.List", function(List, ConfigManager,  Backbone, Marionette, $, _){

    List.Controller = {
      listConfigs : function(){
          var configs = ConfigManager.request("config:entities");

          var configListView = new ConfigManager.ConfigsApp.List.Configs({
              collection: configs
          });

          ConfigManager.mainRegion.show(configListView);
      }
    };
});