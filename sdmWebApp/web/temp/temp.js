var accountVersion = 0;
var refreshRate = 2000; //milli seconds
var numberOfColumns = 5
var USER_LIST_URL = buildUrlWithContextPath("users");
var ACCOUNT_URL = buildUrlWithContextPath("account");
var ZONES_URL = buildUrlWithContextPath("zones");

//function refreshUsersList(users) {
//    //clear all current users
//    $("#usersList").empty();
//
//    // rebuild the list of users: scan all users and add them to the list of users
//    $.each(users || [], function(index, username) {
//        console.log("Adding user #" + index + ": " + username);
//        //create a new <option> tag with a value in it and
//        //appeand it to the #userslist (div with id=userslist) element
//        $('<li>' + username + '</li>').appendTo($("#usersList"));
//    });
//}
//
////entries = the added chat strings represented as a single string
//function appendToAccountArea(account) {
////    $("#chatarea").children(".success").removeClass("success");
//
//    // add the relevant entries
//    $.each(account.m_AccountMovementsList || [], appendAccountEntry);
//
//    // handle the scroller to auto scroll to the end of the chat area
// //   var scroller = $("#accountarea");
// //   var height = scroller[0].scrollHeight - $(scroller).height();
// //   $(scroller).stop().animate({ scrollTop: height }, "slow");
//    $("#balance").textContent="Balance:" +account.m_Balance
//
//}
//
//function appendAccountEntry(index, entry){
//    var entryElement = createAccountEntry(entry);
//    $("#accountMovements").append(entryElement)
//}
//
//function createAccountEntry (accountMovement){
//        var tr = $(document.createElement('tr'));
//        var tdDate = $(document.createElement('td')).text(accountMovement.m_Date);
//        var tdMovementType = $(document.createElement('td')).text(accountMovement.m_MovementType);
//        var tdAmount = $(document.createElement('td')).text(accountMovement.m_Amount);
//        var tdBalanceBefore = $(document.createElement('td')).text(accountMovement.m_BalanceBefore);
//        var tdBalanceAfter = $(document.createElement('td')).text(accountMovement.m_BalanceAfter);
//
//        tdDate.appendTo(tr);
//        tdMovementType.appendTo(tr);
//        tdAmount.appendTo(tr);
//        tdBalanceBefore.appendTo(tr);
//        tdBalanceAfter.appendTo(tr);
//
//    return tr
//}
//
///*
//
//           data will arrive in the next form:
//           {
//              "accountDto": [
//                             {
//                              "balance":
//                              accountMovements:
//                              [
//
//                                     accountOperationType:
//                                      date:
//                                      amount:
//                                     balanceBefore:
//                                     balanceAfter:
//                                ]
//                              ],
//              "version":1
//           }
//           */
//
//function ajaxUsersList() {
//    $.ajax({
//        url: USER_LIST_URL,
//        success: function(users) {
//            refreshUsersList(users);
//        }
//    });
//}
//
////call the server and get the chat version
////we also send it the current chat version so in case there was a change
////in the chat content, we will get the new string as well
//function ajaxAccountContent() {
//    $.ajax({
//        url: ACCOUNT_URL,
//        data: "accountversion=" + accountVersion,
//        dataType: 'json',
//        success: function (data) {
//            /*
//
//             data will arrive in the next form:
//             {
//               {"accountDto":
//               {"m_Balance":33.0,
//               "m_AccountOperationList":
//                    [{
//                            "m_AccountOperationType":"load",
//                            "m_Date":"12.3.2020",
//                            "m_Amount":24.0,
//                            "m_BalanceBefore":10.0,
//                            "m_BalanceAfter":34.0}]}
//               ,"version":1}
//             }
//             */
//            console.log("Server account version: " + data.version + ", Current account version: " + accountVersion);
//            if (data.version !== accountVersion) {
//                accountVersion = data.version;
//                appendToAccountArea(data.accountDto);
//            }
//            triggerAjaxContent();
//        },
//        error: function (error) {
//            triggerAjaxContent();
//        }
//    });
//}
//
////add a method to the button in order to make that form use AJAX
////and not actually submit the form
//$(function() { // onload...do
//    //add a function to the submit event
//    $("#amountToLoadForm").submit(function() {
//        $.ajax({
//            method:'POST',
//            data: $(this).serialize(),
//            url: ACCOUNT_URL,
//            timeout: 2000,
//            dataType: 'json',
//            error: function() {
//                console.error("Failed to submit");
//            },
//            success: function(r) {
//                //do not add the user string to the chat area
//                //since it's going to be retrieved from the server
//                //$("#result h1").text(r);
//            }
//        });
//
// //       $("#userstring").val("");
//        // by default - we'll always return false so it doesn't redirect the user.
//        return false;
//    });
//});

function triggerAjaxContent() {
  //  setTimeout(ajaxAccountContent, refreshRate);
    setTimeout(refreshZoneDetails, refreshRate);
 //   setTimeout(ajaxUsersList,refreshRate)

}

//activate the timer calls after the page is loaded
$(function() {

    //The users list is refreshed automatically every second
  //  setInterval(ajaxUsersList, refreshRate);
    
    //The chat content is refreshed only once (using a timeout) but
    //on each call it triggers another execution of itself later (1 second later)
    triggerAjaxContent();
 //   if(!isCustomer())
 //   {
 //       $('#loadMoney').hide()
 //   }
//    else
//    {
//        $('#uploadForm').hide()
 //   }

});

function refreshZoneDetails() {
    $.ajax
    (
        {
            url: ZONES_URL,
            data: "zoneName=" + getCurrentZone(),
            success: function(zone) {
                loadStorageItemsList(zone.m_StorageItemsDtos);
                loadStoresList(zone.m_StoresDtos);

            }
        }
    )
}

