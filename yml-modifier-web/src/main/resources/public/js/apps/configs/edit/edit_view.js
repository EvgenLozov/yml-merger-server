ConfigManager.module("ConfigsApp.Edit", function(Edit,  ConfigManager,  Backbone, Marionette, $, _){
    Edit.Config = Marionette.ItemView.extend({
        template: "#config-form",

        initialize : function(){
            this.title = "Редактирование  " + this.model.get("name");
        },

        events: {
            "click button.js-submit" : "submitClicked"
        },

        submitClicked: function(e){
            e.preventDefault();
            var data = Backbone.Syphon.serialize(this);
            this.trigger("form:submit", data);
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
                    width: "auto"
                });
            }

        },

        onFormDataInvalid: function(errors){
            var $view = this.$el;

            var clearFormErrors = function(){
                var $form = $view.find("form");
                $form.find(".help-inline.error").each(function(){
                    $(this).remove();
                });
                $form.find(".control-group.error").each(function(){
                    $(this).removeClass("error");
                });
            };

            var markErrors = function(value, key){
                var $controlGroup = $view.find("#config-" + key).parent();
                var $errorEl = $("<span>", {class: "help-inline error", text: value});
                $controlGroup.append($errorEl).addClass("error");
            };

            clearFormErrors();
            _.each(errors, markErrors);
        }
    });
});