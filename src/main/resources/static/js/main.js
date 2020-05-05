//Клик по любой ссылке в табах
//Скрывает все вкладки и удаляет выделения во на всех ссылках
$('.tabs-menu a').click(function(e){
    $('.tabs-menu a').removeClass('active');
    $('#containerOfTabs>div').hide();
    e.preventDefault();
});