//{
//"m_StorageItemsDtos":[
//    {
//        "m_ItemDto":{
//            "m_ItemId":1,
//            "m_ItemName":"Ketshop",
//            "m_PurchaseForm":"QUANTITY",
//            "m_FromDiscount":false
//        },
//        "m_StoresSellIt":1,
//        "m_AvgPrice":20.0,
//        "m_Sales":0.0
//    },
//    {
//        "m_ItemDto":{
//            "m_ItemId":2,
//            "m_ItemName":"Banana",
//            "m_PurchaseForm":"WEIGHT",
//            "m_FromDiscount":false
//        },
//        "m_StoresSellIt":2,
//        "m_AvgPrice":15.0,
//        "m_Sales":0.0
//    }],
//    "m_StoresDtos":[
//
// }

//    {
//        "m_ItemDto":{
//            "m_ItemId":1,
//            "m_ItemName":"Ketshop",
//            "m_PurchaseForm":"QUANTITY",
//            "m_FromDiscount":false
//        },
//        "m_StoresSellIt":1,
//        "m_AvgPrice":20.0,
//        "m_Sales":0.0
//    },
function loadStorageItemsList(items) {
    $.each(items || [], function(index, item){
        var tr = $(document.createElement('tr'));
        var tdID = $(document.createElement('td')).text(item.m_ItemDto.m_ItemId);
        var tdName = $(document.createElement('td')).text(item.m_ItemDto.m_ItemName);
        var tdPurchaseForm = $(document.createElement('td')).text(item.m_ItemDto.m_PurchaseForm);
        var tdStoresSellIt = $(document.createElement('td')).text(item.m_StoresSellIt);
        var tdAveragePrice = $(document.createElement('td')).text(item.m_AvgPrice);
        var tdSells = $(document.createElement('td')).text(item.m_Sales);

        tdID.appendTo(tr);
        tdName.appendTo(tr);
        tdPurchaseForm.appendTo(tr);
        tdStoresSellIt.appendTo(tr);
        tdAveragePrice.appendTo(tr);
        tdSells.appendTo(tr);
        $("#storageItemsTable").append(tr)
    });
}
//          "m_OrdersDto":[
//          ],
//          "m_DeliveriesIncome":0.0,
//          "m_Items":[
//             {
//                "m_ItemId":1,
//                "m_ItemName":"Ketshop",
//                "m_PurchaseForm":"QUANTITY",
//                "m_Price":20.0,
//                "m_AmountOfSell":0.0,
//                "m_FromDiscount":false
//             },
//             {
//                "m_ItemId":2,
//                "m_ItemName":"Banana",
//                "m_PurchaseForm":"WEIGHT",
//                "m_Price":10.0,
//                "m_AmountOfSell":0.0,
//                "m_FromDiscount":false
//             }
//          ],
//       ,
//          "m_Location":{
//             "x":3,
//             "y":4
//          },
//          "m_PPK":30.0,
//          "m_Name":"super baba",
//          "m_Id":1,
//          "m_OwnerName":"asafza@mta.ac.il",
//          "m_StoreFeedbackDtos":[
//
//          ]
//       }
function loadStoresList(stores) {
    $.each(stores || [], function(index, store){
        var tr = $(document.createElement('tr'));
        var tdID = $(document.createElement('td')).text(store.m_Id);
        var tdName = $(document.createElement('td')).text(store.m_Name);
        var tdOwnerName = $(document.createElement('td')).text(store.m_OwnerName);
        var tdLocation = $(document.createElement('td')).text("("+store.m_Location.x +"," +store.m_Location.y +")");
        var tdPPK = $(document.createElement('td')).text(store.m_PPK);
        var tdNumberOfOrders = $(document.createElement('td')).text(store.m_OrdersDto.length);
        var itemsIncomes = 0
        $.each(store.m_OrdersDto|| [], function(index, order) {
            itemsIncomes += order.m_TotalItemsPrice
        });
        var tdIncomesFromItems = $(document.createElement('td')).text(store.itemsIncomes);
        var tdIncomesFromDeliveries = $(document.createElement('td')).text(store.m_DeliveriesIncome);

        tdID.appendTo(tr);
        tdName.appendTo(tr);
        tdOwnerName.appendTo(tr);
        tdLocation.appendTo(tr);
        tdPPK.appendTo(tr);
        tdNumberOfOrders.appendTo(tr);
        tdIncomesFromItems.appendTo(tr);
        tdIncomesFromDeliveries.appendTo(tr);

        tr.click(function() {
            var storesItemsTable = $('#storeItemsTable tbody');
            storesItemsTable.empty();
            $.each(store.m_Items || [], function(index, storeItem){
                var trItem = $(document.createElement('tr'));
                var tdID = $(document.createElement('td')).text(storeItem.m_ItemId);
                var tdName = $(document.createElement('td')).text(storeItem.m_ItemName);
                var tdPurchaseForm = $(document.createElement('td')).text(storeItem.m_PurchaseForm);
                var tdPrice = $(document.createElement('td')).text(storeItem.m_Price);
                var tdSoldSoFar = $(document.createElement('td')).text(storeItem.m_AmountOfSell);

                tdID.appendTo(trItem);
                tdName.appendTo(trItem);
                tdPurchaseForm.appendTo(trItem);
                tdPrice.appendTo(trItem);
                tdSoldSoFar.appendTo(trItem);
                $("#storeItemsTable").append(trItem)
            });
        });
        $("#storesTable").append(tr)
    });
}
