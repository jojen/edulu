$(document).ready(function () {
    /**
     * NOTE - this is only for Edit Mode
     */

    $("button.btn-danger").click({

    });

    $(".sortable").sortable({
            start: function (event, ui) {
                ui.item.startPos = ui.item.index();
            },
            stop: function (event, ui) {
                var startpos = ui.item.startPos;
                var endpos = ui.item.index();
                var lesson = $(this).data("id");
                $.get("/wikistudy/course/move/" + lesson, {
                    from: startpos,
                    to: endpos
                });
            }
        }

    );
    $(".sortable").disableSelection();

    $(".update-course").click(function () {
        var id = $(this).data("id");
        // TODO URL muss übergeben werden -> JQ Plugin draus machen
        $.get("/wikistudy/course/edit/", {
            id: id
        }, function (result) {
            // hier machen machen wir das editierbar
            $("body").prepend(result);
            var dialog = $("#course-update");
            dialog.modal('show');

            $("#save").click(function () {
                dialog.modal('hide');
            });
            var revert = false;
            $("#revert").click(function () {
                revert = true;
                dialog.modal('hide');
            });

            dialog.on('hidden', function () {
                if (!revert) {
                    $('#form-framework').submit();
                }
            });

        });
    });

    $(".update-Text").click(function () {
        var id = $(this).data("id");
        var lessonId = $(this).data("lessonid");
        var courseId = $(this).data("courseid");
        // TODO URL muss übergeben werden -> JQ Plugin draus machen
        $.get("/wikistudy/content/text/edit/", {
            courseid: courseId,
            lessonid: lessonId,
            id: id
        }, function (result) {
            // hier machen machen wir das editierbar
            $("body").prepend(result);
            var dialog = $("#text-update");
            dialog.modal('show');

            $("#save").click(function () {
                dialog.modal('hide');
            });
            var revert = false;
            $(".close").click(function () {
                revert = true;
                dialog.modal('hide');
            });

            dialog.on('hidden', function () {
                if (!revert) {
                    $('#form-framework').submit();
                }
            });

        });
    });


    $(document).bind('dragover', function (e) {
        var dropZone = $('#dropzone'),
            timeout = window.dropZoneTimeout;
        if (!timeout) {
            dropZone.addClass('in');
        } else {
            clearTimeout(timeout);
        }
        var found = false,
            node = e.target;
        do {
            if (node === dropZone[0]) {
                found = true;
                break;
            }
            node = node.parentNode;
        } while (node != null);
        if (found) {
            dropZone.addClass('hover');
        } else {
            dropZone.removeClass('hover');
        }
        window.dropZoneTimeout = setTimeout(function () {
            window.dropZoneTimeout = null;
            dropZone.removeClass('in hover');
        }, 100);
    });


    $('#fileupload').fileupload({
        dataType: 'json',
        done: function (e, data) {
            window.location.reload();
        },
        dropZone: $('#dropzone'),
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .bar').css(
                'width',
                progress + '%'
            );
        }
    });


    $(".update-Quiz").click(function () {
        var id = $(this).data("id");
        var lessonId = $(this).data("lessonid");
        var courseId = $(this).data("courseid");
        // TODO URL muss übergeben werden -> JQ Plugin draus machen
        $.get("/wikistudy/content/quiz/edit/", {
                courseid: courseId,
                lessonid: lessonId,
                id: id
            }, function (result) {
                // hier machen machen wir das editierbar
                $("body").prepend(result);
                var dialog = $("#quiz-update");
                dialog.modal('show');

                $("#save").click(function () {
                    dialog.modal('hide');
                });
                var revert = false;
                $(".close").click(function () {
                    revert = true;
                    dialog.modal('hide');
                });

                dialog.on('hidden', function () {
                    if (!revert) {
                        $('#quiz-json-form').submit();
                        $('#quiz-form').append("<input name='quizcontent' value='" + getQuizContent() + "'>");
                        $('#quiz-form').submit();
                    }
                });
            }
        )
        ;
    });


});


