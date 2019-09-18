layui.use(['form','layedit','laydate'], function () {
    var form = layui.form,
        layer = layui.layer,
        layedit = layui.layedit,
        laydate = layui.laydate,
        finalData;

    //创建一个编辑器
    var editIndex = layedit.build('LAY_demo_editor', {
        tool: ['strong' //加粗
            , 'italic' //斜体
            , 'underline' //下划线
            , 'del' //删除线

            , '|' //分割线

            , 'left' //左对齐
            , 'center' //居中对齐
            , 'right' //右对齐
            , 'link' //超链接
            , 'unlink' //清除链接
            , 'face' //表情
        ]
    });
    //自定义验证规则
    form.verify({
        title: function (value) {
            if (value.length < 2) {
                return '标题至少得2个字符啊';
            }
        },
        content: function (value) {
            layedit.sync(editIndex);
        }
    });

    //监听提交
    form.on('submit(demo1)', function (data) {
        finalData = data.field;
        console.log(JSON.stringify(finalData));
        $.ajax({
            url: "/daily/upload",
            type: "post",
            dataType: "json",
            data: {
                title: finalData.title,
                content: finalData.content,
                date: finalData.date,
                interest: finalData.interest
            },
            success: function (res) {
                layer.alert(res.msg)
            }
        });

    });

    laydate.render({
        elem: '#test1' //指定元素
    });
});
