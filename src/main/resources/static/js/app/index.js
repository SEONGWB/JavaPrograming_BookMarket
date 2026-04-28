var main = {

    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () { _this.save(); });
    },

    save : function () {
        var data = {
            isbn: $('#isbn').val(),
            title: $('#title').val(),
            author: $('#author').val(),
            publisher: $('#publisher').val(),
            price: $('#price').val()
        };
        $.ajax({
            type: 'POST',
            url: '/api/v1/book',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('도서가등록되었습니다.');
            window.location.href= '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};


main.init();