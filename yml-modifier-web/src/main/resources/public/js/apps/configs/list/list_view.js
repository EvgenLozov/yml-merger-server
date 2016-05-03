define(["app",
        "tpl!apps/configs/list/templates/config_list.tpl",
        "tpl!apps/configs/list/templates/config_list_item.tpl",
        "tpl!apps/configs/list/templates/config_list_layout.tpl",
        "tpl!apps/configs/list/templates/config_list_panel.tpl"],
function(ConfigManager, listTpl, listItemTpl, listLayoutTpl, listPanelTpl){
    ConfigManager.module("ConfigsApp.List.View", function(View, ConfigManager,  Backbone, Marionette, $, _){
        View.Config = Marionette.ItemView.extend({
            tagName: "tr",
            template: listItemTpl,

            triggers: {
                "click button.js-delete" : "config:delete",
                "click a.js-edit" : "config:edit",
                "click a.js-show" : "config:show",
                "click button.js-modify" : "config:modify",
                "click button.js-copy" : "config:copy"
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

        View.Configs = Marionette.CompositeView.extend({
            tagName: "table",
            className: "table table-hover",
            template: listTpl,
            childView: View.Config,
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

        View.Layout = Marionette.LayoutView.extend({
            template: listLayoutTpl,

            regions: {
                panelRegion: "#panel-region",
                configsRegion: "#configs-region"
            }
        });

        View.Panel = Marionette.ItemView.extend({
            template: listPanelTpl,

            triggers: {
                "click button.js-new" : "config:new"
            }
        });

    });

    return ConfigManager.ConfigsApp.List.View;
});