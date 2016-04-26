ConfigGroupManager.module("GroupsApp", function(GroupsApp, ConfigGroupManager,  Backbone, Marionette, $, _){

    GroupsApp.Router = Marionette.AppRouter.extend({
        appRoutes: {
            "groups" : "listGroups"
        }
    });

    var API = {
        listGroups: function(){
            GroupsApp.List.Controller.listGroups();
        }
    };

    ConfigGroupManager.on("groups:list", function(){
        ConfigGroupManager.navigate("groups");
        API.listGroups();

    });

    ConfigGroupManager.addInitializer(function(){
        new GroupsApp.Router({
            controller: API
        });
    });

});