function submit(e) {
    e.preventDefault();
    $("#saveboardform").submit();
}

const message = {
    callBackConfirm: function (obj) {
        let opt = $.extend({title: document.title, msg: '처리하겠습니까?', callback: ''}, obj);
        let $div = $('<div id="callBackConfirm"></div>');
        $('body').append($div);
        $($div).kendoDialog({
            title: opt.title,
            content: opt.msg,
            minWidth: 300,
            minHeight: 150,
            closable: false,
            actions: [
                {text: '취소'},
                {
                    text: '확인',
                    action: function () {
                        if (typeof opt.callback === 'function') {
                            opt.callback.call();
                        }
                        return true;
                    },
                    primary: true
                }
            ],
            close: function () {
                $($div).data('kendoDialog').destroy();
            }

        });
    }
}
$('#free-board-editor-popup-title').text('게시글 등록');
$("#free-board-search-drop-down-list").kendoDropDownList();
$('#free-board-editor-title').kendoTextBox();
$('#editor').kendoTextBox();
$('#free-board-editor-btn-save').kendoButton({
    themeColor: 'info'
});

$('#free-board-editor-btn-cancle').kendoButton({
    themeColor: 'base',
    click: function () {
        window.location.href = "/board";
    }
});

var appbar = $("#appbar").kendoAppBar({
    themeColor: "inherit",
    items: [
        {
            template: '<a id="logo" href="/board"> <img src="/images/centerlink.png" alt="logo" style="width: 100%"> </a>',
            type: "contentItem"
        },

        {width: 1, type: "spacer"},
        {
            template: '<button id="home-top-btn"></button>', type: "contentItem"
        },
        {width: 16, type: "spacer"}
    ]
});

$("#home-top-btn").kendoFloatingActionButton({
    icon: "plus",
    align: 'top end',
    positionMode: "absolute",
    themeColor: "info",
    size: "medium",
    alignOffset: {
        x: 10,
        y: 10
    },
    items: [
        {
            label: '로그아웃',
            icon: "logout",
            click: function () {
                message.callBackConfirm({msg: '로그아웃 하시겠습니까?', callback: ''})
            }
        },
        {
            label: '마이페이지',
            icon: 'user'
        }]
});