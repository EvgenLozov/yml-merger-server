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

ConfigGroupManager.on("before:start", function(){
  var RegionContainer = Marionette.LayoutView.extend({
    el: "#app-container",

    regions: {
      main: "#main-region",
      dialog: "#dialog-region"
    }
  });

    ConfigGroupManager.regions = new RegionContainer();
    ConfigGroupManager.regions.dialog.onShow = function(view){
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
      width: "auto",
      close: function(e, ui){
        closeDialog();
      }
    });
  };
});

ConfigGroupManager.on("start",  function(){
    if(Backbone.history) {
        Backbone.history.start();

        if (this.getCurrentRoute() === "") {
            ConfigGroupManager.trigger("groups:list");
        }
    }

});