$(document).ready(function () {
    var len = $(".address").text().trim().length;
    if (len < 12) { $('.addrs-text').text("Click here to load full address!");}
    $(".address").click(function (event) {
        if(len<12)
        {
            let searchParams = new URLSearchParams(window.location.search);
            if (searchParams.has('pid')) {
                var parameters = { pid: searchParams.get('pid') };
                
                $.get('/trigger', parameters, function (data) {
                    $('.addrs-text').html(data);
                });
            }
            event.preventDefault();
        }      
    });
});