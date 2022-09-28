$("#tilelayout").kendoTileLayout({
    containers: [{
        colSpan: 1,
        rowSpan: 3,
        header: {
            text: "추천 게시글"
        },
        bodyTemplate: kendo.template($("#free-board-rec-grid-template").html())
    }, {
        colSpan: 4,
        rowSpan: 3,
        header: {
            text: "게시판"
        },
        bodyTemplate: kendo.template($("#free-board-grid-template").html())
    }],
    columns: 5,
    columnsWidth: 360,
    rowsHeight: 275
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
                message.callBackConfirm({msg: '로그아웃 하시겠습니까?', callback: new user().logout})
            }
        },
        {
            label: '마이페이지',
            icon: 'user'
        }]
});
$("#free-board-range-drop-down-list").kendoDropDownList({
    fillMode: "flat",
});
$("#free-board-search-drop-down-list").kendoDropDownList();
$("#free-board-search-text-box").kendoTextBox();
const boardDataSource = {
    boardSelectPageDataSource: () => {
        return new kendo.data.DataSource({
            transport: {
                read: {
                    url: "/v1/boardList",
                    type: "GET",
                    dataType: "json",
                    contentType: 'application/json; charset=utf-8'
                },
                parameterMap : (data)=>{
                    return {
                        page : parseInt(data.page) -1,
                        size : data.pageSize
                    }
                }
            },
            schema: {
                model: {
                    boardNum: {type: "number"},
                    boardTitle: {type: "string"},
                    boardRegidate: {type: "string"},
                    boardViewcounts: {type: "number"},
                    likeCnt: {type: "number"},
                    userName: {type: "string"}
                },
                parse: (res) => {
                    res.forEach((row) => {
                        row.boardRegidate = kendo.toString(new Date(row.boardRegidate), "yyyy-MM-dd HH:mm");
                        row.userName = row.member.userName;
                    })
                    console.log(res);
                    return res;
                }
            },
            pageSize: 2
        })
    }
}
$("#free-board-grid").kendoGrid({
    columns: [
        {
            field: "boardNum",
            title: "글번호",
            width: 60,
            attributes: {style: 'text-align:center'}
        },
        {
            field: "boardTitle",
            title: "제목",
            attributes: {style: 'text-overflow: ellipsis; overflow: hidden; white-space: nowrap;'}
        },
        {
            field: "boardRegidate",
            title: "등록시간",
            width: 100,
            attributes: {style: 'text-align:center'},
        },
        {
            field: "boardViewcounts",
            title: "조회수",
            width: 60,
            attributes: {style: 'text-align:center'},
        },
        {
            field: "likeCnt",
            title: "추천수",
            width: 60,
            attributes: {style: 'text-align:center'},
        },
        {
            field: "userName",
            title: "등록자",
            width: 110,
            attributes: {style: 'text-align:center'},
        }
    ],
    dataSource: boardDataSource.boardSelectPageDataSource(),
    change: (e) => {
        const cell = e.sender.select();
        const selected = e.sender.dataSource.view()[cell.closest("tr").index()];
        window.location.href = '/v1/detailOne/' + selected.boardNum
    },
    resizable: false,
    selectable: true,
    pageable: {
        refresh: true
    }
});

$("#free-board-rec-grid").kendoGrid({
    columns: [
        {
            field: "board_title",
            title: "제목",
            attributes: {style: 'text-overflow: ellipsis; overflow: hidden;'},
        }
    ]
});
$('#free-board-search-btn').kendoButton({
    themeColor: 'base'
});
$('#free-board-editor-btn').kendoButton({
    themeColor: 'info',
    click: () => {
        window.location = "/editor"
    }
});

class user {
    logout() {

        window.location.href = '/logout';
    }
}



