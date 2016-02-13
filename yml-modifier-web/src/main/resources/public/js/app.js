var ConfigManager = new Marionette.Application();

ConfigManager.addRegions({
    mainRegion: "#main-region"
});

ConfigManager.on("initialize:after",  function(){
    ConfigManager.ConfigsApp.List.Controller.listConfigs();
});