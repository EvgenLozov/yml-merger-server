ConfigManager.module("ConfigsApp.Common.Views", function(Views,  ConfigManager,  Backbone, Marionette, $, _){
    Views.Form = Marionette.ItemView.extend({
        template: "#config-form",

        events: {
            "click button.js-submit" : "submitClicked",
            "click button.js-cancel" : "cancelClicked",
            "click button.addReplace": "addReplace"
        },

        initialize: function(){
            this.replaces = new ConfigManager.Entities.ReplaceCollection(this.model.get("replaces"));
            this.replacesView = new Views.Replaces({collection: this.replaces});
        },

        addReplace: function(){
            var replacement = this.$el.find('#replacement').val();
            var wordsToReplace = this.$el.find('#wordsToReplace').val().split(",");
            wordsToReplace = wordsToReplace.filter(function(e){return e.trim()});

            _.each(wordsToReplace, function(word){
                word.trim();
            });

            var replace = new ConfigManager.Entities.Replace();
            replace.set({replacement : replacement, wordsToReplace: wordsToReplace});

            this.replaces.add(replace);

            this.$el.find('#replacement').val("");
            this.$el.find('#wordsToReplace').val("");
        },

        submitClicked: function(e){
            e.preventDefault();
            var data = Backbone.Syphon.serialize(this);
            data.template = this.$el.find("textarea").val();
            data.epochePeriod = this.$el.find("#config-epochePeriod").val()*3600;

            data.limitSize = this.$el.find("#config-limitSize").val() * 1024;

            data.replaces = Views.Util.Replaces.parseReplaces(this.$el.find("#replacesTable").find('tbody').children());

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

            this.$el.find("#replacesTable").append(this.replacesView.$el);
            this.replacesView.render();
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


    Views.Replace = Marionette.ItemView.extend({
        tagName : "tr",

        events: {
            "click a.delete": "destroy"
        },

        destroy: function (event) {
            event.preventDefault();
            event.stopPropagation();

            this.model.destroy();
        },

        render: function () {
            this.$el.html(_.template($('#replaceTemplate').html(), this.model.toJSON()));
            return this;
        }
    });

    Views.Replaces = Marionette.CollectionView.extend({
        tagName : "tbody",
        itemView: Views.Replace
    });

});
