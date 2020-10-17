function getContentOfSelectedBtn(selectedBtn) {
    let res = "";
    $('.zoneArea').hide()
    switch (selectedBtn){
        case 'makeAnOrder':
            res ="<h1> In Make An order </h1>";
            break;
        case 'rank':
            res ="<h1> In Rank </h1>";
            break;
        case 'customerOrderHistory':
            res ="<h1> In customerOrderHistory </h1>";
            break;
        case 'managerOrderHistory':
            res ="<h1> In managerOrderHistory </h1>";
            break;
        case 'feedback':
            res ="<h1> In feedback </h1>";
            break;
        case 'newStore':
            res ="<h1> In newStore </h1>";
            break;
        case  'details':
            res = "<h1>In Zone Details</h1>"
            loadZoneDetails()
            break;
    }
    return res;
}

function switchContent(evt, selectedBtn){
    $(".active").removeClass("active");
    evt.currentTarget.className += " active";
    $(".zone-content").empty();
    $(".zone-content").append(getContentOfSelectedBtn(selectedBtn))
}

$(function () {
    var customerMenuButtons = $("<button onclick=\"switchContent(event,'makeAnOrder')\">Make an order</button>\n" +
        "    <button onclick=\"switchContent(event,'rank')\">Rank managers</button>\n" +
        "    <button onclick=\"switchContent(event,'customerOrderHistory')\">Orders history</button>");
    var managerMenuButtons = $("<button onclick=\"switchContent(event,'managerOrderHistory')\">Orders history</button>\n" +
        "    <button onclick=\"switchContent(event,'feedback')\">Feedbacks</button>\n" +
        "    <button onclick=\"switchContent(event,'newStore')\">Create new store</button>");
    if(isCustomer()){
        $(".topnav .dynamicButtons", this).append(customerMenuButtons);
    }else{
        $(".topnav .dynamicButtons", this).append(managerMenuButtons);
    }
});

$(function () {
    $(".backButton").click(function () {
        setCurrentZone("")
        location.href = buildUrlWithContextPath("pages/dashboard/dashboard.html");
    });

    loadZoneDetails()
});

var refreshRate = 2000; //milli seconds

function loadZoneDetails()
{
    refreshZoneDetails()
}

function triggerAjaxContent() {
    setInterval(refreshZoneDetails, refreshRate);
}

function refreshZoneDetails() {
    $.ajax
    (
        {
            url: buildUrlWithContextPath("zones"),
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
    addTable("storageItemsTable",6,"Storage Items",["ID","Name","Purchase Form", "How Many Stores Sell","Average Price","Sold So Far"])
    $.each(items || [], function(index, item){
        var tr = $(document.createElement('tr'));
        var tdID = $(document.createElement('td')).text(item.m_ItemDto.m_ItemId);
        var tdName = $(document.createElement('td')).text(item.m_ItemDto.m_ItemName);
        var tdPurchaseForm = $(document.createElement('td')).text(item.m_ItemDto.m_PurchaseForm);
        var tdStoresSellIt = $(document.createElement('td')).text(item.m_StoresSellIt);
        var tdAveragePrice =    $(document.createElement('td')).text(show2DecimalPlaces(item.m_AvgPrice))
        var tdSells = $(document.createElement('td')).text(show2DecimalPlaces(item.m_Sales));

        tdID.appendTo(tr);
        tdName.appendTo(tr);
        tdPurchaseForm.appendTo(tr);
        tdStoresSellIt.appendTo(tr);
        tdAveragePrice.appendTo(tr);
        tdSells.appendTo(tr);
        $("#storageItemsTable table").append(tr)
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
    addTable("storesTable",8,"Available Stores",["ID", "Name",
       "Owner Name",
       "Location",
       "Number Of Orders",
       "PPK",
       "Incomes From Items",
       "Incomes From Deliveries"])
    $.each(stores || [], function(index, store){
        var tr = $(document.createElement('tr'));
        var tdID = $(document.createElement('td')).text(store.m_Id);
        var tdName = $(document.createElement('td')).text(store.m_Name);
        var tdOwnerName = $(document.createElement('td')).text(store.m_OwnerName);
        var tdLocation = $(document.createElement('td')).text("("+store.m_Location.x +"," +store.m_Location.y +")");
        var tdPPK = $(document.createElement('td')).text(show2DecimalPlaces(store.m_PPK));
        var tdNumberOfOrders = $(document.createElement('td')).text(store.m_OrdersDto.length);
        var itemsIncomes = 0
        $.each(store.m_OrdersDto|| [], function(index, order) {
            itemsIncomes += order.m_TotalItemsPrice
        });
        var tdIncomesFromItems = $(document.createElement('td')).text(show2DecimalPlaces(itemsIncomes));
        var tdIncomesFromDeliveries = $(document.createElement('td')).text(show2DecimalPlaces(store.m_DeliveriesIncome));

        tdID.appendTo(tr);
        tdName.appendTo(tr);
        tdOwnerName.appendTo(tr);
        tdLocation.appendTo(tr);
        tdNumberOfOrders.appendTo(tr);
        tdPPK.appendTo(tr);
        tdIncomesFromItems.appendTo(tr);
        tdIncomesFromDeliveries.appendTo(tr);

        tr.click(function() {
            var currentTable = document.getElementById("storeItemsTable");
            if(currentTable!==null)
            {
                currentTable.parentElement.removeChild(currentTable);
            }
            addTable("storeItemsTable",5,"Available Items Of "+store.m_Name,["ID","Name","Purchase Form","Price","Sold So Far"])
            $.each(store.m_Items || [], function(index, storeItem){
                var trItem = $(document.createElement('tr'));
                var tdID = $(document.createElement('td')).text(storeItem.m_ItemId);
                var tdName = $(document.createElement('td')).text(storeItem.m_ItemName);
                var tdPurchaseForm = $(document.createElement('td')).text(storeItem.m_PurchaseForm);
                var tdPrice = $(document.createElement('td')).text(show2DecimalPlaces(storeItem.m_Price));
                var tdSoldSoFar = $(document.createElement('td')).text((storeItem.m_AmountOfSell));

                tdID.appendTo(trItem);
                tdName.appendTo(trItem);
                tdPurchaseForm.appendTo(trItem);
                tdPrice.appendTo(trItem);
                tdSoldSoFar.appendTo(trItem);
                $("#storeItemsTable table").append(trItem)
            });
        });
        $("#storesTable table").append(tr)
    });
}

function addTable(id,numberOfColumns,title,headers) {
    var myTableDiv = document.getElementById("zone-content");
    var container = document.createElement('div');
    container.id = id
    var spanTitle =document.createElement('span')
        spanTitle.textContent = title;
    var table = document.createElement('table');
    table.className="table"
    var tableHeaders = document.createElement('thead');
    var tr = document.createElement('TR');
    var tableBody = document.createElement('tbody');

    for (var i = 0; i < numberOfColumns; i++) {
            var th = document.createElement('th');
            th.colspan="1"
            th.textContent = headers[i]
            tr.appendChild(th);
    }
    container.appendChild(spanTitle)
    table.appendChild(tableBody);
    table.appendChild(tableHeaders);
    tableHeaders.appendChild(tr)
    container.appendChild(table)
    myTableDiv.appendChild(container)

}