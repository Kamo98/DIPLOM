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
    })
})



//Копипаст с документации bootstrap
//https://getbootstrap.com/docs/4.0/components/forms/
// Example starter JavaScript for disabling form submissions if there are invalid fields
  window.addEventListener('load', function() {
    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.getElementsByClassName('needs-validation');
    // Loop over them and prevent submission
    var validation = Array.prototype.filter.call(forms, function(form) {
      form.addEventListener('submit', function(event) {
        if (form.checkValidity() === false) {
          event.preventDefault();
          event.stopPropagation();
        }
        form.classList.add('was-validated');
      }, false);
    });
  }, false);