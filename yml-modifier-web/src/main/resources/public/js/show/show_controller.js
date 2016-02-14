ConfigManager.module("ConfigsApp.Show", function(Show,  ConfigManager,  Backbone, Marionette, $, _){

    Show.Controller = {
        showConfig : function(model){
            var configView = new Show.Config({
                model: model
            });

            ConfigManager.mainRegion.show(configView);
        }
    }

});