var alertsVersion = 0
$(function() {
triggerAjaxAlerts()
})

function triggerAjaxAlerts()
{
    setTimeout(getAlerts,5000)
}

function getAlerts()
{
    $.ajax({
        url: buildUrlWithContextPath("alerts"),
        data: "alertsVersion=" + alertsVersion,
        success: function (data) {
            console.log("Server alerts version: " + data.version + ", Current alerts version: " + alertsVersion);
            if (data.version !== alertsVersion) {
                alertsVersion = data.version;
                $.each(data.alerts || [], function(index, alertToDisplay) {
                    var details = "The user: \"" + alertToDisplay.ownerName + "\" created a new store named: \"" +alertToDisplay.storeName +"\" in one of your zones"+
                        "\nAdditional details about the new store:\n"
                    +"Location: (" +alertToDisplay.location.x +"," + alertToDisplay.location.y + ")\n"
                    +"Available items related to the total items in the zone: " + alertToDisplay.availableItemsFromAllItems
                    alert(details)
                })
        }
            triggerAjaxAlerts()
        },
        error: function (error) {
            console.error(error);
            triggerAjaxAlerts()
        }
    });
}