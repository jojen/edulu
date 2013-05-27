$(document).ready(function () {

    $(".edit").click(function () {
        var id = $(this).attr("data-id");
        $.get("/course/edit", {
            id: id
        }, function (result) {
            // hier machen machen wir das editierbar
            $("#"+id).after(result);
            $("#"+id).hide();
        });
    });

/* wir machen mal hier draus ein Text Feld
    $.each($(".update"), function (index) {
        var updatetext = $(this).text();
        var id = $(this).attr("data-id");
        var key = $(this).attr("data-key");

        var input = $("<input data-id=\"" + id + "\" data-key=\"" + key + "\" class=\"update-input\" value=\"" + updatetext + "\" >");
        $(this).replaceWith(input);
        input.bind('keyup', function () {
            var id = input.attr("data-id");
            var key = input.attr("data-key");
            var value = input.val();

            update(id, key, value);
        });

    });


    for (var i in CKEDITOR.instances) {
        var editor = CKEDITOR.instances[i];
        editor.on("instanceReady", function (e) {
            e.editor.on("key", function (event) {
                console.log(event.editor.getData());
            })
        });
    }


    function update(id, key, value) {
        // TODO das müssen wir hier noch in einer Schleife machen

        console.log("update: [" + id + "] " + key + " : " + value);

        $.getJSON("/course/update", {
            id: id,
            key: key,
            value: value
        }, function (result) {
            // Should be ok
        });
    }
    +*/
});


