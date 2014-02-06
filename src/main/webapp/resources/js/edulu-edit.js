$(document).ready(function () {
    /**
     * NOTE - this is only for Edit Mode
     */


    $('.noEnterSubmit').keypress(function(e){
        if ( e.which == 13 ) e.preventDefault();
    });

    $('.update-Image').click(function(e){
        var id = $(this).data('id');
        $("#update-"+id+"-section").slideToggle();
    });
    $('#move-lesson').click(function(){
        $('#lesson-settings').slideToggle();
    });

    $('.content-property').change(function(e){
        var name = $(this).data('name');
        var id = $(this).data('id');
        var value;

        if ($(this).prop('type') == 'checkbox'){
            value = $(this).get(0).checked;
        }

        $.get("/edulu/content/property/", {
            name: name,
            value: value,
            id: id
        }, function (result) {

        });


    });


    $("#update-lesson-name").click(function(){
        var action = $(this).data('action');
        var name = $("#lesson-name-input").val();
        var id = $(this).data('id');

        $.get(action, {
            name: name
        }, function (result) {
            console.log(id);
            // Wir wursteln noch ein bisschen im dom herum
            if(name && !$("#lesson-name-"+id).val()){
                name = "- " +name;
            }
            $("#lesson-name-"+id).html(name);
        });
    });

    $(".btn-danger").click(function(){
        // Generell wollen wir bei allen gefährlichen Sachen
        // nen Dialog vorher haben
       var action = $(this).data('action');
        if(action!= null){
            $("#modal-yes").data("action",action);
            $("#modal-yes").data("delete",$(this).data('delete'));
            $("#modal-from-dom").modal('show');
        }
    });

    $("#modal-yes").click(function(){
        var action = $(this).data("action");
        if(action != null){
            // Da könnte man mal sehen, ob das Ergebnis angekommen ist
            $.get(action);
        }
        var del = $(this).data("delete");
        if(del != null){
            $(del).remove();
        }

        $("#modal-from-dom").modal('hide');
    });

    $(".sortable").sortable({

            start: function (event, ui) {
                ui.item.startPos = ui.item.index();
            },
            stop: function (event, ui) {
                var startpos = ui.item.startPos;
                var endpos = ui.item.index();
                var id = $(this).data("id");
                var type = $(this).data("type");
                $.get("/edulu/course/move/"+ type +"/" + id, {
                    from: startpos,
                    to: endpos
                });
            }
        }

    );

    $(".sortable").disableSelection();
    $( ".droppable" ).droppable({
        tolerance: "pointer",
        hoverClass:'hovered',
        drop: function( event, ui ) {
            var id = $(ui.draggable).data("id");
            var cid = $(this).data('cid');
            var lid = $(this).data('lid');
            var containerid = $(this).data('id');
            var place = $(this).data('place');
            // TODO jquery plugin draus machen um url zu übergeben
            window.location.href = "/edulu/course/"+cid+"/lesson/"+lid+"/move/in/"+containerid+"/"+id+"/"+place;
        }
    });

    $(".update-course").click(function () {
        var id = $(this).data("id");
        // TODO URL muss übergeben werden -> JQ Plugin draus machen
        $.get("/edulu/course/edit/", {
            id: id
        }, function (result) {
            // hier machen machen wir das editierbar
            $("body").prepend(result);
            var dialog = $("#course-update");
            dialog.modal('show');
            var revert = true;
            $("#save").click(function () {
                revert = false;
                dialog.modal('hide');
            });

            $("#revert").click(function () {
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
        $.get("/edulu/content/text/edit/", {
            courseid: courseId,
            lessonid: lessonId,
            id: id
        }, function (result) {
            // hier machen machen wir das editierbar
            $("body").prepend(result);
            var dialog = $("#text-update");
            dialog.modal('show');
            var revert = true;

            $("#save").click(function () {
                revert = false;
                dialog.modal('hide');
            });

            $(".close").click(function () {
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
        pasteZone: null,
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .bar').css(
                'width',
                progress + '%'
            );
        }
    });


    $(".update-Quiz").click(function () {
        if(!$(this).parent().hasClass("disabled")){
            var id = $(this).data("id");
            var lessonId = $(this).data("lessonid");
            var courseId = $(this).data("courseid");
            // TODO URL muss übergeben werden -> JQ Plugin draus machen
            $.get("/edulu/content/quiz/edit/", {
                    courseid: courseId,
                    lessonid: lessonId,
                    id: id
                }, function (result) {
                    // hier machen machen wir das editierbar
                    $("body").prepend(result);
                    var dialog = $("#quiz-update");
                    dialog.modal('show');
                    var revert = true;
                    $("#save").click(function () {
                        revert = false;
                        dialog.modal('hide');
                    });

                    $(".close").click(function () {
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
            );
        }

    }

    );


});


