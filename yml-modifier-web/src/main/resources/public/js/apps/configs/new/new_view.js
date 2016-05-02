define(["app", "apps/configs/common/views"], function(ConfigManager, CommonViews) {
    ConfigManager.module("ConfigsApp.New.View", function (View, ConfigManager, Backbone, Marionette, $, _) {
        View.Config = CommonViews.Form.extend({
            initialize: function () {
                this.title = "Новый конфиг";
                View.Config.__super__.initialize.apply(this, arguments);
            }
        });
    });

    return ConfigManager.ConfigsApp.New.View;
});