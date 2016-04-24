ConfigManager.module("ConfigsApp.Common.Views.Util", function(Util, ConfigManager,  Backbone, Marionette, $, _){


    Util.Replaces = {
        parseReplaces: function (rows) {
            var replaces = [];

            _.each(rows, function (row) {
                var replacement = row.children[1].textContent.trim();
                var wordsToReplace = row.children[0].textContent.trim().split(",");

                if (replacement == "[Пустая строка]")
                    replaces.push({replacement: "", wordsToReplace: wordsToReplace});
                else
                    replaces.push({replacement: replacement, wordsToReplace: wordsToReplace});
            });

            return replaces;
        }
    }

});