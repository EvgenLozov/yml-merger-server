
"use strict";
APP.ConfigModel = Backbone.Model.extend({

  defaults: {
    "name" : "Конфиг " + getCurrentDate(),
    "user" : "test",
    "psw" : "test",
    "urls" : [ ],
    "files" : [ ],
    "encoding" : "utf-8",
    "currencies" : ["RUR"],
    "outputFile" : "",
    "categoryIds" : [],
    "oldPrice" : 0.01,
    "replaces": [],
    "autoMerge" : false,
    "period" : 0,
    "time" : "00:00"
  },


  urlRoot: "/configs",

  validate: function (attrs) {
    var errors = {};

    if (!attrs.name || !attrs.name.trim()) errors.name = "Укажите название";
    if (!attrs.user || !attrs.user.trim()) errors.user = "Укажите логин для ApiShops";
    if (!attrs.psw || !attrs.psw.trim()) errors.psw = "Укажите пароль для ApiShops";
    if (!attrs.encoding || !attrs.encoding.trim()) errors.encoding = "Укажите кодировку";
    if (!attrs.oldPrice || attrs.oldPrice <0 ) errors.oldPrice = "Наценка для старой цены должна быть больше 0";
    if (!attrs.currencies || !attrs.currencies.length > 0) errors.currencies = "Укажите хотя бы одну валюту";

    if (attrs.autoMerge)
      if (!attrs.period || attrs.period < 1 ) errors.period = "Период обновления должен быть не менше 1 дня";

    if (attrs.autoMerge)
      if (!attrs.time || !attrs.name.trim() )
        errors.time = "Укажите время обновления";
      else
        if (!this.checkTime(attrs.time))
          errors.time = "Ошибка в формате времени";

    var errorsMessages = "";

    var replaces = new APP.ReplaceCollection(attrs.replaces);
    replaces.each(function(replace){
      if (!replace.isValid()){
        _.each(replace.validationError, function(errorMessage){
          errorsMessages += errorMessage + ";";
        });
      }
    });

    if (errorsMessages.trim())
      errors.replace = errorsMessages;

    if (!_.isEmpty(errors)) {
      return errors;
    }
  },

  checkTime: function(timeString){
    var hoursString = timeString.split(":")[0];
    var minutesString = timeString.split(":")[1];

    if (!hoursString || !minutesString)
    return false;

    var hours;
    var minutes;
    try {
      hours = parseInt(hoursString);
      minutes = parseInt(minutesString);
    } catch (e){
      return false;
    }

    if (hours < 0 || hours > 23 ||
        minutes < 0 || minutes > 59)
      return false;

    return true;
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
