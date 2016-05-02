define(["app", "apps/configs/common/views"], function(ConfigManager, CommonViews){
    ConfigManager.module("ConfigsApp.Edit.View", function(View, ConfigManager,  Backbone, Marionette, $, _) {
        View.Config = CommonViews.Form.extend({
            initialize: function () {
                this.title = "Редактирование конфига: " + this.model.get("name");
                View.Config.__super__.initialize.apply(this, arguments);
            },

            onRender: function () {
                if (this.options.generateTitle) {
                    var $title = $("<h3>", {text: this.title});
                    this.$el.prepend($title);
                }
            }
        });
    });

    return ConfigManager.ConfigsApp.Edit.View;
});