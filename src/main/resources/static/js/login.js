var button_click=document.getElementById("button_click");
button_click.click=function () {
    var temp = document.getElementById("inputEmail3");
    //对电子邮件的验证
    var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    if(!myreg.test(temp.value))
    {
        alert('请输入有效的Email！');
        myreg.focus();
        return false;
    }else {
        $.ajax({
            async:true,
            type:"POST",
            url:"/login",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data:{
                email:$("#inputEmail3").val(),
                passwd:$("#inputPassword3").val()
            },
            dataType:"json",
            success:function () {
                var emailname=$("#inputEmail3").val();
                localStorage.setItem("email",emailname);
                window.location.href="index2.html";
            }
        })
    }
};
