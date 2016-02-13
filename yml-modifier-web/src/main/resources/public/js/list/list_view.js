ConfigManager.module("ConfigsApp.List", function(List, ConfigManager,  Backbone, Marionette, $, _){
    List.Config = Marionette.ItemView.extend({
        tagName: "tr",
        template: "#config-list-item"
    });

    List.Configs = Marionette.CollectionView.extend({
        tagName: "table",
        className: "table table-hover",
        itemView: List.Config
    });
});