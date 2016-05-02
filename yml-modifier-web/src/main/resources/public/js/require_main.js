requirejs.config({
    baseUrl: "js",
    paths: {
        marionette: "vendor/backbone.marionette",
        backbone: "vendor/backbone",
        "backbone.nested" : "vendor/backbone.nested",
        "backbone.syphon" : "vendor/backbone.syphon",
        json2: "vendor/json2",
        jquery: "vendor/jquery",
        "jquery-ui": "vendor/jquery-ui",
        text: "vendor/text",
        tpl: "vendor/underscore-tpl",
        underscore: "vendor/underscore",
        spin: "vendor/spin",
        "spin.jquery" : "vendor/spin.jquery"
    },

    shim: {
        underscore: {
            exports: "_"
        },
        backbone: {
            deps: ["jquery", "underscore", "json2"],
            exports : "Backbone"
        },
        "backbone.nested" :{
            deps: ["backbone"]
        },
        "backbone.syphon" : ["backbone"],
        marionette: {
            deps: ["backbone"],
            exports : "Marionette"
        },
        "jquery-ui" : ["jquery"],
        tpl: ["text"],
        "spin.jquery" : ["spin", "jquery"]
    }
});

require(["app"], function(ConfigManager) {
    ConfigManager.start();
});