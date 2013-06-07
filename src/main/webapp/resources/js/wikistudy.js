$(document).ready(function () {

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

    $(".update-content").click(function () {
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
            var dialog = $("#content-update");
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

    $(".update-quiz").click(function () {
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

    $('#slick-quiz').slickQuiz({json: {
        "info": {
            "name": "Test Your Knowledge!!",
            "main": "<p>Think you're smart enough to be on Jeopardy? Find out with this super crazy knowledge quiz!</p>",
            "results": "<h5>Learn More</h5><p>Etiam scelerisque, nunc ac egestas consequat, odio nibh euismod nulla, eget auctor orci nibh vel nisi. Aliquam erat volutpat. Mauris vel neque sit amet nunc gravida congue sed sit amet purus.</p>",
            "level1": "Jeopardy Ready",
            "level2": "Jeopardy Contender",
            "level3": "Jeopardy Amateur",
            "level4": "Jeopardy Newb",
            "level5": "Stay in school, kid..." // no comma here
        },
        "questions": [
            { // Question 1
                "q": "What number is the letter A in the English alphabet?",
                "a": [
                    {"option": "8", "correct": false},
                    {"option": "14", "correct": false},
                    {"option": "1", "correct": true},
                    {"option": "23", "correct": false} // no comma here
                ],
                "correct": "<p><span>That's right!</span> The letter A is the first letter in the alphabet!</p>",
                "incorrect": "<p><span>Uhh no.</span> It's the first letter of the alphabet. Did you actually <em>go</em> to kindergarden?</p>" // no comma here
            },
            { // Question 2
                "q": "How many inches of rain does Michigan get on average per year?",
                "a": [
                    {"option": "149", "correct": false},
                    {"option": "32", "correct": true},
                    {"option": "3", "correct": false},
                    {"option": "1291", "correct": false} // no comma here
                ],
                "correct": "<p><span>Holy bananas!</span> I didn't actually expect you to know that! Correct!</p>",
                "incorrect": "<p><span>Fail.</span> Sorry. You lose. It actually rains approximately 32 times a year in Michigan.</p>" // no comma here
            },
            { // Question 3
                "q": "In which of these places can you purchase a car?",
                "a": [
                    {"option": "The Zoo", "correct": false},
                    {"option": "Ebay", "correct": true},
                    {"option": "Grocery Store", "correct": false},
                    {"option": "Used Car Lot", "correct": true} // no comma here
                ],
                "correct": "<p><span>Nice!</span> You can indeed buy a car on Ebay or in a used car lot.</p>",
                "incorrect": "<p><span>No.</span> You can't buy a car at the zoo or in a grocery store, try Ebay or a used car lot instead.</p>" // no comma here
            },
            { // Question 4
                "q": "Is Earth bigger than a basketball?",
                "a": [
                    {"option": "Yes", "correct": true},
                    {"option": "No", "correct": false} // no comma here
                ],
                "correct": "<p><span>Good Job!</span> You must be very observant!</p>",
                "incorrect": "<p><span>ERRRR!</span> What planet Earth are <em>you</em> living on?!?</p>" // no comma here
            },
            { // Question 5
                "q": "Where are you right now? Select ALL that apply.",
                "a": [
                    {"option": "Planet Earth", "correct": true},
                    {"option": "Pluto", "correct": false},
                    {"option": "At a computer", "correct": true},
                    {"option": "The Milky Way", "correct": true} // no comma here
                ],
                "correct": "<p><span>Brilliant!</span> You're seriously a genius, (wo)man.</p>",
                "incorrect": "<p><span>Not Quite.</span> You're actually on Planet Earth, in The Milky Way, At a computer. But nice try.</p>" // no comma here
            } // no comma here
        ]
    }});


});


