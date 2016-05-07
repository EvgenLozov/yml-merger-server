define(["jquery", "underscore", "app", "../../../entities/replace/models",
        "tpl!apps/configs/common/templates/replace_item.tpl",
        "tpl!apps/configs/common/templates/replace_list.tpl",
        "backbone.syphon"],
function($, _, ConfigManager, ReplacesModel, replaceTpl, replaceListTpl) {

        var ReplaceView = Marionette.ItemView.extend({
            tagName : "tr",
            template: replaceTpl,

            events: {
                "click button.js-delete": "deleteClicked"
            },

            deleteClicked: function (e) {
                e.preventDefault();
                e.stopPropagation();
                this.trigger("replace:delete", this.model);
            }
        });

        var ReplacesView= Marionette.CompositeView.extend({
            template: replaceListTpl,
            childView: ReplaceView,
            childViewContainer: '#replacesTable',

            events: {
                "click button.addReplace": "addReplace"
            },

            addReplace: function(){
                var replacement = this.$el.find('#replacement').val();
                var wordsToReplace = this.$el.find('#wordsToReplace').val().split(",");
                wordsToReplace = wordsToReplace.filter(function(e){return e.trim()});

                _.each(wordsToReplace, function(word){
                    word.trim();
                });

                var replace = new ReplacesModel.Replace({
                    replacement : replacement,
                    wordsToReplace: wordsToReplace
                });

                this.collection.add(replace);

                this.$el.find('#replacement').val("");
                this.$el.find('#wordsToReplace').val("");
            }

        });

    return {
        ReplaceView: ReplaceView,
        ReplacesView: ReplacesView
    };
});
