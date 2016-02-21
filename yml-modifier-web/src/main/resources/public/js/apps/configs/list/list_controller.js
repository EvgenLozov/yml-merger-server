ConfigManager.module("ConfigsApp.List", function(List, ConfigManager,  Backbone, Marionette, $, _){

    List.Controller = {
      listConfigs : function(){
          var loadingView = new ConfigManager.Common.Views.Loading({title: "Loading list of configs"});
          ConfigManager.mainRegion.show(loadingView);

          var configsListLayout = new List.Layout();
          var configsListPanel = new List.Panel();

          var fetchedConfigs = ConfigManager.request("config:entities");

          $.when(fetchedConfigs).done(function(configs){
              var configListView = new List.Configs({
                  collection: configs
              });

              configsListLayout.on("show", function(){
                  configsListLayout.panelRegion.show(configsListPanel);
                  configsListLayout.configsRegion.show(configListView);
              });

              configListView.on("itemview:config:delete", function(childView, model){
                  model.destroy();
              });

              configListView.on("itemview:config:modify", function(childView, model){
                  $.ajax({
                      type: "POST",
                      url: "/modifierService/" + model.get("id") + "/modify",
                      success : function(){ alert("Процес успешно запущен")},
                      error : function(){ alert("Произошла ошибка")}
                  });
              });

              configListView.on("itemview:config:show", function(childView, model){
                  ConfigManager.trigger("config:show", model.get("id"));
              });


              configsListPanel.on("config:new", function() {
                  var newConfig = new ConfigManager.Entities.Config();

                  var view = new ConfigManager.ConfigsApp.New.Config({
                      model: newConfig,
                      asModal: true
                  });

                  newConfig.on("invalid", function(model, error) {
                      view.triggerMethod("form:data:invalid", newConfig.validationError);
                  });

                  view.on("form:submit", function (data) {

                  newConfig.save(data, { success: function()
                                            {
                                              configs.add(newConfig);
                                              ConfigManager.dialogRegion.close();
                                              configListView.children.findByModel(newConfig).
                                                  flash("success");
                                            }
                                        }
                                );

                  });

                  ConfigManager.dialogRegion.show(view);
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
                        childView.flash("info");
                    } else {
                        view.triggerMethod("form:data:invalid", model.validationError)
                    }
                  });

                  ConfigManager.dialogRegion.show(view);

              });


              ConfigManager.mainRegion.show(configsListLayout);
          });
      }
    };
});