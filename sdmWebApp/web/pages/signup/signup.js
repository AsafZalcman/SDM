$(function() {
    $("#signup-form").submit(function () {
            $.ajax({
                data: $(this).serialize(),
                url: this.action,
                timeout: 2000,
                method: 'POST',
                error: function () {
                    console.error("Failed to send ajax");
                },
                success: function (response) {
                    if (response.status !== 200) {
                        //handle error
                        console.log('Im in the response.status != 200');
                    }
                }
            })
    });
});