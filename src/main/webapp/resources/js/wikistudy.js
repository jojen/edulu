$(document).ready(function () {

    $(".update-course").click(function () {
        var id = $(this).data("id");

        $.get("/ajax/course/edit", {
            id: id
        }, function (result) {
            // hier machen machen wir das editierbar
            $("body").prepend(result);
            var dialog = $("#course-update");
            dialog.modal('show');
            // TODO das müssen wir noch für revert korrekt machen

            dialog.on('hidden', function () {
                $('#form-framework').submit();
            });

        });
    });

});


