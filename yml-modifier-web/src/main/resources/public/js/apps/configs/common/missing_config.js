define(["marionette",
        "tpl!apps/configs/common/templates/missing_config_view.tpl"],
    function(Marionette, missingTpl){

        return Marionette.ItemView.extend({
            template: missingTpl
        });

});