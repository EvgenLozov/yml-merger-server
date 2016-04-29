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

ConfigManager.on("before:start", function(){
    var RegionContainer = Marionette.LayoutView.extend({
        el: "#app-container",

        regions: {
            main: "#main-region",
            dialog: "#dialog-region"
        }
    });

    ConfigManager.regions = new RegionContainer();
    ConfigManager.regions.dialog.onShow = function(view){
        var self = this;
        var closeDialog = function(){
            self.stopListening();
            self.empty();
            self.$el.dialog("destroy");
        };

        this.listenTo(view, "dialog:close", closeDialog);

        this.$el.dialog({
            modal: true,
            title: view.title,
            width: 700,
            height: 700,
            close: function(e, ui){
                closeDialog();
            }
        });
    };
});

ConfigManager.on("start",  function(){
    if(Backbone.history) {
        Backbone.history.start();

        if (this.getCurrentRoute() === "") {
            ConfigManager.trigger("configs:list");
        }
    }

});