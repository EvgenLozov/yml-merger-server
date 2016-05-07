define(["marionette", "app",
        "tpl!apps/configs/show/templates/config_view.tpl"],
function(Marionette, ConfigManager, showTpl){

        var Config = Marionette.ItemView.extend({
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

    return Config;
});