ConfigManager.module("ConfigsApp.Edit", function(Edit, ConfigManager,  Backbone, Marionette, $, _){
    Edit.Config = ConfigManager.ConfigsApp.Common.Views.Form.extend({
        initialize : function(){
            this.title = "Редактирование  " + this.model.get("name");
        }
    });
});