ConfigManager.module("ConfigsApp.List", function(List, ConfigManager,  Backbone, Marionette, $, _){
    List.Config = Marionette.ItemView.extend({
        tagName: "tr",
        template: "#config-list-item",

        events: {
            "click button.js-delete" : "deleteClicked",
            "click button.js-modify" : "modifyClicked",
            "click button.js-copy" : "copyClicked",
            "click a.js-show" : "showConfig",
            "click a.js-edit" : "editConfig"
        },

        deleteClicked: function(e){
            e.stopPropagation();
            this.trigger("config:delete", this.model);
        },

        modifyClicked: function(e){
            e.stopPropagation();
            this.trigger("config:modify", this.model);
        },

        copyClicked: function(e){
            e.stopPropagation();
            this.trigger("config:copy", this.model);
        },

        showConfig: function(e){
            e.preventDefault();
            e.stopPropagation();
            this.trigger("config:show", this.model);
        },

        editConfig: function(e){
            e.preventDefault();
            e.stopPropagation();
            this.trigger("config:edit", this.model);
        },

        flash: function(cssClass){
            var $view = this.$el;
            $view.hide().toggleClass(cssClass).fadeIn(800, function(){
                setTimeout(function(){
                    $view.toggleClass(cssClass)
                }, 500);
            });
        },

        remove: function(){
            var self = this;
            this.$el.fadeOut(function(){
                Marionette.ItemView.prototype.remove.call(self);
            });
        }

    });

    List.Configs = Marionette.CompositeView.extend({
        tagName: "table",
        className: "table table-hover",
        template: "#config-list",
        childView: List.Config,
        childViewContainer: "tbody",

        initialize: function(){
            this.listenTo(this.collection, "reset", function(){
                this.appendHtml = function(collectionView, itemView, index){
                    collectionView.$el.append(itemView.el);
                }
            });
        },

        onCompositeCollectionRendered: function(){
            this.appendHtml = function(collectionView, itemView, index){
                collectionView.$el.prepend(itemView.el);
            }
        }
    });

    List.Layout = Marionette.LayoutView.extend({
        template: "#config-list-layout",

        regions: {
            panelRegion: "#panel-region",
            configsRegion: "#configs-region"
        }
    });

    List.Panel = Marionette.ItemView.extend({
        template: "#config-list-panel",

        triggers: {
            "click button.js-new" : "config:new"
        }
    });

});