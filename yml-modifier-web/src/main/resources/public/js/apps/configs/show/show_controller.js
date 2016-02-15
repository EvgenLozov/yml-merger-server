ConfigManager.module("ConfigsApp.Show", function(Show,  ConfigManager,  Backbone, Marionette, $, _){

    Show.Controller = {
        showConfig : function(id){
            var fetchingConfig = ConfigManager.request("config:entity", id);
            $.when(fetchingConfig).done(function(config){
                var configView;
                if (config === undefined){
                    configView = new Show.MissingConfig();
                } else {
                    configView = new Show.Config({
                        model: config
                    });
                }

                ConfigManager.mainRegion.show(configView);
            });
        }
    }

});