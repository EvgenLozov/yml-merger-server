ConfigManager.module("ConfigsApp.Edit", function(Edit,  ConfigManager,  Backbone, Marionette, $, _){
    Edit.Controller = {
        editConfig: function(id){
            var loadingView = new ConfigManager.Common.Views.Loading();
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
                    if (config.save(data)){
                        ConfigManager.trigger("config:show", config.get("id"));
                    } else {
                       configView.triggerMethod("form:data:invalid", config.validationError);
                    }

                });

                ConfigManager.mainRegion.show(configView);
            });
        }
    }
});