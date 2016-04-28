ConfigGroupManager.module("GroupsApp.New", function(New, ConfigGroupManager,  Backbone, Marionette, $, _){
    New.Group = ConfigGroupManager.GroupsApp.Common.Views.Form.extend({
        initialize: function(options){
            this.title = "Новая группа";
            this.configs = options.configs || {};
        }
    });
});