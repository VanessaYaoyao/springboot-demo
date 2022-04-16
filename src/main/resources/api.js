function askVerifyCode(){
    $.get("http://47.102.215.61:8889/api/auth/verify-code",{
        email: $("#input-email").val()
    },function (data){
        alert(data.reason)
    })
}

function register(){
    $.post('http://47.102.215.61:8889/api/auth/register',{
        username: $("#input-username").val(),
        password: $("#input-password").val(),
        email: $("#input-email").val(),
        verify: $("#input-verify").val()
    }, function(data){
        if(data.code === 200){
            window.location = "/login.html"
        }else {
            alert(data.reason)
        }
    })
}

function login(){
    $.post('http://47.102.215.61:8889/api/auth/login',{
        username: $("#username").val(),
        password: $("#password").val()
    }, function(data){
        if (data.code === 200)
            window.location = "/index.html"
        else
            alert(data.reason)
    })
}

function logout(){
    $.get('http://47.102.215.61:8889/api/auth/logout',function(data){
        if (data.code === 200)
            window.location = "/login.html"
    })
}

function initUserInfo(){
    $.get("http://47.102.215.61:8889/api/user/info",function (data){
        if (data.code === 200){
            $("#profile-name").val(data.data.username)
        }else {
            window.location = "/login.html"
        }
    })
}