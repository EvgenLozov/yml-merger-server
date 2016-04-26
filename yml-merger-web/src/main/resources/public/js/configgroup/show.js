ConfigGroupManager.module("GroupsApp.Show", function(Show, ConfigGroupManager,  Backbone, Marionette, $, _) {

    Show.Group = Marionette.ItemView.extend({
        template: "#group-show"
    });

});