$(document).keypress(function(e) {

    if((e.keyCode || e.which)==13) {
        $("#logBtn").click();  //login_btn登录按钮的id
    }

});

function loginCe() {
    var acc= $("#account").val();
    var pas=$("#password").val();
    if(acc==""||pas==""){
        layer.msg('必填项不能为空', {icon: 5});
        return;
    }
    $.ajax({
        url: "/login/checking",
        type: "post",
        dataType: "json",
        data: {
            account: acc,
            password: pas
        },
        success: function (res) {
            if(res.code==1){
                layer.msg(res.msg, {icon: 1});
                window.location.href=("//"+location.host+"/"+res.url)
            }else {
                layer.msg(res.msg, {icon: 5})
            }
        }
    });
}