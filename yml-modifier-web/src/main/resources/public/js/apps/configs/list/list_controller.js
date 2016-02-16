ConfigManager.module("ConfigsApp.List", function(List, ConfigManager,  Backbone, Marionette, $, _){

    List.Controller = {
      listConfigs : function(){
          var loadingView = new ConfigManager.Common.Views.Loading({title: "Loading list of configs"});
          ConfigManager.mainRegion.show(loadingView);

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


              configListView.on("itemview:config:edit", function(childView, model){
                  var view = new ConfigManager.ConfigsApp.Edit.Config({
                      model: model,
                      asModal: true
                  });

                  view.on("form:submit", function(data){
                    if (model.save(data)){
                        childView.render();
                        ConfigManager.dialogRegion.close();
                        childView.flash("success");
                    } else {
                        view.triggerMethod("form:data:invalid", model.validationError)
                    }
                  });

                  ConfigManager.dialogRegion.show(view);

              });


              ConfigManager.mainRegion.show(configListView);
          });
      }
    };
});