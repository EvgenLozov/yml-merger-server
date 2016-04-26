ConfigGroupManager.module("GroupsApp.List", function(List, ConfigGroupManager,  Backbone, Marionette, $, _) {

    List.Config = Marionette.ItemView.extend({
        tagName: "tr",
        template: "#config-list-item"
    });

    List.Configs = Marionette.CompositeView.extend({
        template: "#config-list",
        itemView : List.Config,
        itemViewContainer: "#configs-content",

        events: {
            "click button.js-submit" : "createGroup"
        },

        createGroup: function(e){
            e.preventDefault();
            e.stopPropagation();

            var data = {};

            data.name = this.$el.find("#group-name").val();
            data.epochePeriod = this.$el.find("#epochePeriod").val() * 1000 * 60;

            var ids = [];

            this.$el.find('#configs-content input').each(function(){
                if ($(this).prop('checked'))
                    ids.push($(this).attr('configId'));
            });

            data.mergerConfigIds = ids;

            this.trigger("group:create", data);
        }
    });

});