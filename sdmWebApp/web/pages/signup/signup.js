$(function() {
    $("#signup-form").submit(function () {
            $.ajax({
                data: $(this).serialize(),
                url: buildUrlWithContextPath("users"),
                method: 'POST',
                error: function (response) {
                    console.error("Failed to send ajax:" + response.responseText);
                },
                success: function () {
                        console.log('Redirect to dashboard');
                    setUserRole(document.getElementById('role').value)
                    setCurrentAlertVersion(0)
                    window.location.href = buildUrlWithContextPath("pages/dashboard/dashboard.html")
                    }
            })
        return false;
    });
});