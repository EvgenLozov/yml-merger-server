define(["jquery", "app",
        "apps/configs/common/missing_config",
        "apps/configs/edit/edit_view"],
    function($, ConfigManager, MissingConfigView, EditView){

        return {
            editConfig: function (id) {
                require(["entities/config/model"], function () {

                    var fetchingConfig = ConfigManager.request("config:entity", id);

                    $.when(fetchingConfig).done(function (config) {
                        var configView;
                        if (config === undefined) {
                            configView = new MissingConfigView();
                        } else {
                            configView = new EditView({
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