define(["jquery", "backbone", "app", "../replace/models", "backbone.nested"],
function($, Backbone, ConfigManager, ReplacesModel){

    var Config = Backbone.NestedAttributesModel.extend({
        urlRoot: "configs",

        relations: [
            {
                key:  'replaces',
                relatedModel: function () { return ReplacesModel.Replace }
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

    var API = {
        getConfigEntity: function(id){
            var config = new Config({id: id});
            var defer = $.Deferred();

            config.fetch({
                success: function(data){
                    defer.resolve(data);
                },
                error: function(data){
                    defer.resolve(undefined);
                }
            });

            return defer.promise();
        }
    };

    ConfigManager.reqres.setHandler("config:entity", function(id){
        return API.getConfigEntity(id);
    });

    ConfigManager.reqres.setHandler("config:entity:new", function(){
        return new Config();
    });

    return Config;
});