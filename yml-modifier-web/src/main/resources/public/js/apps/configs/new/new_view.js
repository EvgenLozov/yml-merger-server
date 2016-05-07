define(["app", "../common/config_form"],
function(ConfigManager, ConfigFormView) {

    var NewConfigView = ConfigFormView.extend({
            initialize: function () {
                this.title = "Новый конфиг";
                NewConfigView.__super__.initialize.apply(this, arguments);
            }
        });

    return NewConfigView;
});