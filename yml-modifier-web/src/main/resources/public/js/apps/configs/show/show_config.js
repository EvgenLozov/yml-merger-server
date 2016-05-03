define(["app",
        "tpl!apps/configs/show/templates/config_view.tpl",
        "tpl!apps/configs/show/templates/missing_config_view.tpl"],
function(ConfigManager, showTpl, missingTpl){
    ConfigManager.module("ConfigsApp.Show.View", function(View,  ConfigManager,  Backbone, Marionette, $, _){

        View.Config = Marionette.ItemView.extend({
            template: showTpl,

            events: {
                "click a.js-edit" : "editConfig",
                "click a.js-list-configs" : "listConfigsClicked"
            },

            editConfig: function(e){
                e.preventDefault();
                e.stopPropagation();
                this.trigger("config:edit", this.model);
            },

            listConfigsClicked: function(e){
                e.preventDefault();
                ConfigManager.trigger("configs:list");
            }
        });

        View.MissingConfig = Marionette.ItemView.extend({
            template: missingTpl
        });
    });

    return ConfigManager.ConfigsApp.Show.View;
});