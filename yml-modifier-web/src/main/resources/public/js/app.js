var ConfigManager = new Marionette.Application();

ConfigManager.addRegions({
    mainRegion: "#main-region",
    dialogRegion: "#dialog-region"
});

ConfigManager.navigate = function(route, options){
    options || (options = {});
    Backbone.history.navigate(route, options);
};

ConfigManager.getCurrentRoute = function(){
    return Backbone.history.fragment
};


ConfigManager.on("initialize:after",  function(){
    if(Backbone.history) {
        Backbone.history.start();

        if (this.getCurrentRoute() === "") {
            ConfigManager.trigger("configs:list");
        }
    }

});