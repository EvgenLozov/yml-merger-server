ConfigManager.module("ConfigsApp.Edit", function(Edit,  ConfigManager,  Backbone, Marionette, $, _){
    Edit.Controller = {
        editConfig: function(id){
            var loadingView = new ConfigManager.Common.Views.Loading({title: "Loading config with id: " + id});
            ConfigManager.mainRegion.show(loadingView);

            var fetchingConfig = ConfigManager.request("config:entity", id);
            $.when(fetchingConfig).done(function(config){
                var configView;
                if (config === undefined){
                    configView = new ConfigManager.ConfigsApp.Show.MissingConfig();
                } else {
                    configView = new Edit.Config({
                        model: config
                    });
                }

                configView.on("form:submit", function(data){
                    config.save(data);
                    ConfigManager.trigger("config:show", config.get("id"));

                });

                ConfigManager.mainRegion.show(configView);
            });
        }
    }
});