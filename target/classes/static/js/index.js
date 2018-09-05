$(function () {
    $('#myTab a:last').tab('show');//初始化显示哪个tab

    $('#myTab a').click(function (e) {
        e.preventDefault();//阻止a链接的跳转行为
        $(this).tab('show');//显示当前选中的链接及关联的content
    })
});


$(document).ready(function () {
    $.ajax({
        type:"GET",
        url:'/master',
        async:true,
        dataType:'json',
        success:function (data) {
            var html='';
            for (var i=0;i<data.length;i++){
                var ls=data[i];
                html +="<tr class=\"error\">" +
                    "<td></td>" +
                    "<td class=\"servername\">"+ls['servername']+"</td>\n" +
                    "<td class=\"email\">"+ls['email']+"</td>" +
                    "<td class=\"path\">"+ls['path']+"</td>" +
                    "</tr>"
            }
            $(".tbody").html(html);
        },
        error:function () {
            console.log("失败请重试！");
        }
    })
});

function f_ind() {
    if ($("#find_server").val()===''){
        alert("请输入您想查询的servername!");
    }else {
        $.ajax({
            async:true,
            type:"POST",
            url:"/search",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data:{
                servername:$("#find_server").val()
            },
            dataType:"json",
            success:function (data) {
                var html='';
                for (var i=0;i<data.length;i++){
                    var ls=data[i];
                    html +="<tr class=\"error\">" +
                        "<td></td>" +
                        "<td class=\"servername\">"+ls['servername']+"</td>\n" +
                        "<td class=\"email\">"+ls['email']+"</td>" +
                        "<td class=\"path\">"+ls['path']+"</td>" +
                        "</tr>"
                }
                $(".tbody").html(html);
            },
            error:function () {
                console.log("失败请重试！");
            }
        });
    }
}

function my_server() {
    $.ajax({
        async:true,
        type:"POST",
        url:"/myService",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        data:{
            email:localStorage.getItem("email")
        },
        dataType:"json",
        success:function (data) {
            window.location.href="myserver.html";
            var html='';
            for (var i=0;i<data.length;i++){
                var ls=data[i];
                html +="<tr class=\"error\">" +
                    "<td></td>" +
                    "<td class=\"servername\">"+ls['servername']+"</td>\n" +
                    "<td class=\"clicknum\">"+ls['clicknum']+"</td>"
                    "</tr>"
            }
            $(".mybody").html(html);
        }
    });
}

function my_share() {
    $.ajax({
        async:true,
        type:"POST",
        url:"localhost:8080/earn/search",
        data:"uuid="+localStorage.getItem("email"),
        dataType:"json",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        success:function (data) {
            window.location.href="myshare.html";
            var html='';
            for (var i=0;i<data.length;i++){
                var ls=data[i];
                html +="<tr class=\"error\">" +
                    "<td></td>" +
                    "<td class=\"servername\">"+ls['id']+"</td>\n" +
                    "<td class=\"money\">"+ls['money']+"</td>" +
                    "</tr>"
            }
            $(".mymoney").html(html);
        }
    })
}

function server_content() {
    $.ajax({
        async:true,
        type:"POST",
        url:"localhost:8080/earn/search",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType:"json",
        data:{
            path:$(".path").val()
        },
        success:function (data) {
            $("#index").hide();
            $("#index1").show();
            var html='';
            var ls=data;
            html+="<div class='path_div'>"+ls;
            $(".path_div").html(html);
        }

    })
}

function close_div() {
    $("#index1").hide();
    $("#index").show();
}
