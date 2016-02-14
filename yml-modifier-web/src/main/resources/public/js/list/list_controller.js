ConfigManager.module("ConfigsApp.List", function(List, ConfigManager,  Backbone, Marionette, $, _){

    List.Controller = {
      listConfigs : function(){
          var configs = ConfigManager.request("config:entities");

          var configListView = new ConfigManager.ConfigsApp.List.Configs({
              collection: configs
          });

          configListView.on("itemview:config:delete", function(childView, model){
            configs.remove(model);
          });

          configListView.on("itemview:config:show", function(childView, model){
              ConfigManager.ConfigsApp.Show.Controller.showConfig(model);
          });

          ConfigManager.mainRegion.show(configListView);
      }
    };
});