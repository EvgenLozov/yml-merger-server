define(["app", "apps/configs/edit/edit_view"], function(ConfigManager, View){
    ConfigManager.module("ConfigsApp.Edit", function(Edit,  ConfigManager,  Backbone, Marionette, $, _){
        Edit.Controller = {
            editConfig: function (id) {
                require(["common/views", "entities/config"], function (CommonViews) {
                    var loadingView = new CommonViews.Loading();
                    ConfigManager.regions.main.show(loadingView);

                    var fetchingConfig = ConfigManager.request("config:entity", id);
                    $.when(fetchingConfig).done(function (config) {
                        var configView;
                        if (config === undefined) {
                            configView = new ConfigManager.ConfigsApp.Show.MissingConfig();
                        } else {
                            configView = new View.Config({
                                model: config,
                                generateTitle: true
                            });
                        }

                        configView.on("form:submit", function (data) {
                            if (config.save(data)) {
                                ConfigManager.trigger("config:show", config.get("id"));
                            } else {
                                configView.triggerMethod("form:data:invalid", config.validationError);
                            }

                        });

                        configView.on("form:cancel", function () {
                            ConfigManager.trigger("configs:list");
                        });

                        ConfigManager.regions.main.show(configView);
                    });
                });
            }
        }
    });

    return ConfigManager.ConfigsApp.Edit.Controller;
});