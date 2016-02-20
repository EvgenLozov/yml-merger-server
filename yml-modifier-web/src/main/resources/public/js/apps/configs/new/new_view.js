ConfigManager.module("ConfigsApp.New", function(New, ConfigManager,  Backbone, Marionette, $, _){
    New.Config = ConfigManager.ConfigsApp.Common.Views.Form.extend({
        title: "Новый конфиг"
    });
});