define(["app", "apps/configs/common/replaces_view",
        "tpl!apps/configs/common/templates/config_form.tpl",
        "backbone.syphon"],
function(ConfigManager, ReplacesView, formTpl){

    ConfigManager.module("ConfigsApp.Common.Views", function(Views,  ConfigManager,  Backbone, Marionette, $, _){

        Views.Form = Marionette.LayoutView.extend({
            template: formTpl,

            events: {
                "click button.js-submit": "submitClicked"
            },

            triggers: {
                "click button.js-cancel": "form:cancel"
            },

            regions: {
                'replaces-region' : "#replacesView"
            },

            onBeforeShow: function() {
                var models = this.model.get('replaces');
                this.replacesView = new ReplacesView.Replaces({collection: models});
                this.replacesView.on("childview:replace:delete", function(childview, model){
                    model.destroy();
                });

                this.showChildView('replaces-region', this.replacesView);
            },

            submitClicked: function(e){
                e.preventDefault();

                var data = Backbone.Syphon.serialize(this);
                data.template = this.$el.find("textarea").val();
                data.epochePeriod = this.$el.find("#config-epochePeriod").val()*3600;
                data.limitSize = this.$el.find("#config-limitSize").val() * 1024;

                this.trigger("form:submit", data);
            },

            onFormDataInvalid: function(errors){
                var $view = this.$el;

                var clearFormErrors = function(){
                    var $form = $view.find("form");
                    $form.find(".help-block").each(function(){
                        $(this).remove();
                    });
                    $form.find(".form-group.has-error").each(function(){
                        $(this).removeClass("has-error");
                    });
                };

                var markErrorSubmit = function(){
                    var $submitElDiv = $view.find(".js-submit").closest("div .col-sm-10");
                    var $submitElError = $("<span>", {class: "help-block", text: "Исправьте ошибки заполнения формы"});
                    $submitElDiv.append($submitElError).addClass("has-error");
                };

                var markErrors = function(value, key){
                    var $controlGroup1 = $view.find("#config-" + key).parent();
                    var $controlGroup = $controlGroup1.closest("div .form-group");
                    var $errorEl = $("<span>", {class: "help-block", text: value});
                    $controlGroup.append($errorEl).addClass("has-error");
                };

                clearFormErrors();
                markErrorSubmit();
                _.each(errors, markErrors);
            }
        });
    });

    return ConfigManager.ConfigsApp.Common.Views;
});
