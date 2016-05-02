define(["app", "apps/configs/list/list_view"], function(ConfigManager, View){
    ConfigManager.module("ConfigsApp.List", function(List, ConfigManager,  Backbone, Marionette, $, _){

        List.Controller = {
            listConfigs : function(){
                //var loadingView = new ConfigManager.Common.Views.Loading({title: "Loading list of configs"});
                //ConfigManager.regions.main.show(loadingView);
                require(["entities/config"], function(){

                    var configsListLayout = new View.Layout();
                    var configsListPanel = new View.Panel();

                    var fetchedConfigs = ConfigManager.request("config:entities");

                    $.when(fetchedConfigs).done(function(configs){

                        var configListView = new View.Configs({
                            collection: configs
                        });

                        configsListLayout.on("show", function(){
                            configsListLayout.panelRegion.show(configsListPanel);
                            configsListLayout.configsRegion.show(configListView);
                        });

                        configListView.on("childview:config:delete", function(childView, args){
                            args.model.destroy();
                        });

                        //configListView.on("childview:config:modify", function(childView, model){
                        //    $.ajax({
                        //        type: "POST",
                        //        url: "/modifierService/" + model.get("id") + "/modify",
                        //        success : function(){ alert("Процес успешно запущен")},
                        //        error : function(){ alert("Произошла ошибка")}
                        //    });
                        //});
                        //
                        //configListView.on("childview:config:copy", function(childView, model){
                        //    var attributes = _.clone(model.attributes);
                        //    attributes.id = null;
                        //    attributes.name = model.get("name") + "(Копия)";
                        //
                        //    var newConfig = new ConfigManager.Entities.Config();
                        //
                        //    newConfig.save(attributes, { success: function()
                        //        {
                        //            configs.add(newConfig);
                        //            configListView.children.findByModel(newConfig).
                        //                flash("success");
                        //        }
                        //        }
                        //    );
                        //
                        //});

                        configListView.on("childview:config:show", function(childView, model){
                            ConfigManager.trigger("config:show", model.get("id"));
                        });


                        configsListPanel.on("config:new", function() {
                            require(["apps/configs/new/new_view"], function(NewView) {
                                var newConfig = ConfigManager.request("config:entity:new");

                                var view = new NewView.Config({
                                    model: newConfig
                                });

                                newConfig.on("invalid", function (model, error) {
                                    view.triggerMethod("form:data:invalid", newConfig.validationError);
                                });

                                view.on("form:submit", function (data) {
                                    newConfig.save(data, {
                                            success: function () {
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
                        });

                        configListView.on("childview:config:edit", function(childView, args) {
                            require(["apps/configs/edit/edit_view"], function(EditView) {
                                var model = args.model;
                                var view = new EditView.Config({
                                    model: model
                                });
                                view.on("form:submit", function (data) {
                                    if (model.save(data)) {
                                        childView.render();
                                        view.trigger("dialog:close");
                                        childView.flash("success");
                                    }
                                    else {
                                        view.triggerMethod("form:data:invalid", model.validationError);
                                    }
                                });

                                view.on("form:cancel", function () {
                                    view.trigger("dialog:close");
                                });

                                ConfigManager.regions.dialog.show(view);
                            });

                        });

                        ConfigManager.regions.main.show(configsListLayout);

                    });
                });

            }
        };
    });

    return ConfigManager.ConfigsApp.List.Controller;
});