ConfigGroupManager.module("GroupsApp.Common.Views", function(Views,  ConfigGroupManager,  Backbone, Marionette, $, _){
    Views.Form = Marionette.ItemView.extend({
        template: "#group-form",

        events: {
            "click button.js-submit" : "submitClicked",
            "click button.js-cancel" : "cancelClicked"
        },

        submitClicked: function(e){
            e.preventDefault();
            e.stopPropagation();

            var data = {};

            data.name = this.$el.find("#group-name").val();
            data.epochePeriod = this.$el.find("#group-epochePeriod").val() * 1000 * 60;

            var ids = [];

            this.$el.find('#configs-content input').each(function(){
                if ($(this).prop('checked'))
                    ids.push($(this).attr('configId'));
            });

            data.mergerConfigIds = ids;

            this.trigger("form:submit", data);
        },

        cancelClicked: function(e){
            e.preventDefault();
            this.trigger("form:cancel");
        },

        templateHelpers: function () {
            var configs = this.configs.models;

            return {
                configs: configs
            };
        },

        onRender: function(){
            var checkedConfigs = this.model.get('mergerConfigIds');

            this.$el.find('#configs-content input').each(function(){
                var inputEl = $(this);
                var inputId = inputEl.attr('configId');
                if (checkedConfigs.includes(inputId)){
                    inputEl.prop('checked', true);
                }
            });
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
                var $controlGroup1 = $view.find("#group-" + key).parent();
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