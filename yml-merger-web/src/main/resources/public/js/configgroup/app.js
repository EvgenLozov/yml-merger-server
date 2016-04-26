var ConfigGroupManager = new Marionette.Application();

ConfigGroupManager.addRegions({
    mainRegion: "#main-region",
    dialogRegion: "#dialog-region"
});

ConfigGroupManager.navigate = function(route, options){
    options || (options = {});
    Backbone.history.navigate(route, options);
};

ConfigGroupManager.getCurrentRoute = function(){
    return Backbone.history.fragment
};


ConfigGroupManager.on("initialize:after",  function(){
    if(Backbone.history) {
        Backbone.history.start();

        if (this.getCurrentRoute() === "") {
            ConfigGroupManager.trigger("groups:list");
        }
    }

});