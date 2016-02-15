ConfigManager.module("ConfigsApp.Show", function(Show,  ConfigManager,  Backbone, Marionette, $, _){

    Show.Config = Marionette.ItemView.extend({
       template: "#config-view",

        events: {
            "click a.js-edit" : "editConfig"
        },

        editConfig: function(e){
            e.preventDefault();
            e.stopPropagation();
            this.trigger("config:edit", this.model);
        }
    });

    Show.MissingConfig = Marionette.ItemView.extend({
        template: "#missing-config-view"
    });
});