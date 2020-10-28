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
        data: "alertsVersion=" + getCurrentAlertVersion(),
        success: function (data) {
            console.log("Server alerts version: " + data.version + ", Current alerts version: " + getCurrentAlertVersion());
            if (data.version !== getCurrentAlertVersion()) {
                setCurrentAlertVersion(data.version);
                $.each(data.alerts || [], function(index, alertToDisplay) {
                    switch (alertToDisplay.alertType) {
                        case 'NEW_STORE':
                            createNewStoreAlert(alertToDisplay)
                            break;
                        case  'NEW_ORDER':
                            createNewOrderAlert(alertToDisplay)
                            break;
                        case 'FEEDBACK':
                            createFeedbackAlert(alertToDisplay)
                            break
                    }

                })
        }
            triggerAjaxAlerts()
        },
        error: function (error) {
            console.error(error);
            triggerAjaxAlerts()
        }
    });
    function createNewStoreAlert(alertToDisplay)
    {
        var details = "The user: \"" + alertToDisplay.ownerName + "\" created a new store named: \"" +alertToDisplay.storeName +"\" in one of your zones"+
            "\nAdditional Details About The New Store:\n"
            +"Location: (" +alertToDisplay.location.x +"," + alertToDisplay.location.y + ")\n"
            +"Available Items Related To The Total Items In The Zone: " + alertToDisplay.availableItemsFromAllItems
        alert(details)
    }

    function createNewOrderAlert(alertToDisplay)
    {
        var details = "The user: \"" + alertToDisplay.ownerName + "\" just ordered from the store: \"" +alertToDisplay.storeName +"\" in one of your zones"+
            "\nAdditional Details About The Order:\n"
            +"Order Id:" + alertToDisplay.orderId + "\n"
            +"Number Of Items: " + alertToDisplay.numberOfDifferentItems + "\n"
            +"Total Price Of Items: " + show2DecimalPlaces(alertToDisplay.totalPriceOfItems) + "\n"
            +"Delivery Price: " + show2DecimalPlaces(alertToDisplay.deliveryPrice) + "\n"
        alert(details)
    }

    function createFeedbackAlert(alertToDisplay)
    {
        var details = "The user: \"" + alertToDisplay.ownerName + "\" wrote a feedback for the store: \"" +alertToDisplay.storeName +"\" in one of your zones"+
            "\nAdditional Details About The Feedback:\n"
            +"Rank:" + alertToDisplay.rank + "\n"
            +"Date: " + alertToDisplay.date + "\n"
           if(alertToDisplay.description === undefined || alertToDisplay.description === null || alertToDisplay.description==="")
           {
               details+= "Description: " + alertToDisplay.description
           }
        alert(details)
    }
}
