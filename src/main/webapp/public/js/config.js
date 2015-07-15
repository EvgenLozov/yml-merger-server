$(document).ready(function() {
    $.ajax({
        url: "/config",
        statusCode : {
            500: function() {
                alert("Не удалось получить данные! " +
                        "Убедитесь , что конфиг файл config/config.json сущевствует")
            }}
    }).then(function(data) {
        $('#user').val(data.user);
        $('#psw').val(data.psw);
        $('#encoding').val(data.encoding);
        $('#urls').val(data.urls);
        $('#files').val(data.files);
        $('#outputFile').val(data.outputFile);
        $('#categoryIds').val(data.categoryIds);
    });

    $( "#saveBtn" ).click(function() {
        saveConfig()
    });

    $( "#mergeBtn" ).click(function() {
        merge()
    });
});

function saveConfig(){
    var user = $('#user').val();
    var psw = $('#psw').val();
    var encoding = $('#encoding').val();
    var outputFile = $('#outputFile').val();
    var urls = $('#urls').val().split(",");
    var files = $('#files').val().split(",");
    var categoryIds = $('#categoryIds').val().split(",");

    var config = {
        user : user,
        psw : psw,
        encoding : encoding,
        outputFile : outputFile,
        urls : urls,
        files : files,
        categoryIds : categoryIds
    };

    $.ajax({
        type: "POST",
        url: "/config",
        data: JSON.stringify(config),
        dataType: "application/json",
        contentType: "application/json; charset=utf-8",
        statusCode : {
            200: function() {
                alert("Данные успешно сохранены!")
            },
            500: function() {
                alert("Не удалось сохранить данные!")
            }}
    });
}

function merge(){
    $.ajax({
        type: "POST",
        url: "/download",
        statusCode : {
            200: function() {
                alert("Ок!")
            },
            500: function() {
                alert("Ошмбка!")
            }}
    });
}