/**
 * Bolg main JS.
 * Created by waylau.com on 2017/3/9.
 */
"use strict";
//# sourceURL=main.js
function load(data){

    $("#rightContainer").html(data);

}


// DOM 加载完再执行
$(function() {

    // 菜单事件
    $(".ajaxButton").click(function() {

        $.ajax({
            url: "/complexNetwork/index/",
            type: 'POST',
            data:$('#userForm').serialize(),
            async:false,
            cache:false,
            beforeSend :function(xmlHttp) {
                xmlHttp.setRequestHeader("If-Modified-Since", "0");
                xmlHttp.setRequestHeader("Cache-Control", "no-cache");
            },
            success: function(data) {
                load(data);
                window.scrollTo(0,0);
            },
            error: function() {
                alert("error");
            }
        });
    });

    var e = document.createEvent("MouseEvents");
    e.initEvent("click", true, true);
    document.getElementById("ajaxButton").dispatchEvent(e)




});



