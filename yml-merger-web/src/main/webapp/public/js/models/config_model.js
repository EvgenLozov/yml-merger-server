
"use strict";
APP.ConfigModel = Backbone.Model.extend({

  defaults: {
    "name" : "Конфиг " + getCurrentDate(),
    "user" : "",
    "psw" : "",
    "urls" : [ ],
    "files" : [ ],
    "encoding" : "utf-8",
    "currency" : "RUR",
    "outputFile" : "",
    "categoryIds" : [],
    "oldPrice" : 0
  },


  urlRoot: "/configs",

  validate: function (attrs) {
    var errors = {};

    if (!attrs.name || !attrs.name.trim()) errors.name = "Укажите название";
    if (!attrs.user || !attrs.user.trim()) errors.user = "Укажите логин для ApiShops";
    if (!attrs.psw || !attrs.psw.trim()) errors.psw = "Укажите пароль для ApiShops";
    if (!attrs.encoding || !attrs.encoding.trim()) errors.encoding = "Укажите кодировку";
    if (!attrs.oldPrice || attrs.oldPrice <0 ) errors.oldPrice = "Наценка для старой цены должна быть больше 0";

    if (!_.isEmpty(errors)) {
      return errors;
    }
  }
});

APP.ConfigCollection = Backbone.Collection.extend({

  model: APP.ConfigModel,

  url: "/configs"

});

function getCurrentDate(){
  var today = new Date();
  var hh = today.getHours();
  var MM = today.getMinutes();
  var dd = today.getDate();
  var mm = today.getMonth()+1; //January is 0!
  var yyyy = today.getFullYear();

  if(dd<10) {
    dd='0'+dd
  }

  if(mm<10) {
    mm='0'+mm
  }

  if(MM<10) {
    MM='0'+MM
  }

  today = dd+'/'+mm+'/'+yyyy + " " + hh + ":" + MM;

  return today;
}
