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

                groupsView.on("itemview:group:edit", function(childView, model){

                    var view = new ConfigGroupManager.GroupsApp.Edit.Group({
                        model: model,
                        asModal: true,
                        configs: configsData
                    });

                    view.on("show", function(){
                        this.$el.dialog({
                            modal: true,
                            width: "auto"
                        });
                    });

                    view.on("form:submit", function(data){
                        if (model.save(data)){
                            childView.render();
                            ConfigGroupManager.dialogRegion.close();
                        } else {
                            view.triggerMethod("form:data:invalid", model.validationError)
                        }
                    });

                    view.on("form:cancel", function () {
                        ConfigGroupManager.dialogRegion.close();
                    });

                    ConfigGroupManager.dialogRegion.show(view);

                });

                groupsView.on("itemview:group:delete", function(childView, model){
                    model.destroy();
                });

                groupPanelView.on("group:new", function() {
                    var newGroup = new ConfigGroupManager.Entities.Group();

                    var view = new ConfigGroupManager.GroupsApp.New.Group({
                        model: newGroup,
                        asModal: true,
                        configs: configsData
                    });

                    view.on("show", function(){
                         this.$el.dialog({
                             modal: true,
                             width: "auto"
                         });
                    });

                    view.on("form:submit", function (data) {
                        newGroup.save(data, { success: function()
                            {
                                groupsData.add(newGroup);
                                ConfigGroupManager.dialogRegion.close();
                            }
                            }
                        );

                    });

                    view.on("form:cancel", function () {
                        ConfigGroupManager.dialogRegion.close();
                    });

                    ConfigGroupManager.dialogRegion.show(view);
                });

                ConfigGroupManager.mainRegion.show(groupListLayout);
            });
        }
    }

});