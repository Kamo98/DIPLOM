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

//function buttonEditFioClick ()
//{
//    var div = document.getElementById ("editFIO");
//    var button = document.getElementById ("myButtonFio");
//
//    if (div.contentEditable == "true") {
//        div.contentEditable = "false";
//        button.className = "btn btn-dark";
//        $.ajax({
//            type : 'get',
//            url : "/admin/editTeacher/",
//            data : {'fio' : div.textContent},
//            dataType: "json",
//            success : function() {
//                console.log("фио было успешно изменено");
//            }
//        });
//    }
//    else  {
//        div.contentEditable = "true";
//        button.className = "btn btn-success";
//    }
//
//}

$(".myButtonFio").click(function(e){
    var editBtn = $(this);                              //Кнопка с редактированием
    var itemDivTeach = editBtn.parent().parent();     //Элемент списка с преподом
    var editInputFIO = itemDivTeach.find(".editInputFIO");  //Инпут для редактирования
    var textFIO = itemDivTeach.find(".textFIO");

    if (editBtn.hasClass("myButtonFioEdit")) {      //Завершаем редактирование
        var stringFIO = editInputFIO.val().trim();


        var idTeacher = itemDivTeach.attr("id").split('_')[1];
        textFIO.html('<i class="fa fa-user" aria-hidden="true"></i>&nbsp;' + stringFIO);
        $.ajax({
           type : 'post',
           url : "/admin/editTeacher/",
           data : {'fio' : stringFIO, 'idTeacher' : idTeacher},
           dataType: "json",
           success : function() {
               console.log("фио было успешно изменено");
           }
       });

        editInputFIO.hide();
        textFIO.show(200);
        editBtn.html('<i class="fa fa-pencil fa-fw"></i>');
        editBtn.removeClass("myButtonFioEdit");
        editBtn.removeClass("btn-success");
        editBtn.addClass("btn-dark");
        itemDivTeach.removeClass("editable-div-item");
    } else {        //Начинаем редактирование

        textFIO.hide();
        editInputFIO.show(400);
        editBtn.html('<i class="fa fa-floppy-o" aria-hidden="true"></i>');
        editBtn.addClass("myButtonFioEdit");
        editBtn.removeClass("btn-dark");
        editBtn.addClass("btn-success");
        itemDivTeach.addClass("editable-div-item");
    }

     e.preventDefault();
});