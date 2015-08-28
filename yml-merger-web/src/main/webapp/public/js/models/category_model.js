
"use strict";
APP.CategoryModel = Backbone.Model.extend({

    defaults: {
        checked : false
    },

    checked: function(checked){
        if (checked == true && this.attributes.checked != true) {
            this.set('checked', true);
        }
        if (checked == false && this.attributes.checked != false) {
            this.set('checked', false);
        }
        console.log("Category with id " + this.attributes.id + " checked as " + checked);
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
    },

    url: function(){
        return "config/" + this.configId + "/categories/" + this.parentId;
    }

});