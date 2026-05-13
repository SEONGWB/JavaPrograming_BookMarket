var main = {
    init: function () {
        var _this = this;

        $('#btn-save').on('click', function () { _this.save(); });
        $('#btn-update').on('click', function () { _this.update(); });
        $('#btn-login').on('click', function () { _this.login(); });
        $('#btn-signup').on('click', function () { _this.signup(); });
        $('#btn-user-update').on('click', function () { _this.updateUser(); });
        $('#btn-book-delete').on('click', function () { _this.deleteBook($('#id').val()); });
        $(document).on('click', '#profile-menu-button', function (event) { _this.toggleProfileMenu(event); });
        $(document).on('click', '#btn-logout', function () { _this.logout(); });
        $(document).on('click', function (event) { _this.closeProfileMenu(event); });
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
        $('[data-action="book-delete"]').on('click', function () {
            _this.deleteBook($(this).data('book-id'));
        });

        this.syncUserLinks();
        this.loadCurrentUser();
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
            releaseDate: $('#releaseDate').val(),
            userId: this.getUserId()
        };
    },

    save: function () {
        if (!this.requireLogin()) {
            return;
        }

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
        if (!this.requireLogin()) {
            return;
        }

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

    deleteBook: function (bookId) {
        var userId = this.getUserId();
        if (!userId) {
            this.requireLogin();
            return;
        }

        if (!confirm('도서를 삭제하시겠습니까?')) {
            return;
        }

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/book/' + bookId + '?userId=' + encodeURIComponent(userId)
        }).done(function () {
            alert('도서가 삭제되었습니다.');
            window.location.href = '/books';
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

    getUserId: function () {
        return localStorage.getItem('bookmarketUserId');
    },

    requireLogin: function () {
        if (!this.getUserId()) {
            alert('로그인 후 이용해 주세요.');
            window.location.href = '/login';
            return false;
        }
        return true;
    },

    loadCurrentUser: function () {
        var _this = this;
        var userId = this.getUserId();
        var needsUser = $('#profileNameTitle, #profileEditName, #bookOwnerId, #adminUsers, #orderList').length > 0;

        if (!userId) {
            if (needsUser) {
                this.requireLogin();
            }
            return;
        }

        $.ajax({
            type: 'GET',
            url: '/api/v1/user/' + userId,
            dataType: 'json'
        }).done(function (user) {
            _this.renderHeaderUser(user);
            _this.renderProfile(user);
            _this.renderProfileEdit(user);
            _this.renderBookActions(user);
            _this.guardBookEdit(user);
            _this.renderAdminLink(user);
            _this.loadAdminDashboard(user);
            _this.loadOrderHistory(user);
        }).fail(function () {
            localStorage.removeItem('bookmarketUserId');
            if (needsUser) {
                alert('회원 정보를 찾을 수 없습니다. 다시 로그인해 주세요.');
                window.location.href = '/login';
            }
        });
    },

    renderHeaderUser: function (user) {
        var authMenu = $('#auth-menu');
        if (!authMenu.length || !user) {
            return;
        }

        authMenu.html(
            '<button type="button" id="profile-menu-button" class="flex items-center gap-2 rounded-lg px-2 py-1 transition-colors hover:bg-slate-100" aria-expanded="false">' +
            '<img src="' + this.profileImage(user.name) + '" alt="프로필 사진" class="h-8 w-8 rounded-full border border-gray-200 object-cover">' +
            '<span class="max-w-[120px] truncate font-semibold text-slate-900">' + this.escapeHtml(user.name) + '</span>' +
            '<i data-lucide="chevron-down" class="h-4 w-4 text-slate-500"></i>' +
            '</button>' +
            '<div id="profile-dropdown" class="absolute right-0 top-11 hidden w-40 overflow-hidden rounded-lg border border-gray-100 bg-white py-2 shadow-lg">' +
            '<a href="/admin" id="admin-menu-link" class="hidden items-center gap-2 px-4 py-2 text-sm font-semibold text-slate-700 transition-colors hover:bg-slate-50">' +
            '<i data-lucide="shield" class="h-4 w-4"></i>관리자</a>' +
            '<a href="/user/profile" class="flex items-center gap-2 px-4 py-2 text-sm font-semibold text-slate-700 transition-colors hover:bg-slate-50">' +
            '<i data-lucide="user-circle" class="h-4 w-4"></i>프로필</a>' +
            '<a href="/orders" class="flex items-center gap-2 px-4 py-2 text-sm font-semibold text-slate-700 transition-colors hover:bg-slate-50">' +
            '<i data-lucide="receipt-text" class="h-4 w-4"></i>구매내역</a>' +
            '<button type="button" id="btn-logout" class="flex w-full items-center gap-2 px-4 py-2 text-left text-sm font-semibold text-slate-700 transition-colors hover:bg-slate-50">' +
            '<i data-lucide="log-out" class="h-4 w-4"></i>로그아웃</button>' +
            '</div>'
        );
        lucide.createIcons();
    },

    toggleProfileMenu: function (event) {
        event.preventDefault();
        event.stopPropagation();

        var dropdown = $('#profile-dropdown');
        var button = $('#profile-menu-button');
        var willOpen = dropdown.hasClass('hidden');

        dropdown.toggleClass('hidden', !willOpen);
        button.attr('aria-expanded', String(willOpen));
    },

    closeProfileMenu: function (event) {
        if ($(event.target).closest('#auth-menu').length) {
            return;
        }

        $('#profile-dropdown').addClass('hidden');
        $('#profile-menu-button').attr('aria-expanded', 'false');
    },

    logout: function () {
        localStorage.removeItem('bookmarketUserId');
        $.ajax({ type: 'POST', url: '/logout' })
            .always(function () {
                window.location.href = '/login';
            });
    },

    canManageBook: function (bookOwnerId, user) {
        if (!user) {
            return false;
        }

        if (user.role === 'ADMIN') {
            return true;
        }

        return bookOwnerId && String(bookOwnerId) === String(user.id);
    },

    renderAdminLink: function (user) {
        if (user && user.role === 'ADMIN') {
            $('#admin-menu-link').removeClass('hidden').addClass('flex');
        }
    },

    renderBookActions: function (user) {
        var _this = this;
        $('[data-book-card]').each(function () {
            var card = $(this);
            var ownerId = card.data('owner-id');
            if (_this.canManageBook(ownerId, user)) {
                card.find('[data-owner-actions]').removeClass('hidden').addClass('grid');
            }
        });
    },

    guardBookEdit: function (user) {
        var ownerId = $('#bookOwnerId').val();
        if (!$('#id').length) {
            return;
        }

        if (this.canManageBook(ownerId, user)) {
            $('#btn-book-delete').removeClass('hidden');
            return;
        }

        alert('본인이 등록한 도서만 수정할 수 있습니다.');
        window.location.href = '/books';
    },

    loadAdminDashboard: function (user) {
        if (!$('#adminUsers').length) {
            return;
        }

        if (!user || user.role !== 'ADMIN') {
            alert('관리자만 접근할 수 있습니다.');
            window.location.href = '/books';
            return;
        }

        this.loadAdminUsers();
        this.loadAdminBooks();
        this.loadAdminOrders();
    },

    loadAdminUsers: function () {
        $.ajax({
            type: 'GET',
            url: '/api/v1/users',
            dataType: 'json'
        }).done(function (users) {
            $('#adminUserCount').text(users.length);
            var html = users.map(function (user) {
                return '<div class="flex items-center justify-between rounded-lg bg-slate-50 px-4 py-3">' +
                    '<span class="font-semibold text-slate-900">' + main.escapeHtml(user.name) + '</span>' +
                    '<span class="text-slate-500">' + main.escapeHtml(user.loginId) + ' / ' + main.escapeHtml(user.role) + '</span>' +
                    '</div>';
            }).join('');
            $('#adminUsers').html(html || '<p>회원이 없습니다.</p>');
        });
    },

    loadAdminBooks: function () {
        $.ajax({
            type: 'GET',
            url: '/api/v1/books',
            dataType: 'json'
        }).done(function (books) {
            $('#adminBookCount').text(books.length);
            var html = books.map(function (book) {
                return '<div class="flex items-center justify-between gap-3 rounded-lg bg-slate-50 px-4 py-3">' +
                    '<div>' +
                    '<p class="font-semibold text-slate-900">' + main.escapeHtml(book.title) + '</p>' +
                    '<p class="text-xs text-slate-500">등록자 ' + main.escapeHtml(book.ownerName || '-') + '</p>' +
                    '</div>' +
                    '<a href="/book/update/' + book.id + '" class="shrink-0 rounded-lg border border-gray-200 px-3 py-2 text-xs font-semibold text-slate-600 hover:bg-white">관리</a>' +
                    '</div>';
            }).join('');
            $('#adminBooks').html(html || '<p>도서가 없습니다.</p>');
        });
    },

    loadAdminOrders: function () {
        $.ajax({
            type: 'GET',
            url: '/api/v1/orders',
            dataType: 'json'
        }).done(function (orders) {
            $('#adminOrderCount').text(orders.length);
            $('#adminOrders').html(main.orderListHtml(orders, true));
        });
    },

    loadOrderHistory: function (user) {
        if (!$('#orderList').length) {
            return;
        }

        $.ajax({
            type: 'GET',
            url: '/api/v1/orders/' + user.id,
            dataType: 'json'
        }).done(function (orders) {
            $('#orderList').html(main.orderListHtml(orders, false));
        });
    },

    orderListHtml: function (orders, showUser) {
        if (!orders || !orders.length) {
            return '<div class="rounded-2xl border border-gray-100 bg-white px-6 py-16 text-center text-slate-500 shadow-[0_4px_20px_-4px_rgba(0,0,0,0.06)]">구매내역이 없습니다.</div>';
        }

        return orders.map(function (order) {
            var items = order.items.map(function (item) {
                return '<li class="flex items-center justify-between gap-4 py-2">' +
                    '<span class="font-semibold text-slate-800">' + main.escapeHtml(item.title) + '</span>' +
                    '<span class="text-slate-500">' + item.orderPrice.toLocaleString('ko-KR') + '원 x ' + item.count + '</span>' +
                    '</li>';
            }).join('');
            var user = showUser ? '<p class="text-sm font-semibold text-slate-500">구매자 ' + main.escapeHtml(order.userName) + '</p>' : '';

            return '<article class="rounded-2xl border border-gray-100 bg-white p-6 shadow-[0_4px_20px_-4px_rgba(0,0,0,0.06)]">' +
                '<div class="mb-4 flex flex-col justify-between gap-2 sm:flex-row sm:items-start">' +
                '<div>' +
                '<p class="text-xs font-bold uppercase tracking-[0.16em] text-emerald-700">Order #' + order.id + '</p>' +
                '<h2 class="mt-1 text-xl font-extrabold text-[#0B1E40]">' + main.formatDate(order.orderDate) + '</h2>' +
                user +
                '</div>' +
                '<strong class="text-xl font-extrabold text-slate-900">' + order.totalPrice.toLocaleString('ko-KR') + '원</strong>' +
                '</div>' +
                '<ul class="divide-y divide-gray-100 text-sm">' + items + '</ul>' +
                '</article>';
        }).join('');
    },

    formatDate: function (value) {
        if (!value) {
            return '-';
        }

        return String(value).replace('T', ' ').slice(0, 16);
    },

    renderProfile: function (user) {
        if (!$('#profileNameTitle').length || !user) {
            return;
        }

        $('#profileAvatar').text(this.initial(user.name));
        $('#profileNameTitle').text(user.name + '님');
        $('#profileLoginId').text(user.loginId);
        $('#profileName').text(user.name);
        $('#profileProvider').text(user.provider);
    },

    renderProfileEdit: function (user) {
        if (!$('#profileEditName').length || !user) {
            return;
        }

        $('#profileEditLoginId').val(user.loginId);
        $('#profileEditName').val(user.name);
    },

    updateUser: function () {
        var userId = this.getUserId();
        if (!userId) {
            this.requireLogin();
            return;
        }

        var data = {
            name: $('#profileEditName').val().trim(),
            password: $('#profileEditPassword').val()
        };

        if (!data.name) {
            alert('이름을 입력해 주세요.');
            return;
        }

        $.ajax({
            type: 'PUT',
            url: '/api/v1/user/' + userId,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('개인정보가 수정되었습니다.');
            window.location.href = '/user/profile';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    initial: function (name) {
        return (name || 'U').trim().charAt(0).toUpperCase();
    },

    profileImage: function (name) {
        var initial = this.initial(name);
        var svg = '<svg xmlns="http://www.w3.org/2000/svg" width="80" height="80" viewBox="0 0 80 80">' +
            '<rect width="80" height="80" rx="40" fill="#0B1E40"/>' +
            '<text x="50%" y="54%" dominant-baseline="middle" text-anchor="middle" fill="#ffffff" font-family="Arial, sans-serif" font-size="34" font-weight="700">' +
            this.escapeHtml(initial) +
            '</text></svg>';
        return 'data:image/svg+xml;charset=UTF-8,' + encodeURIComponent(svg);
    },

    escapeHtml: function (value) {
        return String(value || '')
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#039;');
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
