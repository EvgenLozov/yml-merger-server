ConfigManager.module("ConfigsApp.Common.Views", function(Views,  ConfigManager,  Backbone, Marionette, $, _){
    Views.Form = Marionette.ItemView.extend({
        template: "#config-form",

        events: {
            "click button.js-submit" : "submitClicked",
            "click button.js-cancel" : "cancelClicked"
        },

        submitClicked: function(e){
            e.preventDefault();
            var data = Backbone.Syphon.serialize(this);
            data.template = this.$el.find("textarea").val();
            this.trigger("form:submit", data);
        },

        cancelClicked: function(e){
            e.preventDefault();
            this.trigger("form:cancel");
        },

        onRender: function(){
            if (!this.options.asModal){
                var $title = $("<h3>", { text: this.title });
                this.$el.prepend($title);
            }
        },

        onShow : function(){
            if (this.options.asModal) {
                this.$el.dialog({
                    modal: true,
                    title: this.title,
                    width: 700,
                    height: 700
                });
            }

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