ConfigManager.module("ConfigsApp.List", function(List, ConfigManager,  Backbone, Marionette, $, _){

    List.Controller = {
      listConfigs : function(){
          var fetchedConfigs = ConfigManager.request("config:entities");

          $.when(fetchedConfigs).done(function(configs){
              var configListView = new List.Configs({
                  collection: configs
              });

              configListView.on("itemview:config:delete", function(childView, model){
                  model.destroy();
              });

              configListView.on("itemview:config:show", function(childView, model){
                  ConfigManager.trigger("config:show", model.get("id"));
              });

              ConfigManager.mainRegion.show(configListView);
          });
      }
    };
});