ConfigGroupManager.module("GroupsApp.List", function(List, ConfigGroupManager,  Backbone, Marionette, $, _) {

    List.Group = Marionette.ItemView.extend({
        tagName: "tr",
        template: "#group-list-item",

        events: {
            "click button.js-delete" : "deleteClicked",
            "click button.js-edit" : "editGroup"
        },

        editGroup: function(e){
            e.preventDefault();
            e.stopPropagation();
            this.trigger("group:edit", this.model);
        },

        deleteClicked: function(e){
            e.preventDefault();
            e.stopPropagation();
            this.trigger("group:delete", this.model);
        }
    });

    List.Groups = Marionette.CompositeView.extend({
        tagName: "table",
        className: "table table-hover",
        template: "#group-list",
        childView: List.Group,
        childViewContainer: "tbody"
    });

    List.Layout = Marionette.LayoutView.extend({
        template: "#group-list-layout",

        regions: {
            panelRegion: "#panel-region",
            groupsRegion: "#groups-region"
        }
    });

    List.Panel = Marionette.ItemView.extend({
        template: "#group-list-panel",

        triggers: {
            "click button.js-new" : "group:new"
        }
    });

});