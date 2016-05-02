define(["app", "backbone.nested"], function(ConfigManager){
    ConfigManager.module("Entities", function(Entities, ConfigManager,  Backbone, Marionette, $, _){

        Entities.Config = Backbone.NestedAttributesModel.extend({
            urlRoot: "configs",

            relations: [
                {
                    key:  'replaces',
                    relatedModel: function () { return Entities.Replace }
                }
            ],

            defaults: {
                "categoryIdPrefix": "",
                "encoding": "UTF-8",
                "filesCount": 1,
                "limitSize": 0,
                "inputFile": "",
                "inputFileURL": "",
                "modifyCategoryId": false,
                "modifyDescription": false,
                "modifyOfferId": false,
                "name": "",
                "offerIdPrefix": "",
                "period": 1,
                "psw": "",
                "removedCategoryId": "",
                "template": "",
                "time": "00:00",
                "user": "",
                "epochePeriod" : ""
            },

            validate: function(attrs, options){
                var errors = {};
                if (! attrs.name){
                    errors.name = "Укажите название"
                }

                if (! attrs.encoding){
                    errors.encoding = "Укажите кодировку"
                }

                if (! attrs.inputFile && !attrs.inputFileURL){
                    errors.inputFile = "Укажите путь к исходному файлу";
                    errors.inputFileURL = "или ссылку на исходный файл"
                }

                if (! attrs.filesCount){
                    errors.filesCount = ""
                } else if (attrs.filesCount < 0 )
                    errors.filesCount = "Укажите число больше 0";



                if (!_.isEmpty(errors))
                    return errors;
            }
        });

        Entities.Configs = Backbone.Collection.extend({
            model: Entities.Config,
            url: "configs"
        });

        Entities.Replace = Backbone.NestedAttributesModel.extend({});

        Entities.ReplaceCollection = Backbone.Collection.extend({
            model: Entities.Replace
        });

        var API = {
            getConfigEntities: function(){
                var configs = new Entities.Configs();
                var defer = $.Deferred();

                setTimeout(function(){
                    configs.fetch({
                        success: function (data) {
                            defer.resolve(data);
                        }
                    });
                }, 300);

                return defer.promise();
            },

            getConfigEntity: function(id){
                var config = new Entities.Config({id: id});
                var defer = $.Deferred();

                setTimeout(function(){
                    config.fetch({
                        success: function(data){
                            defer.resolve(data);
                        },
                        error: function(data){
                            defer.resolve(undefined);
                        }
                    });
                }, 300);

                return defer.promise();
            }
        };

        ConfigManager.reqres.setHandler("config:entities", function(){
            return API.getConfigEntities();
        });

        ConfigManager.reqres.setHandler("config:entity", function(id){
            return API.getConfigEntity(id);
        });

        ConfigManager.reqres.setHandler("config:entity:new", function(){
            return new Entities.Config();
        });

    });
});