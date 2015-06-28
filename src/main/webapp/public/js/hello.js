$(document).ready(function() {
    $.ajax({
        url: "/hi"
    }).then(function(data) {
        $('.greeting-id').append(data.id);
        $('.greeting-content').append(data.content);
    });
});