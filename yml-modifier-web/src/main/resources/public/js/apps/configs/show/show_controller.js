ConfigManager.module("ConfigsApp.Show", function(Show,  ConfigManager,  Backbone, Marionette, $, _){

    Show.Controller = {
        showConfig : function(id){
            var configs = ConfigManager.request("config:entities");
            var model = configs.get(id);
            var configView;
            if (model === undefined){
                configView = new Show.MissingConfig();
            } else {
                configView = new Show.Config({
                    model: model
                });
            }

            ConfigManager.mainRegion.show(configView);
        }
    }

});