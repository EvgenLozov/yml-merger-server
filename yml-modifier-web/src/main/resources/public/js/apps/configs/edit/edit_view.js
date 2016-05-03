define(["jquery", "app", "../common/config_form"], function($, ConfigManager, ConfigFormView){

        var EditConfigView = ConfigFormView.extend({
            initialize: function () {
                this.title = "Редактирование конфига: " + this.model.get("name");
                EditConfigView.__super__.initialize.apply(this, arguments);
            },

            onRender: function () {
                if (this.options.generateTitle) {
                    var $title = $("<h3>", {text: this.title});
                    this.$el.prepend($title);
                }
            }
        });

    return EditConfigView;
});