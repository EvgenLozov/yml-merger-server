ConfigManager.module("ConfigsApp.Show", function(Show,  ConfigManager,  Backbone, Marionette, $, _){

    Show.Config = Marionette.ItemView.extend({
       template: "#config-view",

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

    Show.MissingConfig = Marionette.ItemView.extend({
        template: "#missing-config-view"
    });
});