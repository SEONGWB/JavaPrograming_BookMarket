var main = {
    init: function () {
        var _this = this;

        $('#btn-save').on('click', function () { _this.save(); });
        $('#btn-update').on('click', function () { _this.update(); });
        $('#btn-login').on('click', function () { _this.login(); });
        $('#btn-signup').on('click', function () { _this.signup(); });
        $('#btn-order').on('click', function () { _this.order($(this).data('user-id')); });
        $('#imageUrl').on('input', function () { _this.setImagePreview($(this).val()); });
        $('#imageFile').on('change', function () { _this.readImageFile(this.files[0]); });

        $('[data-action="add-cart"]').on('click', function () {
            _this.addCart($(this).data('book-id'));
        });
        $('[data-action="cart-delete"]').on('click', function () {
            _this.deleteCartItem($(this).data('cart-item-id'));
        });
        $('[data-action="cart-quantity"]').on('change', function () {
            _this.updateQuantity($(this).data('cart-item-id'), $(this).val());
        });

        this.syncUserLinks();
        this.renderCartTotals();
        this.setImagePreview($('#imageUrl').val());
    },

    bookFormData: function () {
        return {
            isbn: $('#isbn').val(),
            title: $('#title').val(),
            author: $('#author').val(),
            publisher: $('#publisher').val(),
            price: Number($('#price').val() || 0),
            description: $('#description').val(),
            imageUrl: $('#imageUrl').val(),
            category: $('#category').val(),
            releaseDate: $('#releaseDate').val()
        };
    },

    save: function () {
        $.ajax({
            type: 'POST',
            url: '/api/v1/book',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(this.bookFormData())
        }).done(function () {
            alert('도서가 등록되었습니다.');
            window.location.href = '/books';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    update: function () {
        var id = $('#id').val();
        $.ajax({
            type: 'PUT',
            url: '/api/v1/book/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(this.bookFormData())
        }).done(function () {
            alert('도서 정보가 수정되었습니다.');
            window.location.href = '/books';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    signup: function () {
        var data = {
            loginId: $('#signupLoginId').val(),
            password: $('#signupPassword').val(),
            name: $('#signupName').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/signup',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (userId) {
            localStorage.setItem('bookmarketUserId', userId);
            $.ajax({ type: 'POST', url: '/api/v1/cart/' + userId })
                .always(function () {
                    alert('회원가입이 완료되었습니다.');
                    window.location.href = '/books';
                });
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    login: function () {
        var data = {
            loginId: $('#loginId').val(),
            password: $('#password').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/login',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (user) {
            localStorage.setItem('bookmarketUserId', user.id);
            alert(user.name + '님, 환영합니다.');
            window.location.href = '/books';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    addCart: function (bookId) {
        var userId = localStorage.getItem('bookmarketUserId');
        if (!userId) {
            alert('로그인 후 장바구니를 이용해 주세요.');
            window.location.href = '/login';
            return;
        }

        $.ajax({
            type: 'POST',
            url: '/api/v1/cart/' + userId + '/book',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({ bookId: bookId, quantity: 1 })
        }).done(function () {
            alert('장바구니에 담았습니다.');
            window.location.href = '/cart/' + userId;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    updateQuantity: function (cartItemId, quantity) {
        $.ajax({
            type: 'PUT',
            url: '/api/v1/cart',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({ cartItemId: cartItemId, quantity: Number(quantity || 1) })
        }).done(function () {
            window.location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    deleteCartItem: function (cartItemId) {
        $.ajax({
            type: 'DELETE',
            url: '/api/v1/cart/' + cartItemId
        }).done(function () {
            window.location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    order: function (userId) {
        $.ajax({
            type: 'POST',
            url: '/api/v1/order/' + userId,
            dataType: 'json'
        }).done(function (orderId) {
            alert('주문이 완료되었습니다. 주문 번호: ' + orderId);
            window.location.href = '/books';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    renderCartTotals: function () {
        var total = 0;
        $('[data-cart-row]').each(function () {
            var price = Number($(this).data('price') || 0);
            var quantity = Number($(this).data('quantity') || 0);
            var lineTotal = price * quantity;
            total += lineTotal;
            $(this).find('[data-line-total]').text(lineTotal.toLocaleString('ko-KR') + '원');
        });
        $('#cart-total, #cart-grand-total').text(total.toLocaleString('ko-KR') + '원');
    },

    syncUserLinks: function () {
        var userId = localStorage.getItem('bookmarketUserId');
        if (userId) {
            $('a[href="/cart/1"]').attr('href', '/cart/' + userId);
        }
    },

    readImageFile: function (file) {
        var _this = this;
        if (!file) {
            return;
        }

        var reader = new FileReader();
        reader.onload = function (event) {
            $('#imageUrl').val(event.target.result);
            _this.setImagePreview(event.target.result);
        };
        reader.readAsDataURL(file);
    },

    setImagePreview: function (src) {
        var preview = $('#imagePreview');
        var empty = $('#imageEmpty');

        if (!preview.length) {
            return;
        }

        if (src) {
            preview.attr('src', src).removeClass('hidden');
            empty.addClass('hidden').removeClass('flex');
        } else {
            preview.attr('src', '').addClass('hidden');
            empty.removeClass('hidden').addClass('flex');
        }
    }
};

main.init();
