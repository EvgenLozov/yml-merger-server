ConfigManager.module("ConfigsApp.Edit", function(Edit, ConfigManager,  Backbone, Marionette, $, _){
    Edit.Config = ConfigManager.ConfigsApp.Common.Views.Form.extend({
        initialize : function(){
            this.title = "Редактирование конфига: " + this.model.get("name");
            Edit.Config.__super__.initialize.apply(this, arguments);
        }
    });
});