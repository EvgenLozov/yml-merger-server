ConfigManager.module("ConfigsApp.New", function(New, ConfigManager,  Backbone, Marionette, $, _){
    New.Config = ConfigManager.ConfigsApp.Common.Views.Form.extend({
        initialize : function(){
            this.title = "Новый конфиг";
            New.Config.__super__.initialize.apply(this, arguments);
        }
    });
});