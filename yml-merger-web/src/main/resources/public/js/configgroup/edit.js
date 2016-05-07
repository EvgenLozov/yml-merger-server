ConfigGroupManager.module("GroupsApp.Edit", function(Edit, ConfigGroupManager,  Backbone, Marionette, $, _){
    Edit.Group = ConfigGroupManager.GroupsApp.Common.Views.Form.extend({
        initialize : function(options){
            this.title = "Редактирование группы: " + this.model.get("name");
            this.configs = options.configs || {};
        }
    });
});