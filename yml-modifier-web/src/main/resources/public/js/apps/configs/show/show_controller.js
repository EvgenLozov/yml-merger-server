ConfigManager.module("ConfigsApp.Show", function(Show,  ConfigManager,  Backbone, Marionette, $, _){

    Show.Controller = {
        showConfig : function(id){
            var loadingView = new ConfigManager.Common.Views.Loading();
            ConfigManager.regions.main.show(loadingView);

            var fetchingConfig = ConfigManager.request("config:entity", id);
            $.when(fetchingConfig).done(function(config){
                var configView;
                if (config === undefined){
                    configView = new Show.MissingConfig();
                } else {
                    configView = new Show.Config({
                        model: config
                    });

                    configView.on("config:edit", function(model){
                        ConfigManager.trigger("config:edit", model.get("id"));
                    });
                }

                ConfigManager.regions.main.show(configView);
            });
        }
    }

});