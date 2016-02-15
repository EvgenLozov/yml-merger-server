ConfigManager.module("ConfigsApp.List", function(List, ConfigManager,  Backbone, Marionette, $, _){
    List.Config = Marionette.ItemView.extend({
        tagName: "tr",
        template: "#config-list-item",

        events: {
            "click button.js-delete" : "deleteClicked",
            "click a.js-show" : "showConfig"
        },

        deleteClicked: function(e){
            e.stopPropagation();
            this.trigger("config:delete", this.model);
        },

        showConfig: function(e){
            e.preventDefault();
            e.stopPropagation();
            this.trigger("config:show", this.model);
        }

    });

    List.Configs = Marionette.CollectionView.extend({
        tagName: "table",
        className: "table table-hover",
        itemView: List.Config
    });
});