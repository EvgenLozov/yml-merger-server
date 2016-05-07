ConfigGroupManager.module("GroupsApp.List", function(List, ConfigGroupManager,  Backbone, Marionette, $, _){

    List.Controller = {
        listGroups: function(){

            var groupListLayout = new List.Layout();
            var groupPanelView = new List.Panel();

            var fetchedGroups = ConfigGroupManager.request("group:entities");
            var fetchedConfigs = ConfigGroupManager.request("config:entities");

            $.when(fetchedGroups, fetchedConfigs).done(function(groupsData, configsData){

                var groupsView = new List.Groups({
                    collection: groupsData
                });

                groupListLayout.on("show", function(){
                    groupListLayout.panelRegion.show(groupPanelView);
                    groupListLayout.groupsRegion.show(groupsView);
                });

                groupsView.on("childview:group:edit", function(childView, model){

                    var view = new ConfigGroupManager.GroupsApp.Edit.Group({
                        model: model,
                        configs: configsData
                    });

                    view.on("form:submit", function(data){
                        if (model.save(data)){
                            childView.render();
                            view.trigger("dialog:close");
                        } else {
                            view.triggerMethod("form:data:invalid", model.validationError)
                        }
                    });

                    view.on("form:cancel", function () {
                        view.trigger("dialog:close");
                    });

                    ConfigGroupManager.regions.dialog.show(view);

                });

                groupsView.on("childview:group:delete", function(childView, model){
                    model.destroy();
                });

                groupPanelView.on("group:new", function() {
                    var newGroup = new ConfigGroupManager.Entities.Group();

                    var view = new ConfigGroupManager.GroupsApp.New.Group({
                        model: newGroup,
                        configs: configsData
                    });

                    newGroup.on("invalid", function(model, error) {
                        view.triggerMethod("form:data:invalid", newGroup.validationError);
                    });

                    view.on("form:submit", function (data) {
                        newGroup.save(data, { success: function()
                            {
                                groupsData.add(newGroup);
                                view.trigger("dialog:close");
                            }
                            }
                        );

                    });

                    view.on("form:cancel", function () {
                        view.trigger("dialog:close");
                    });

                    ConfigGroupManager.regions.dialog.show(view);
                });

                ConfigGroupManager.mainRegion.show(groupListLayout);
            });
        }
    }

});