$(document).ready(function () {
    insertContainer();


    /* wir plazieren mal die elemente im container
    function insertContainer() {


        $('.content-item').each(function (index,contentitem) {
            var item = $(this);

            $('.insert-container-content').each(function (index2,contentitem2) {
                // nehmen nur die items die nach dem container kommen
                if (index >= $(this).data('pos')) {
                    // und leer sollte der platz auch sein
                    if (!$(this).html()) {
                        // wir machen das move icon weg
                        item.find('.handle').remove();
                        // und fügen dafür ein release button hinzu
                        // TODO hier auch jquery plugin draus machen wegen url
                        item.find('.btn-group').append('<a href="/edulu/course/'+$(this).data("cid")+'/lesson/'+$(this).data("lid")+'/move/out/'+$(this).data("id")+'/'+item.data("id")+'/'+$(this).data("place")+'" class="btn btn-info">Release</a>');
                        item.appendTo($(this));
                        return false;
                    }
                }
            });

        });

    }
    */

});


