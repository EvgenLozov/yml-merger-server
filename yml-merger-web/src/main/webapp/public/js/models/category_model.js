
"use strict";
APP.CategoryModel = Backbone.Model.extend({

    defaults: {
        checked : false,
        isSelectedToMove : false
    },

    checked: function(checked){
        if (checked == true && this.attributes.checked != true) {
            this.set('checked', true);
        }
        if (checked == false && this.attributes.checked != false) {
            this.set('checked', false);
        }
        console.log("Category with id " + this.attributes.id + " checked as " + checked);
    },

    selectToMove: function(selected){
        if (selected == true && this.attributes.isSelectedToMove != true) {
            this.set('isSelectedToMove', true);
        }
        if (selected == false && this.attributes.isSelectedToMove != false) {
            this.set('isSelectedToMove', false);
        }
    }
});

APP.CategoryCollection = Backbone.Collection.extend({

    model: APP.CategoryModel,

    initialize: function(models, options) {
        options || (options = {});
        if (options.configId) {
            this.configId = options.configId;
        };
        if (options.parentId) {
            this.parentId = options.parentId;
        };
    },

    setParentId : function(parentId){
        this.parentId = parentId;
    }

});