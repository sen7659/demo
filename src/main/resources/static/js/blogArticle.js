var $;
layui.use(['jquery'], function () {
    $ = layui.jquery;
    article.Init($);//初始化共用js

});
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
};

var page = 1; //设置首页页码
var limit = 5;  //设置一页显示的条数
var total;





window.onload=function () {
    NProgress.done();
    loadData();    //请求数据
    getPage();     //分页操作
};

function seach() {
    loadData();    //请求数据
    getPage();     //分页操作
}

function seach2(str) {
    page=1;
    limit=5;
    loadData(str);    //请求数据
    getPage(str);     //分页操作
}

function loadData(str) {
    $.ajax({
        url: "/daily/query",
        type: "post",
        async: false,
        dataType: "json",
        data: {
            page: page,
            limit: limit,
            title: $("#searchtxt").val(),
            interest:str
        },
        success: function (res) {
            $("#interest").val("");
            total = res.count;  //设置总条数
            var i = 0;
            var dateStr;
            $("#div1").html("");
            $("#seachTable").html("");
            document.body.scrollTop = 0;
            document.documentElement.scrollTop = 0;
            if (res.count==0){
                $("#div1").append("<a>暂时没有此类文章</a>");
            }

            $.each(res.data, function () {
                dateStr = res.data[i].date.toString();

                $("#div1").append(
                    "               <section class=\"article-item zoomIn article\">\n" +
                    "               <h5 class=\"title\">\n" +
                    "               <span class=\"fc-blue\">【原创】</span>\n" +
                    "               <a>" + res.data[i].title + "</a>\n" +
                    "               </h5>\n" +
                    "               <div class=\"time\">\n" +
                    "               <span class=\"day\" id='day'>" + dateStr.substring(8, 10) + "</span>\n" +
                    "               <span class=\"month fs-18\"><span class=\"fs-14\" id='month'> " + dateStr.substring(5, 7) + 'mon' + "</span></span>\n" +
                    "               <span class=\"year fs-18 ml10\" id='year'>" + dateStr.substring(0, 4) + "</span>\n" +
                    "               </div>\n" +
                    "               <div  class=\"content\">\n" +
                    "               <a class=\"cover img-light\">\n" +
                    "               <img  src=" + res.data[i].pictureUrl + ">\n" +
                    "               </a>\n" +
                    "               <div style='height: 180px;overflow: hidden'> " + res.data[i].content + " " +
                    "               </div>" +
                    "               </div>\n" +
                    "               <div class=\"read-more\">\n" +
                    "               <a href=\"/blog/read?id=" + res.data[i].id + "\" class=\"fc-black f-fwb\">继续阅读</a>\n" +
                    "               </div>\n" +
                    "               <aside class=\"f-oh footer\">\n" +
                    "               <div class=\"f-fl tags\">\n" +
                    "               <span class=\"fa fa-tags fs-16\"></span>\n" +
                    "               <a class=\"tag\">" + res.data[i].interest + "</a>\n" +
                    "               </div>\n" +
                    "               </aside>\n" +
                    "               </section>");
                ++i;
            });
            i = 0;
        }
    });
}

function getPage(str) {
    layui.use('laypage', function () {
        var laypage = layui.laypage;

        //执行一个laypage实例
        laypage.render({
            elem: 'test1'
            , count: total //数据总数，从服务端得到
            , limit: limit
            , jump: function (obj, first) {
                page = obj.curr;  //改变当前页码
                limit = obj.limit;
                if (!first) {
                    loadData(str);  //加载数据
                }
            }
        })
    })
}