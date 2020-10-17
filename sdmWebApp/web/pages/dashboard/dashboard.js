var accountVersion = 0;
var refreshRate = 2000; //milli seconds
var numberOfColumns = 5
var USER_LIST_URL = buildUrlWithContextPath("users");
var ACCOUNT_URL = buildUrlWithContextPath("account");
var ZONES_URL = buildUrlWithContextPath("zones");

function refreshUsersList(users) {
    //clear all current users
    $("#usersList").empty();

    // rebuild the list of users: scan all users and add them to the list of users
    $.each(users || [], function(index, username) {
        console.log("Adding user #" + index + ": " + username);
        //create a new <option> tag with a value in it and
        //appeand it to the #userslist (div with id=userslist) element
        $('<li>' + username + '</li>').appendTo($("#usersList"));
    });
}

//entries = the added chat strings represented as a single string
function appendToAccountArea(account) {
//    $("#chatarea").children(".success").removeClass("success");
    
    // add the relevant entries
    $.each(account.m_AccountMovementsList || [], appendAccountEntry);
    
    // handle the scroller to auto scroll to the end of the chat area
 //   var scroller = $("#accountarea");
 //   var height = scroller[0].scrollHeight - $(scroller).height();
 //   $(scroller).stop().animate({ scrollTop: height }, "slow");
    $("#balance").textContent="Balance:" +account.m_Balance

}

function appendAccountEntry(index, entry){
    var entryElement = createAccountEntry(entry);
    $("#accountMovements").append(entryElement)
}

function createAccountEntry (accountMovement){
        var tr = $(document.createElement('tr'));
        var tdDate = $(document.createElement('td')).text(accountMovement.m_Date);
        var tdMovementType = $(document.createElement('td')).text(accountMovement.m_MovementType);
        var tdAmount = $(document.createElement('td')).text(accountMovement.m_Amount);
        var tdBalanceBefore = $(document.createElement('td')).text(accountMovement.m_BalanceBefore);
        var tdBalanceAfter = $(document.createElement('td')).text(accountMovement.m_BalanceAfter);

        tdDate.appendTo(tr);
        tdMovementType.appendTo(tr);
        tdAmount.appendTo(tr);
        tdBalanceBefore.appendTo(tr);
        tdBalanceAfter.appendTo(tr);

    return tr
}

/*

           data will arrive in the next form:
           {
              "accountDto": [
                             {
                              "balance":
                              accountMovements:
                              [

                                     accountOperationType:
                                      date:
                                      amount:
                                     balanceBefore:
                                     balanceAfter:
                                ]
                              ],
              "version":1
           }
           */

function ajaxUsersList() {
    $.ajax({
        url: USER_LIST_URL,
        success: function(users) {
            refreshUsersList(users);
        }
    });
}

//call the server and get the chat version
//we also send it the current chat version so in case there was a change
//in the chat content, we will get the new string as well
function ajaxAccountContent() {
    $.ajax({
        url: ACCOUNT_URL,
        data: "accountversion=" + accountVersion,
        dataType: 'json',
        success: function (data) {
            /*

             data will arrive in the next form:
             {
               {"accountDto":
               {"m_Balance":33.0,
               "m_AccountOperationList":
                    [{
                            "m_AccountOperationType":"load",
                            "m_Date":"12.3.2020",
                            "m_Amount":24.0,
                            "m_BalanceBefore":10.0,
                            "m_BalanceAfter":34.0}]}
               ,"version":1}
             }
             */
            console.log("Server account version: " + data.version + ", Current account version: " + accountVersion);
            if (data.version !== accountVersion) {
                accountVersion = data.version;
                appendToAccountArea(data.accountDto);
            }
            triggerAjaxContent();
        },
        error: function (error) {
            triggerAjaxContent();
        }
    });
}

//add a method to the button in order to make that form use AJAX
//and not actually submit the form
$(function() { // onload...do
    //add a function to the submit event
    $("#amountToLoadForm").submit(function() {
        $.ajax({
            method:'POST',
            data: $(this).serialize(),
            url: ACCOUNT_URL,
            timeout: 2000,
            dataType: 'json',
            error: function() {
                console.error("Failed to submit");
            },
            success: function(r) {
                //do not add the user string to the chat area
                //since it's going to be retrieved from the server
                //$("#result h1").text(r);
            }
        });

 //       $("#userstring").val("");
        // by default - we'll always return false so it doesn't redirect the user.
        return false;
    });
});

function triggerAjaxContent() {
    setTimeout(ajaxAccountContent, refreshRate);
    setTimeout(refreshZoneList, refreshRate);
    setTimeout(ajaxUsersList,refreshRate)

}

//activate the timer calls after the page is loaded
$(function() {

    //The users list is refreshed automatically every second
  //  setInterval(ajaxUsersList, refreshRate);
    
    //The chat content is refreshed only once (using a timeout) but
    //on each call it triggers another execution of itself later (1 second later)
    triggerAjaxContent();
    if(!isCustomer())
    {
        $('#loadMoney').hide()
    }
    else
    {
        $('#uploadForm').hide()
    }

});

$( function() {
    $( "#datepicker" ).datepicker();
} )

$(function() { // onload...do
    $("#uploadForm").submit(function() {
        var file = this[0].files[0];
        var formData = new FormData();
        formData.append("fileInput", file);

        $.ajax({
            method:'POST',
            data: formData,

            url: ZONES_URL,
            processData: false, // Don't process the files
            contentType: false, // Set content type to false as jQuery will tell the server its a query string request
            timeout: 4000,
            error: function(e) {
                console.error("Failed to submit");
                $("#result").text("Failed to get result from server " + e);
            },
            success: function(r) {
                loadFileCallback(r);
            }
        });

        // return value of the submit operation
        // by default - we'll always return false so it doesn't redirect the user.
        return false;
    })
})



function loadFileCallback(json) {
    if (json.isLoaded) {
        alert("Load file Success !!");
        refreshZoneList();
        clearFileInput();
    }
    else {
        clearFileInput();
        alert(json.errorMessage);
    }
}

function refreshZoneList() {
    $.ajax
    (
        {
            url: ZONES_URL,
            success: function(zones) {
                refreshZoneListCallback(zones);
            }
        }
    )
}

function refreshZoneListCallback(zones) {
    var zoneTable = $('#zonesTable tbody');
    zoneTable.empty();

    $.each(zones || [], function(index, zone){
        var tr = $(document.createElement('tr'));
        var tdOwnerName = $(document.createElement('td')).text(zone.ownerName);
        var tdZoneName = $(document.createElement('td')).text(zone.name);
        var tdNumberOfItems = $(document.createElement('td')).text(zone.numberOfItems);
        var tdNumberOfStores = $(document.createElement('td')).text(zone.numberOfStores);
        var tdNumberOfOrders = $(document.createElement('td')).text(zone.numberOfOrders);
        var tdAveragePriceOfOrdersWithoutDelivery = $(document.createElement('td')).text(zone.averagePriceOfOrdersWithoutDelivery);

        tdOwnerName.appendTo(tr);
        tdZoneName.appendTo(tr);
        tdNumberOfItems.appendTo(tr);
        tdNumberOfStores.appendTo(tr);
        tdNumberOfOrders.appendTo(tr);
        tdAveragePriceOfOrdersWithoutDelivery.appendTo(tr);
        tr.click(function() {
            setCurrentZone(zone.name);
            location.href = buildUrlWithContextPath("pages/zone/zone.html");
        });
        $("#zonesTable").append(tr)

    });
}

function clearFileInput() {
    document.getElementsByName("fileInput")[0].value=""
}
