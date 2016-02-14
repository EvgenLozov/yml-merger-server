ConfigManager.module("ConfigsApp.Show", function(Show,  ConfigManager,  Backbone, Marionette, $, _){

    Show.Config = Marionette.ItemView.extend({
       template: "#config-view"
    });

});