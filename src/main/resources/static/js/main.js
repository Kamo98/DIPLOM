//Клик по любой ссылке в табах
//Скрывает все вкладки и удаляет выделения во на всех ссылках
$('.tabs-menu a').click(function(e){
    $('.tabs-menu a').removeClass('active');
    $('#containerOfTabs>div').hide();
    e.preventDefault();
});

$(document).ready(function(){
    $("#submit").click(function(){
        $("#form").submit();
    });
})

function buttonEditFioClick ()
{
    var div = document.getElementById ("editFIO");
    var button = document.getElementById ("myButtonFio");

    if (div.contentEditable == "true") {
        div.contentEditable = "false";
        button.className = "btn btn-dark";
        $.ajax({
            type : 'get',
            url : "/admin/editTeacher/",
            data : {'fio' : div.textContent},
            dataType: "json",
            success : function() {
                console.log("фио было успешно изменено");
            }
        });
    }
    else  {
        div.contentEditable = "true";
        button.className = "btn btn-success";
    }

}

