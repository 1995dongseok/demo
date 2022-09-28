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
$("#free-board-detail-popup-title").text('게시글 상세보기');
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
$("#free-board-detail-body-splitter").kendoSplitter({
    orientation: 'vertical',
    panes: [
        {collapsible: false, resizable: false},
        {collapsible: false, size: '100px', resizable: false}
    ]
});
$("#free-board-detail-reg-date").kendoTextBox({
    fillMode: 'flat',
    readonly: true
});
$("#free-board-detail-modi-date").kendoTextBox({
    fillMode: 'flat',
    readonly: true
});
$("#free-board-detail-like").kendoButton({
    themeColor: 'base',
    click: () => {
        message.callBackConfirm({msg: '추천 하시겠습니까?', callback: new likeInsert().like});

    }

})

$("#free-board-detail-delete-btn").kendoButton({
    themeColor: 'base',
    click: () => {
        if (userIdx !== dbUserIdx) {
            alert("자신이 작성한 글만 삭제할 수 있습니다.");
        } else {
            message.callBackConfirm({msg: '삭제 하시겠습니까?', callback: new boardDel().deleteOne});
        }
        ;
    }

});
$("#free-board-detail-update-btn").kendoButton({
    themeColor: 'info',
    click: () => {
        const boardNum = Number($("#free-board-detail-board-num").val());
        if (userIdx !== dbUserIdx) {
            alert("자신이 작성한 글만 수정할 수 있습니다.");
        } else {
            window.location.href = '/modify/' + boardNum;
        }
        ;
    }
});

boardRegidate = kendo.toString(new Date(boardRegidate), "yyyy-MM-dd H:mm");
boardModidate = kendo.toString(new Date(boardModidate), "yyyy-MM-dd H:mm");
$("#free-board-detail-user-num").val(userIdx)
$("#free-board-detail-board-num").val(boardNum);
$("#free-board-detail-title").text(boardTitle);
$("#free-board-detail-contents").text(boardContets);
$("#free-board-detail-reg-date").data("kendoTextBox").value(boardRegidate);
$("#free-board-detail-modi-date").data("kendoTextBox").value(boardModidate);

class boardDel {
    deleteOne() {
        const boardNum = Number($("#free-board-detail-board-num").val());
        $.ajax({
            url: '/v1/delete/' + boardNum,
            contentType: "application/json; charset=utf-8",
            success: window.location.href = '/board'
        });
    };

}

class likeInsert {
    like() {
        const boardNum = Number($("#free-board-detail-board-num").val());
        $.ajax({
            type: "POST",
            url: '/v1/updateLike/' + boardNum,
            contentType: "application/json; charset=utf-8",
            success: location.reload()

        });


    }
}

