
layui.use('table', function () {
    var table = layui.table;
    //执行渲染
    table.render({
        elem: '#table'
        , height: 540
        , url: '/daily/query' //数据接口
        , page: true //开启分页
        , toolbar: true
        , done: function (res, curr, count) {
            $(".layui-table-view").css("width", "100%");
        }
        , cols: [[ //表头
            {field: 'pictureUrl', title: '封面图片', align: "center", width: "15%", templet: '#imgtmp'}
            , {field: 'title', title: '标题', width: "15%"}
            , {field: 'date', title: '日期', width: "15%"}
            , {field: 'interest', title: '类型', width: "15%"}
            , {field: 'content', title: '内容', width: "40%"}
        ]]
    });


    //这里以搜索为例
    $('#search').on('click', function () {
        table.reload("table", {
            where: { //设定异步数据接口的额外参数，任意设
                title: $("#titleInput").val(),
                interest: $("#typeInput").val(),
                SelectDate: $("#dateInput").val()
            }
            , page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });
});


layui.use('laydate', function () {
    var laydate = layui.laydate;
    //日期范围
    laydate.render({
        elem: '#dateInput'
        , range: true
    });
});
