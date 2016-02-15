ConfigManager.module("ConfigsApp.Edit", function(Edit,  ConfigManager,  Backbone, Marionette, $, _){
    Edit.Config = Marionette.ItemView.extend({
        template: "#config-form",

        events: {
            "click button.js-submit" : "submitClicked"
        },

        submitClicked: function(e){
            e.preventDefault();
            var data = Backbone.Syphon.serialize(this);
            this.trigger("form:submit", data);
        }
    });
});