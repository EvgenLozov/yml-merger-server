$(document).ready(function() {
    $.ajax({
        url: "/config"
    }).then(function(data) {
        $('#user').val(data.user);
        $('#psw').val(data.psw);
        $('#encoding').val(data.encoding);
        $('#urls').val(data.urls);
        $('#files').val(data.files);
        $('#outputFile').val(data.outputFile);
        $('#categoryIds').val(data.categoryIds);
    });
});