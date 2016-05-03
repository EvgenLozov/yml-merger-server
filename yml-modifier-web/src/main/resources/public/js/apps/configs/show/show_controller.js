define(["app", "apps/configs/show/show_config"], function(ConfigManager, View){
    ConfigManager.module("ConfigsApp.Show", function(Show,  ConfigManager,  Backbone, Marionette, $, _){

        Show.Controller = {
            showConfig: function (id) {
                require(["common/views", "entities/config"], function (CommonViews) {
                    var loadingView = new CommonViews.Loading();
                    ConfigManager.regions.main.show(loadingView);

                    var fetchingConfig = ConfigManager.request("config:entity", id);
                    $.when(fetchingConfig).done(function (config) {
                        var configView;
                        if (config === undefined) {
                            configView = new View.MissingConfig();
                        } else {
                            configView = new View.Config({
                                model: config
                            });

                            configView.on("config:edit", function (model) {
                                ConfigManager.trigger("config:edit", model.get("id"));
                            });
                        }

                        ConfigManager.regions.main.show(configView);
                    });
                });
            }
        }
    });

    return ConfigManager.ConfigsApp.Show.Controller;
});