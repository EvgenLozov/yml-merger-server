define(["jquery", "app", "apps/configs/show/show_config", "apps/configs/common/missing_config"],
function($, ConfigManager, ShowView, MissingView){

        return {
            showConfig: function (id) {
                require(["entities/config/model"], function () {
                    var fetchingConfig = ConfigManager.request("config:entity", id);
                    $.when(fetchingConfig).done(function (config) {
                        var configView;
                        if (config === undefined) {
                            configView = new MissingView();
                        } else {
                            configView = new ShowView({
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