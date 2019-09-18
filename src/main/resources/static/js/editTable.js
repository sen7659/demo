$(document).keypress(function (e) {
    if ((e.keyCode || e.which) == 13) {
        $("#search").click();  //按钮的id
    }
});


layui.use(['layedit', 'laydate', 'table', 'upload'], function () {
    var table = layui.table;
    var layer = layui.layer; //获取当前窗口的layer对象
    var layedit = layui.layedit;
    var upload = layui.upload;
    var dataRes;
    var editIndex;
    //执行渲染
    table.render({
        elem: '#table'
        , height: 540
        , url: '/daily/query' //数据接口
        , page: true //开启分页
        , toolbar: true
        , cols: [[ //表头
            {field: 'pictureUrl', title: '封面图片', align: "center", width: "15%", templet: '#imgtmp'}
            , {field: 'title', title: '标题', width: "15%"}
            , {field: 'date', title: '日期', width: "15%"}
            , {field: 'interest', title: '类型', width: "15%"}
            , {field: 'content', title: '内容', width: "25%"}
            , {title: '操作', toolbar: '#barDemo'}
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

    //监听工具条
    table.on('tool(test)', function (obj) {
        dataRes = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                $.ajax({
                    url: "/daily/dele",
                    type: "post",
                    dataType: "json",
                    data: {
                        id: dataRes.id
                    },
                    success: function (res) {
                        if (res.code == 1) {
                            layer.alert(res.msg)
                            obj.del();
                        } else {
                            layer.alert(res.msg)
                        }
                    }
                });
                layer.close(index);
            });
        } else if (obj.event === 'edit') {
            layer.open({
                type: 1,//类型
                area: ['70%', '70%'],//定义宽和高
                title: '查看详细信息',//题目
                content: $('#editFrom'),//打开的内容
                maxmin: true
            });

            //上传图片,必须放在 创建一个编辑器前面
            layedit.set({
                uploadImage: {
                    url: '/daily/uploadImg' //接口url
                    , type: 'post' //默认post
                }
            });

            //创建一个编辑器
             editIndex = layedit.build('LAY_demo_editor', {
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
                    , 'code'
                    , 'image'
                    , 'big'
                ]
            });

            $("#big").click(function(event) {
                $(".layui-layedit").css({"height":"1000px"});
                $("#LAY_layedit_1").css({"height":"960px"});
            });

            // layer.alert('编辑行：<br>' + JSON.stringify(data))
            $('#demo1').attr('src', dataRes.pictureUrl); //图片链接（base64）
            $("#picDiv").show();
            $("#date").val(dataRes.date);
            $("#title").val(dataRes.title);
            $('#select').val(dataRes.interest);
            $('#LAY_demo_editor').val(dataRes.content);

        }
    });


    //执行实例
    var uploadInst = upload.render({
        elem: '#pic' //绑定元素
        , url: '/daily/updata' //上传接口
        , auto: false //选择文件后不自动上传
        , bindAction: '#done' //指向一个按钮触发上传
        , multiple: false
        , data: {
            id: function () {
                return dataRes.id
            },
            pictureUrl: function () {
                return dataRes.pictureUrl
            },
            title: function () {
                return $("#title").val()
            },
            date: function () {
                return $("#date").val()
            },
            interest: function () {
                return $("#select").val()
            },
            content: function () {
                return layedit.getContent(editIndex)
            }
        }
        , choose: function (obj) {
            obj.preview(function (index, file, result) {
                $('#demo1').attr('src', result); //图片链接（base64）
                $("#picDiv").show()
            });
        }
        , done: function (res) {
            //上传完毕回调
            layer.alert(res.msg);
        }
        , error: function (res) {
            //请求异常回调
            layer.alert(res.msg);
        }
    });

});


layui.use('laydate', function () {
    var laydate = layui.laydate;

    laydate.render({
        elem: '#date' //指定元素
    });

    laydate.render({
        elem: '#dateInput' //指定元素
        , range: true
    });
});
