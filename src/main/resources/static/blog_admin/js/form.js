layui.use(['layedit', 'laydate', 'upload'], function () {
    var layer = layui.layer,
        layedit = layui.layedit,
        laydate = layui.laydate,
        upload = layui.upload;


    //上传图片,必须放在 创建一个编辑器前面
    layedit.set({
        uploadImage: {
            url: '/daily/uploadImg' //接口url
            ,type: 'post' //默认post
        }
    });

    //创建一个编辑器
    var editIndex = layedit.build('content', {
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

            , '|' //分割线

            , 'face' //表情
            , 'code'
            , 'image' //插入图片
            , 'big'
        ]
    });

    $("#big").click(function(event) {
        $(".layui-layedit").css({"height":"1000px"});
        $("#LAY_layedit_1").css({"height":"960px"});
    });


    //执行实例
    var uploadInst = upload.render({
        elem: '#pic' //绑定元素
        , url: '/daily/upload' //上传接口
        , auto: false //选择文件后不自动上传
        , bindAction: '#done' //指向一个按钮触发上传
        , multiple: false
        , data: {
            title: function () {
                return $("#title").val()
            },
            date: function () {
                return $("#test1").val()
            },
            interest: function () {
                return $("#interest").val()
            },
            content: function () {
                return layedit.getContent(editIndex)
            }
        }
        , choose: function (obj) {
            obj.preview(function (index, file, result) {
                $('#demo1').attr('src', result); //图片链接（base64）
                $("#picDiv").show()
                $("#complete").val("1")
            });
        }
        , done: function (res) {
            //上传完毕回调
            layer.alert(res.msg);
        }
        ,error: function(){
            //演示失败状态，并实现重传
            var demoText = $('#demoText');
            demoText.html('<span style="color: #FF5722;">图片必须小于1mb,请重新上传</span>');
        }
    });

    laydate.render({
        elem: '#test1' //指定元素
    });
});
