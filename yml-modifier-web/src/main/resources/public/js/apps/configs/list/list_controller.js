ConfigManager.module("ConfigsApp.List", function(List, ConfigManager,  Backbone, Marionette, $, _){

    List.Controller = {
      listConfigs : function(){
          var loadingView = new ConfigManager.Common.Views.Loading({title: "Loading list of configs"});
          ConfigManager.regions.main.show(loadingView);

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

              configListView.on("childview:config:delete", function(childView, model){
                  model.destroy();
              });

              configListView.on("childview:config:modify", function(childView, model){
                  $.ajax({
                      type: "POST",
                      url: "/modifierService/" + model.get("id") + "/modify",
                      success : function(){ alert("Процес успешно запущен")},
                      error : function(){ alert("Произошла ошибка")}
                  });
              });

              configListView.on("childview:config:copy", function(childView, model){
                  var attributes = _.clone(model.attributes);
                  attributes.id = null;
                  attributes.name = model.get("name") + "(Копия)";

                  var newConfig = new ConfigManager.Entities.Config();

                  newConfig.save(attributes, { success: function()
                                                      {
                                                          configs.add(newConfig);
                                                          configListView.children.findByModel(newConfig).
                                                              flash("success");
                                                      }
                                            }
                                 );

              });

              configListView.on("childview:config:show", function(childView, model){
                  ConfigManager.trigger("config:show", model.get("id"));
              });


              configsListPanel.on("config:new", function() {
                  var newConfig = new ConfigManager.Entities.Config();

                  var view = new ConfigManager.ConfigsApp.New.Config({
                      model: newConfig
                  });

                  newConfig.on("invalid", function(model, error) {
                      view.triggerMethod("form:data:invalid", newConfig.validationError);
                  });

                    view.on("form:submit", function (data) {
                        newConfig.save(data, { success: function()
                                                {
                                                  configs.add(newConfig);
                                                  view.trigger("dialog:close");
                                                  configListView.children.findByModel(newConfig).
                                                      flash("success");
                                                }
                                            }
                                        );

                  });

                  view.on("form:cancel", function () {
                      view.trigger("dialog:close");
                  });

                  ConfigManager.regions.dialog.show(view);
              });

              configListView.on("childview:config:edit", function(childView, model){
                  ConfigManager.trigger("config:edit", model.get("id"));

              });

              ConfigManager.regions.main.show(configsListLayout);
          });
      }
    };
});