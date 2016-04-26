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

                groupsView.on("itemview:group:details", function(childView, model){

                    var configIds = model.get("mergerConfigIds");

                    var configNames = [];

                    for (var i = 0 ; i < configIds.length; i++) {
                        configNames.push(configsData.get(configIds[i]).get("name"));
                    }

                    model.set("configNames", configNames);

                    var view = new ConfigGroupManager.GroupsApp.Show.Group({
                        model: model,
                        asModal: true
                    });

                    var title = "Просмотр списка " + model.get("name");

                    view.on("show", function(){
                        this.$el.dialog({
                            modal: true,
                            title: title,
                            width: "auto"
                        });
                    });

                    ConfigGroupManager.dialogRegion.show(view);

                });

                groupPanelView.on("group:new", function() {
                    var newGroup = new ConfigGroupManager.Entities.Group();

                    var view = new List.Configs({
                        collection: configsData,
                        asModal: true
                    });

                    view.on("show", function(){
                         this.$el.dialog({
                             modal: true,
                             title: "Создать новую группу конфигов",
                             width: "auto"
                         });
                    });

                    view.on("group:create", function (data) {

                        newGroup.save(data, { success: function()
                            {
                                groupsData.add(newGroup);
                                ConfigGroupManager.dialogRegion.close();
                            }
                            }
                        );

                    });

                    ConfigGroupManager.dialogRegion.show(view);
                });

                groupsView.on("itemview:group:delete", function(childView, model){
                    model.destroy();
                });

                ConfigGroupManager.mainRegion.show(groupListLayout);
            });
        }
    }

});