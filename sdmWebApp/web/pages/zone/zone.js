var zoneDetailsTabName = "details"
var createStoreTab= "newStore"
var currentTab=zoneDetailsTabName
var refreshRate = 2000; //milli seconds
let itemIDtoItemName = new Map();
let storeIDtoDiscounts = new Map();
var emptyOrder


    async function setContentOfSelectedBtn(selectedBtn) {
        currentTab = selectedBtn
        $(".zone-content").empty();
        switch (selectedBtn) {
            case 'makeAnOrder':
                $(".zone-content").append(await loadMakeOrder());
                activateOnLoadMakeAnOrder();
                break;
            case 'rank':
                res = "<h1> In Rank </h1>";
                break;
            case 'customerOrderHistory':
                refreshCustomerOrderHistory()
break
            case 'managerOrderHistory':
                refreshManagerOrderHistory()
                break;
            case 'feedback':
                refreshFeedbacksContent()
                break;
            case createStoreTab:
                $(".zone-content").append(await loadCreateStoreTab());
                createNewStore()
                break;
            case  zoneDetailsTabName:
                loadZoneDetails()
                break;
        }
}

    function switchContent(evt, selectedBtn) {
        $(".active").removeClass("active");
        evt.currentTarget.className += " active";
        setContentOfSelectedBtn(selectedBtn);
    }

    $(function () {
        var customerMenuButtons = $("<button onclick=\"switchContent(event,'makeAnOrder')\">Make an order</button>\n" +
            "    <button onclick=\"switchContent(event,'rank')\">Rank managers</button>\n" +
            "    <button onclick=\"switchContent(event,'customerOrderHistory')\">Orders history</button>");
        var managerMenuButtons = $("<button onclick=\"switchContent(event,'managerOrderHistory')\">Orders history</button>\n" +
            "    <button onclick=\"switchContent(event,'feedback')\">Feedbacks</button>\n" +
            "    <button onclick=\"switchContent(event,'newStore')\">Create new store</button>");
        if (isCustomer()) {
            $(".topnav .dynamicButtons", this).append(customerMenuButtons);
        } else {
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

/*make order tab */
async function loadMakeOrder(){
    var mainBlockDiv = loadMainBlock();
    var selectItemsDiv = loadSelectItemBlock();
    var selectDiscounts = loadSelectDiscountsBlock();
    var orderSummeryDiv = loadOrderSummeryBlock();
    var feedbackDiv = loadOrderFeedbacksBlock();
    var container = "<div class=\"makeAnOrder\" id=\"makeAnOrder\">" + "\n" + mainBlockDiv + "\n" +  selectItemsDiv + "\n" + selectDiscounts + "\n" + orderSummeryDiv + "\n" + feedbackDiv + "\n" + "</div>";

    return container;
    }

    function loadMainBlock() {
        return "<div class=\"main-block\">\n" +
            "        <h1>Make an order</h1>\n" +
            "        <form id=\"pre-form\" action=\"\">\n" +
            "            <fieldset>\n" +
            "            <div class=\"pre-inputs\">\n" +
            "                <label for=\"datepicker\">Choose date:</label>\n" +
            "                <input type=\"text\" id=\"datepicker\" name=\"date\" required>\n" +
            "\n" +
            "                <br><br>\n" +
            "\n" +
            "                <label for=\"orderLocation\">Choose your location:</label>\n" +
            "                <select name=\"location\" id=\"orderLocation\" required>\n" +
            "                    <option value=\"\" disabled selected>Select your location..</option>\n" +
            "                </select>\n" +
            "\n" +
            "                <br><br>\n" +
            "            </div>\n" +
            "            <h3>Select your type of order:</h3>\n" +
            "            <div class=\"orderType\">\n" +
            "                    <input type=\"radio\" value=\"static\" id=\"radioStatic\" name=\"method\"/>\n" +
            "                    <label for=\"radioStatic\" class=\"radio\">Static order</label>\n" +
            "\n" +
            "\n" +
            "                    <input type=\"radio\" value=\"dynamic\" id=\"radioDynamic\" name=\"method\" checked/>\n" +
            "                    <label for=\"radioDynamic\" class=\"radio\">Dynamic order</label>\n" +
            "            </div>\n" +
            "            <br>\n" +
            "            <div class=\"staticSelectStore\" hidden>\n" +
            "                <label for=\"selectStore\">Select store:</label>\n" +
            "                <select name=\"selectStore\" id=\"selectStore\">\n" +
            "                    <option value=\"\" disabled selected>Select store..</option>\n" +
            "                </select>\n" +
            "            </div>\n" +
            "\n" +
            "            <input type=\"submit\" value=\"Press to Continue\">\n" +
            "            </fieldset>\n" +
            "        </form>\n" +
            "    </div>";
    }

    function loadSelectItemBlock() {
        return "<div class=\"select-item-block\" hidden>\n" +
            "        <table id=\"selectItemTable\" class=\"tableView\">\n" +
            "            <tr>\n" +
            "                <th>ID</th>\n" +
            "                <th>Name</th>\n" +
            "                <th>Purchase Form</th>\n" +
            "                <th>Price</th>\n" +
            "                <th>Amount</th>\n" +
            "                <th></th>\n" +
            "            </tr>\n" +
            "        </table>\n" +
            "        <form id=\"selectItemForm\" action=\"\">\n" +
            "        <input type=\"submit\" value=\"Press to Continue\">\n" +
            "        </form>\n" +
            "    </div>";
    }
function triggerAjaxZoneDetailsTables() {
        setTimeout(refreshZoneDetails, refreshRate);
}

function refreshZoneDetails() {
    if(currentTab === zoneDetailsTabName) {

        $.ajax
        (
            {
                url: buildUrlWithContextPath("zones"),
                data: "zoneName=" + getCurrentZone(),
                success: function (zone) {
                    loadStorageItemsList(zone.m_StorageItemsDtos);
                    loadStoresList(zone.m_StoresDtos);
                    triggerAjaxZoneDetailsTables()

                }
            }
        )
    }
}

function loadSelectDiscountsBlock(){
    return "    <div id=\"select-discounts-block\" hidden>\n" +
        "        <h1>Here are the discounts!</h1>\n" +
        "        <select id=\"storeDiscount\" name=\"storeDiscount\" required>\n" +
        "            <option value=\"\" disabled selected>Select store...</option>\n" +
        "        </select>\n" +
        "        <div id=\"discounts\">\n" +
        "        </div>\n" +
        "        <form id=\"selectDiscountForm\" action=\"\">\n" +
        "        <input type=\"submit\" value=\"Press to Continue\">\n" +
        "        </form>\n" +
        "    </div>";
}

function loadOrderSummeryBlock(){
    return "<div id=\"order-summery-block\" hidden>\n" +
        "        <h1>Order Summary:</h1>\n" +
        "        <h3>Press on each store at the table for more information</h3>\n" +
        "        <div id=\"order-summary-details\">\n" +
        "            \n" +
        "        </div>\n" +
        "    </div>";
}

function loadOrderFeedbacksBlock(){
    return "<div id=\"store-feedback\" hidden>\n" +
        "        <h1>Feedback The Participating Stores:</h1>\n" +
        "        <div id=\"feedbacks\">\n" +
        "            \n" +
        "        </div>\n" +
        "<input type=\"submit\" value=\"Press here to Make new Order!\" onclick=\"setContentOfSelectedBtn('makeAnOrder')\"/>\n" +
        "    </div>";
}


    async function activateOnLoadMakeAnOrder() {
        emptyOrder=true
        $("#datepicker").datepicker();
        await cleanOrderView();
        getAllAvailableLocationsMakeAnOrder();
        getAllZoneStoresMakeAnOrder();
        onLoadRadioButtonsMakeAnOrder();
        submitFirstStepMakeAnOrder();
        onLoadDiscountSelect();
    }

function cleanOrderView(){
    $.ajax({
        url: buildUrlWithContextPath("orders"),
        method: 'PUT',
        error: function (response) {
            console.error("Failed to send ajax:" + response.responseText);
        },
        success: function (response) {
           console.log(response);
        }
    });
}

function loadOrderFeedbacksDetails(stores){
    console.log(stores);
    $.each(stores, function (index, store) {
        var storeDetails = store[0];
        var myDiv = document.getElementById("feedbacks");
        var container = document.createElement('div');
        container.id = "feedbacks-store" + storeDetails.m_Id;
        container.className = "boxBorder";
        var h1Title = document.createElement('h1');
        h1Title.textContent = storeDetails.m_Name + "'s store:";

        var feedbackForm = document.createElement('form');

        var label1 = document.createElement('label');
        var radio1 = document.createElement('input');
        label1.htmlFor = "radio1";
        label1.className = "radioTo";
        label1.textContent = "1";
        radio1.id = "radio1";
        radio1.type = "radio";
        radio1.name = "rank";
        radio1.value = "1";



        var label2 = document.createElement('label');
        var radio2 = document.createElement('input');
        label2.htmlFor = "radio2";
        label2.className = "radioTo";
        label2.textContent = "2";
        radio2.id = "radio2";
        radio2.type = "radio";
        radio2.name = "rank";
        radio2.value = "2";

        var label3 = document.createElement('label');
        var radio3 = document.createElement('input');
        label3.htmlFor = "radio3";
        label3.className = "radioTo";
        label3.textContent = "3";
        radio3.id = "radio3";
        radio3.type = "radio";
        radio3.name = "rank";
        radio3.value = "3";

        var label4 = document.createElement('label');
        var radio4 = document.createElement('input');
        label4.htmlFor = "radio4";
        label4.className = "radioTo";
        label4.textContent = "4";
        radio4.id = "radio4";
        radio4.type = "radio";
        radio4.name = "rank";
        radio4.value = "4";

        var label5 = document.createElement('label');
        var radio5 = document.createElement('input');
        label5.htmlFor = "radio5";
        label5.className = "radioTo";
        label5.textContent = "5";
        radio5.id = "radio5";
        radio5.type = "radio";
        radio5.name = "rank";
        radio5.value = "5";

        radio5.checked = true;

        var fbTextArea = document.createElement('textarea');
        fbTextArea.name = "description";
        fbTextArea.placeholder = "Enter your feedback here.....";

        var fbSubmit = document.createElement('input');
        fbSubmit.type = "submit";
        fbSubmit.value = "Send!";

        feedbackForm.onsubmit = function ()
        {
            var parameters = $(this).serialize();

            parameters = parameters.concat("&zoneName=" + getCurrentZone() + "&storeId=" + storeDetails.m_Id + "&date=" + JSON.stringify(store[1].m_Date));


            $.ajax({
                url: buildUrlWithContextPath("storeFeedback"),
                method: 'POST',
                data: parameters,
                error: function (response) {
                    console.error("Failed to send ajax:" + response.responseText);
                },
                success: function (res) {
                    alert("Feedback saved!");
                    $('#feedbackForm').trigger("reset");
                    $('#feedbackForm').find("*").attr("disabled", "disabled");
                }
            });
            return false;
        }

        feedbackForm.appendChild(label1);
        feedbackForm.appendChild(radio1);
        feedbackForm.appendChild(label2);
        feedbackForm.appendChild(radio2);
        feedbackForm.appendChild(label3);
        feedbackForm.appendChild(radio3);
        feedbackForm.appendChild(label4);
        feedbackForm.appendChild(radio4);
        feedbackForm.appendChild(label5);
        feedbackForm.appendChild(radio5);

        feedbackForm.appendChild(fbTextArea);
        feedbackForm.appendChild(fbSubmit);

        container.appendChild(h1Title);
        container.appendChild(feedbackForm);

        myDiv.appendChild(container);
    });
}

function addSummarySubmitForm(storageOrderDto){
    var order = storageOrderDto.m_OrderDto;
    var myDiv = document.getElementById("order-summery-block");
    var container = document.createElement('div');
    container.id = "order-summary-submit";
    var h1Title = document.createElement('h1');
    h1Title.textContent = "Total Summary:";
    var itemPriceLabel = document.createElement('h3');
    itemPriceLabel.textContent = "Total Items Price: " + order.m_TotalItemsPrice;
    var deliveryPriceLabel = document.createElement('h3');
    deliveryPriceLabel.textContent = "Total Delivery Price: " + order.m_DeliveryPrice;
    var totalPriceLabel = document.createElement('h3');
    totalPriceLabel.textContent = "Total Price: " + order.m_TotalOrderPrice;

    var okSubmit = document.createElement('button');
    okSubmit.textContent = "Save Order";
    okSubmit.onclick = function (){
        $.ajax({
            url: buildUrlWithContextPath("makeOrder"),
            method: 'POST',
            error: function (response) {
                console.error("Failed to send ajax:" + response.responseText);
            },
            success: function (res) {
                alert("Your Order saved successfully");
                loadOrderFeedbacksDetails(storageOrderDto.m_StoreToOrderMap);
                $('#store-feedback').show();
            }
        });
    };
    okSubmit.className = "okButton";
    var cancelSubmit = document.createElement('button');
    cancelSubmit.textContent = "Cancel Order";
    cancelSubmit.onclick = function (){
        alert("Your Order Canceled");
        location.href = "http://localhost:8080/SDM/pages/zone/zone.html";
    };
    cancelSubmit.className = "cancelButton";


    container.appendChild(h1Title);
    container.appendChild(itemPriceLabel);
    container.appendChild(deliveryPriceLabel);
    container.appendChild(totalPriceLabel);
    container.appendChild(okSubmit);
    container.appendChild(cancelSubmit);
    myDiv.appendChild(container);
}

function loadOrderSummeryDetails(storageOrderDto){
    addTable("storesOrderSummeryTable", 5, "Stores", ["ID", "Name", "PPK", "Distance From Customer", "Delivery Price"], "order-summary-details");

    $.each(storageOrderDto.m_StoreToOrderMap, function (index, storeToOrder) {

        var tr = $(document.createElement('tr'));
        var tdID = $(document.createElement('td')).text(storeToOrder[0].m_Id);
        var tdName = $(document.createElement('td')).text(storeToOrder[0].m_Name);
        var tdPPK = $(document.createElement('td')).text(show2DecimalPlaces(storeToOrder[0].m_PPK));
        var tdDistance = $(document.createElement('td')).text(show2DecimalPlaces(getDistanceBetweenTwoPoints(storeToOrder[0].m_Location, storeToOrder[1].m_DestLocation)));
        var tdDeliveryPrice = $(document.createElement('td')).text(show2DecimalPlaces(storeToOrder[1].m_DeliveryPrice));



        tdID.appendTo(tr);
        tdName.appendTo(tr);
        tdPPK.appendTo(tr);
        tdDistance.appendTo(tr);
        tdDeliveryPrice.appendTo(tr);

        tr.click(function () {
            var currentTable = document.getElementById("storeOrderItemsTable");
            if (currentTable !== null) {
                currentTable.parentElement.removeChild(currentTable);
            }
            addTable("storeOrderItemsTable", 7, "Order Items Of " + storeToOrder[0].m_Name, ["ID", "Name", "Purchase Form", "Amount", "Price Per Unit", "Total Price", "From Discount"], "order-summary-details")
            $.each( storeToOrder[1].m_ItemsDto || [], function (index, storeItem) {
                var trItem = $(document.createElement('tr'));
                var tdID = $(document.createElement('td')).text(storeItem.m_ItemId);
                var tdName = $(document.createElement('td')).text(storeItem.m_ItemName);
                var tdPurchaseForm = $(document.createElement('td')).text(storeItem.m_PurchaseForm);
                var tdAmount = $(document.createElement('td')).text(storeItem.m_AmountOfSell);
                var tdPricePerUnit = $(document.createElement('td')).text(show2DecimalPlaces(storeItem.m_Price));
                var tdTotalPrice = $(document.createElement('td')).text(show2DecimalPlaces(parseInt(storeItem.m_Price) * parseInt(storeItem.m_AmountOfSell)));
                var tdIsPartOfDiscount = $(document.createElement('td')).text(storeItem.m_FromDiscount);

                tdID.appendTo(trItem);
                tdName.appendTo(trItem);
                tdPurchaseForm.appendTo(trItem);
                tdAmount.appendTo(trItem);
                tdPricePerUnit.appendTo(trItem);
                tdTotalPrice.appendTo(trItem);
                tdIsPartOfDiscount.appendTo(trItem);

                $("#storeOrderItemsTable table tbody").append(trItem);
            });
        });

        $("#storesOrderSummeryTable table").append(tr);
    });


    addSummarySubmitForm(storageOrderDto);
}

function loadDiscountForStoreID(storeID, discounts){
    $('#discounts').empty();

    var discountsContainer = document.createElement('div');
    discountsContainer.id = "discounts" + storeID;

    $.each(discounts || [], function(index, discount){
        console.log("Discount number " + index + " is " + discount);
        var discountDiv = document.createElement('div');
        discountDiv.id = "discountNum" + index;

        var discountName = document.createElement('h3');
        discountName.textContent = discount.m_Name;

        var ifYouBuy = document.createElement('label');
        ifYouBuy.textContent = "If You Buy: " + discount.m_DiscountCondition.value + " " + itemIDtoItemName.get(discount.m_DiscountCondition.key);

        var br = document.createElement('br');

        var thenYouGet = document.createElement('label');
        if(discount.m_StoreDiscountOperator === "ONE-OF") {
            thenYouGet.textContent = "Then You Get ONE-OF the following: ";
        }else{
            thenYouGet.textContent = "Then You Get: ";
        }


        let theOffer = document.createElement('form');
        var index;
        for (index = 0; index < discount.m_StoreOfferDtos.length; index++){
            let labelOffer = document.createElement('label');
            labelOffer.htmlFor = "radio" + index;
            labelOffer.className = "radio";
            labelOffer.textContent = discount.m_StoreOfferDtos[index].m_Quantity + " " + itemIDtoItemName.get(discount.m_StoreOfferDtos[index].m_ItemId) + " For additional " + discount.m_StoreOfferDtos[index].m_ForAdditional + " shekels.";

            let radioOffer = document.createElement('input');
            radioOffer.type = "radio";
            radioOffer.id = "radio" + index;
            radioOffer.name = "offer";
            radioOffer.value = discount.m_StoreOfferDtos[index].m_ItemId;

            if(discount.m_StoreDiscountOperator !== "ONE-OF"){
                radioOffer.disabled = true;
            }else{
                if(index === 0){
                    radioOffer.defaultChecked = true;
                }
            }

            var radioBr = document.createElement('br');

            theOffer.append(labelOffer);
            theOffer.append(radioOffer);
            theOffer.append(radioBr);
        }

        let submitOffer = document.createElement('input');
        submitOffer.className = "submitOffer";
        submitOffer.type = "submit";
        submitOffer.value = "Buy Discount";


        var inputBr = document.createElement('br');
        theOffer.append(inputBr);
        theOffer.append(submitOffer);


        theOffer.action = "";
        theOffer.onsubmit = function buyDiscountSubmit(){
            $.ajax({
                data: $(this).serialize() + "&storeId=" + storeID + "&discount=" +  JSON.stringify(discount),
                dataType: 'json',
                url: buildUrlWithContextPath("makeOrder/discount"),
                method: 'POST',
                error: function (response) {
                    console.error(response);
                },
                success: function (response) {
                    console.log(response);
                    alert("Discount added to your chart successfully");
                    //here should re-fetch the discounts form servlet.
                    $('#storeDiscount').empty();
                    $('#discounts').empty();
                    getAllAvialDiscount();
                }
            });
            return false;
        }



        discountDiv.append(discountName);
        discountDiv.append(ifYouBuy);
        discountDiv.append(br);
        discountDiv.append(thenYouGet);
        discountDiv.append(theOffer);

        discountDiv.className = "discountComp";

        discountsContainer.append(discountDiv);
    });

    $('#discounts').append(discountsContainer);
}

function onLoadDiscountSelect(){
    $('#storeDiscount').change(function (){
        var storeID = $(this).val();
        //$('#discounts').find("*").attr("hidden", "hidden");
        //$('#discounts' + storeID).show();
        loadDiscountForStoreID(storeID, storeIDtoDiscounts.get(parseInt(storeID)));
    });

    $("#selectDiscountForm").submit(function (){
        $.ajax({
            url: buildUrlWithContextPath("makeOrder"),
            method: 'GET',
            error: function (response) {
                console.error("Failed to send ajax:" + response.responseText);
            },
            success: function (storageOrderDto) {
                console.log(storageOrderDto);
                loadOrderSummeryDetails(storageOrderDto);
                $('#select-discounts-block').find("*").attr("disabled", "disabled");
                $("#order-summery-block").show();
            }
        });
        return false;
    });
}

    function getAllAvailableLocationsMakeAnOrder() {
        $.ajax({
            data: "zoneName=" + getCurrentZone(),
            url: buildUrlWithContextPath("locations"),
            method: 'GET',
            error: function (response) {
                console.error("Failed to send ajax:" + response.responseText);
            },
            success: function (locations) {
                $.each(locations, function (index, location) {
                    var locationStr = "(" + location.x + "," + location.y + ")";
                    $('#orderLocation').append($("<option></option>").attr("value", JSON.stringify({
                        x: location.x,
                        y: location.y
                    }))
                        .text(locationStr));
                });
            }
        });
    }

    function getAllZoneStoresMakeAnOrder() {
        $.ajax({
            data: "zoneName=" + getCurrentZone(),
            url: buildUrlWithContextPath("zones"),
            method: 'GET',
            error: function (response) {
                console.error("Failed to send ajax:" + response.responseText);
            },
            success: function (zone) {
                $.each(zone.m_StoresDtos, function (index, store) {
                    $('#selectStore').append($("<option></option>").attr("value", store.m_Id)
                        .text(store.m_Name));
                });
            }
        });
    }

    function onLoadRadioButtonsMakeAnOrder() {
        $('input[type="radio"]').click(function () {
            if ($(this).attr("value") == "static") {
                $(".staticSelectStore").show();
                $("#selectStore").prop('required', true);
            }
            if ($(this).attr("value") == "dynamic") {
                $(".staticSelectStore").hide();
                $("#selectStore").prop('required', false);

            }
        });

        $('input[type="radio"]').trigger('click');  // trigger the event
    }

    async function submitFirstStepMakeAnOrder() {
        $('.select-item-block').hide();
        $('#pre-form').submit(function () {
            var parameters = $(this).serialize();
var isStaticOrder = this.method.value === "static"
            parameters = parameters.concat("&zone=" + getCurrentZone());

            try {
                $.ajax({
                    data: parameters,
                    url: buildUrlWithContextPath("orders"),
                    timeout: 2000,
                    method: 'post',
                    error: function () {
                        console.error("Failed to submit");
                    },
                    success: async function (r) {
                        await getAllItemsMakeAnOrder(isStaticOrder);
                        $('.select-item-block').show();
                        $('#pre-form fieldset').prop('disabled', 'disabled');

                        submitSecondStepMakeAnOrder();
                    }
                });
            } catch (e) {
                console.log("Error invoking the ajax !" + e);
            }
            return false
        });
    }

    function addItemToOrder(id, amount) {
        try {
            var parameters = "id=" + id + "&amount=" + amount;
            $.ajax({
                data: parameters,
                url: buildUrlWithContextPath("makeOrder/item"),
                method: 'post',
                error: function (response) {
                    alert(response.responseText);
                },
                success: function (r) {
                    alert("Item add successfully")
                    emptyOrder=false
                },
                complete: function () {
                    $("#" + id + "amount").val("");
                },
            });
        } catch (e) {
            console.log("Error invoking the ajax !" + e);
        }
    }

    function getAllItemsMakeAnOrder(isStatic){
    try {
        $.ajax({
            url: buildUrlWithContextPath("makeOrder/item"),
            method: 'get',
            error: function (err) {
                console.error("Failed to submit" + err);
            },
            success: function (items) {
                itemIDtoItemName.clear();
                $.each(items || [], function (index, item) {
                    itemIDtoItemName.set(item.m_ItemId, item.m_ItemName);
                    var tr = $(document.createElement('tr'));
                    var tdID = $(document.createElement('td')).text(item.m_ItemId);
                    var tdName = $(document.createElement('td')).text(item.m_ItemName);
                    var tdPurchaseForm = $(document.createElement('td')).text(item.m_PurchaseForm);
                    var tdPrice = $(document.createElement('td')).text(item.m_Price);
                    var tdAmount = $(document.createElement('td'));
                    var tdSubmit = $(document.createElement('td'));
if(!isStatic || item.m_Price > 0.0) {
    tdAmount.append($("<input onkeypress=\"return isFloatNumber(this,event)\" name='amount' type='text' style='color: white; text-align: center;' required/>")
        .attr("id", item.m_ItemId + "amount"));

    var button = $(document.createElement('button')).text("Add Item");
    button.addClass("addItemBtn");
    button.click(function () {
        var amountVal = parseFloat($("#" + item.m_ItemId + "amount").val());
        addItemToOrder(item.m_ItemId, amountVal);
    });

    tdSubmit.append(button);
}

                    tdID.appendTo(tr);
                    tdName.appendTo(tr);
                    tdPurchaseForm.appendTo(tr);
                    tdPrice.appendTo(tr);
                    tdAmount.appendTo(tr);
                    tdSubmit.appendTo(tr);
                    $("#selectItemTable").append(tr);

                });
            }

            });
        } catch (e) {
            console.log("Error invoking the ajax !" + e);
        }
    }

function setDiscountToDiv(storeID, storeName,itemsArray, discounts){
    console.log(itemsArray);
    $('#storeDiscount').append($("<option></option>").attr("value", storeID)
        .text(storeName));
}

function getAllAvialDiscount(){
    try {
        $.ajax({
            url: buildUrlWithContextPath("makeOrder/discount"),
            method: 'get',
            error: function () {
                console.error("Failed to submit");
            },
            success: function (response) {
                $.each(response, function (key, value) {
                    storeIDtoDiscounts.set(value[0].m_Id, value[1]);
                    setDiscountToDiv(value[0].m_Id, value[0].m_Name, value[0].m_Items, value[1]);
                });
                $("#select-discounts-block").show();
                $('.select-item-block').find("*").attr("disabled", "disabled");
            }
        });
    } catch (e) {
        console.log("Error invoking the ajax !" + e);
    }

}

function submitSecondStepMakeAnOrder(){
    $("#selectItemForm").submit(function (){
        if(!emptyOrder) {
            getAllAvialDiscount();
        }
        else
        {
            alert("Cannot create an empty order!")
        }
        return false;
    });



}
    /*make order tab  DONE*/

    function loadZoneDetails() {
        refreshZoneDetails()
    }

    function triggerAjaxZoneDetailsTables() {
        if (currentTab === zoneDetailsTabName) {
            setTimeout(refreshZoneDetails, refreshRate);
        }
    }

    function refreshZoneDetails() {

        $.ajax
        (
            {
                url: buildUrlWithContextPath("zones"),
                data: "zoneName=" + getCurrentZone(),
                success: function (zone) {
                    if (currentTab === zoneDetailsTabName) {
                        loadStorageItemsList(zone.m_StorageItemsDtos);
                        loadStoresList(zone.m_StoresDtos);
                        triggerAjaxZoneDetailsTables()
                    }

                }
            }
        )
    }

    function loadStorageItemsList(items) {
        if (!$("#storageItemsTable").length)  // table not exists
        {
            addTable("storageItemsTable", 6, "Storage Items", ["ID", "Name", "Purchase Form", "How Many Stores Sell", "Average Price", "Sold So Far"], "zone-content");
        }
        loadStorageItemsListData(items)
    }

    function loadStorageItemsListData(items) {
        $("#storageItemsTable table tbody").empty()
        $.each(items || [], function (index, item) {
            var tr = $(document.createElement('tr'));
            var tdID = $(document.createElement('td')).text(item.m_ItemDto.m_ItemId);
            var tdName = $(document.createElement('td')).text(item.m_ItemDto.m_ItemName);
            var tdPurchaseForm = $(document.createElement('td')).text(item.m_ItemDto.m_PurchaseForm);
            var tdStoresSellIt = $(document.createElement('td')).text(item.m_StoresSellIt);
            var tdAveragePrice = $(document.createElement('td')).text(show2DecimalPlaces(item.m_AvgPrice))
            var tdSells = $(document.createElement('td')).text(show2DecimalPlaces(item.m_Sales));

            tdID.appendTo(tr);
            tdName.appendTo(tr);
            tdPurchaseForm.appendTo(tr);
            tdStoresSellIt.appendTo(tr);
            tdAveragePrice.appendTo(tr);
            tdSells.appendTo(tr);
            $("#storageItemsTable table tbody").append(tr)
        });
    }

    function loadStoresList(stores) {
        if (!$("#storesTable").length)  // table not exists
        {
            addTable("storesTable", 8, "Available Stores", ["ID", "Name",
                "Owner Name",
                "Location",
                "Number Of Orders",
                "PPK",
                "Incomes From Items",
                "Incomes From Deliveries"], "zone-content");
        }
        loadStoreTableData(stores);
    }

    function loadStoreTableData(stores) {
        $("#storesTable table tbody").empty()
        $.each(stores || [], function (index, store) {
            var tr = $(document.createElement('tr'));
            var tdID = $(document.createElement('td')).text(store.m_Id);
            var tdName = $(document.createElement('td')).text(store.m_Name);
            var tdOwnerName = $(document.createElement('td')).text(store.m_OwnerName);
            var tdLocation = $(document.createElement('td')).text("(" + store.m_Location.x + "," + store.m_Location.y + ")");
            var tdPPK = $(document.createElement('td')).text(show2DecimalPlaces(store.m_PPK));
            var tdNumberOfOrders = $(document.createElement('td')).text(store.m_OrdersDto.length);
            var itemsIncomes = 0
            $.each(store.m_OrdersDto || [], function (index, order) {
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

            tr.click(function () {
                var currentTable = document.getElementById("storeItemsTable");
                if (currentTable !== null) {
                    currentTable.parentElement.removeChild(currentTable);
                }
                addTable("storeItemsTable", 5, "Available Items Of " + store.m_Name, ["ID", "Name", "Purchase Form", "Price", "Sold So Far"], "zone-content")
                $.each(store.m_Items || [], function (index, storeItem) {
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
                    $("#storeItemsTable table tbody").append(trItem)
                });
            });
            $("#storesTable table").append(tr)
        });
    }

    function addTable(id, numberOfColumns, title, headers, tableDiv) {
        var myTableDiv = document.getElementById(tableDiv);
        var container = document.createElement('div');
        container.id = id;
        var spanTitle = document.createElement('h1');
        spanTitle.textContent = title;
        var table = document.createElement('table');
        table.className = "tableView"
        var tableHeaders = document.createElement('thead');
        var tr = document.createElement('TR');
        var tableBody = document.createElement('tbody');

        for (var i = 0; i < numberOfColumns; i++) {
            var th = document.createElement('th');
            th.colspan = "1"
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

async function loadCreateStoreTab() {
    return "<form id=\"createStoreForm\" action=\"\">\n" +
        "    <label for=\"id\">Store Id (whole number):</label>\n" +
        "    <input  type=\"text\"  id=\"id\" name=\"id\" required><br>\n" +
        "    <label for=\"storeName\">Store Name:</label>\n" +
        "    <input type=\"text\" id=\"storeName\" name=\"name\" required><br>\n" +
        "    <label for=\"availableLocations\">Available Locations:</label>\n" +
        "    <select id=\"availableLocations\" name=\"location\" required>\n" +
        "        <option value=\"\" disabled selected>Select Location..</option>\n" +
        "    </select>\n" +
        "    <label for=\"ppk\">PPK:</label>\n" +
        "    <input type=\"text\" id=\"ppk\" name=\"ppk\" required onkeypress=\"return isFloatNumber(this,event)\" ><br>\n" +
        "    <input type=\"submit\" value=\"Create Store\">\n" +
        "</form>\n" +
        "    <table id=\"itemsTable\">\n" +
        "    </table>\n"
}
    function createNewStore() {
        signCreateStoreSubmitEvent()
        loadAvailableLocations()
        loadAvailableItems()
    }

    function loadAvailableLocations() {
        $.ajax
        ({
                data: "zoneName=" + getCurrentZone(),
                url: buildUrlWithContextPath("locations"),
                success: function (locations) {
                    $.each(locations || [], function (index, location) {
                        var locationStr = "(" + location.x + "," + location.y + ")"
                        $("#availableLocations").append($("<option></option>")
                            .attr("value", JSON.stringify({x: location.x, y: location.y}))
                            .text(locationStr))
                            .className = "locationAvailableOption"
                    })
                }
            }
        )
    }

    var requestedItems = []

    function loadAvailableItems() {
        $.ajax
        ({
                data: "zoneName=" + getCurrentZone(),
                url: buildUrlWithContextPath("items"),
                success: function (items) {
                    $.each(items || [], function (index, item) {
                        var trItem = $(document.createElement('tr'));
                        var td = $(document.createElement('td'));
                        td.append($("<label></label>")
                            .text = "Item ID:" + item.m_ItemId)
                        td.append($("<label>Enter Price:</label>"))
                        td.append($("<input onkeypress=\"return isFloatNumber(this,event)\" name='price' type='text' required/>")
                            .attr("id", item.m_ItemId + "price"))
                        var button = $(document.createElement('button')).text("Add Item")
                        button.click(function () {
                            var priceVal = parseFloat($("#" + item.m_ItemId + "price").val())
                            if (priceVal <= 0.0) {
                                alert("Price must be positive number!!");
                            } else {
                                trItem.remove()
                                requestedItems.push({id: item.m_ItemId, price: priceVal})
                                alert("Item was added successfully!!");

                            }
                        })
                        button.appendTo(td)
                        td.appendTo(trItem)
                        $("#itemsTable").append(trItem)
                    })
                }
            }
        )
    }

    function signCreateStoreSubmitEvent () {
        $("#createStoreForm").submit(function () {
            if (requestedItems.length === 0) {
                alert("Cannot Create Empty Store!!");
            } else {
                $.ajax({
                    data: $(this).serialize() + "&items=" + JSON.stringify(requestedItems) + "&zoneName=" + getCurrentZone(),
                    dataType: 'json',
                    url: buildUrlWithContextPath("stores"),
                    method: 'POST',
                    error: function (response) {
                        console.error(response);
                    },
                    success: function (response) {
                        console.log(response);
                        clearCreateStoreWindow()

                    }
                })
            }
            return false;
        });
    }

    function clearCreateStoreWindow() {
        requestedItems = []
        setContentOfSelectedBtn(createStoreTab)
    }

function refreshCustomerOrderHistory() {
    $.ajax
    (
        {
            url: buildUrlWithContextPath("orders"),
            data: "zoneName=" + getCurrentZone(),
            success: function(orders) {
                displayOrdersForCustomer(orders)
            }
        }
    )
}

function displayOrdersForCustomer(orders)
{
    addTable("customerOrders",8,"Orders History",["Id","Data","Location","Number Of Stores In Order","Number Of Items" , "Price Of Items", "Price Of Delivery", "Total Price"], "zone-content")
    loadCustomerHistoryTableData(orders)
}

function loadCustomerHistoryTableData(orders)
{
    $.each(orders || [], function(index, order){
        var tr = $(document.createElement('tr'));
        var tdID = $(document.createElement('td')).text(order.orderDto.m_Id);
        var tdDate  = $(document.createElement('td')).text(order.orderDto.m_Date.day + "." + order.orderDto.m_Date.month+ "." + order.orderDto.m_Date.year);
        var tdLocation = $(document.createElement('td')).text("("+order.orderDto.m_DestLocation.x +"," +order.orderDto.m_DestLocation.y +")");
        var tdNumberOfStores = $(document.createElement('td')).text(order.simpleStoreToOrderMap.length)
        var tdNumberOfItems = $(document.createElement('td')).text(order.orderDto.m_TotalItemsCount)
        var tdPriceOfItems = $(document.createElement('td')).text(show2DecimalPlaces(order.orderDto.m_TotalItemsPrice))
        var tdPriceOfDelivery = $(document.createElement('td')).text(show2DecimalPlaces(order.orderDto.m_DeliveryPrice))
        var tdTotalPrice = $(document.createElement('td')).text(show2DecimalPlaces(order.orderDto.m_TotalOrderPrice))

        tdID.appendTo(tr);
        tdDate.appendTo(tr);
        tdLocation.appendTo(tr);
        tdNumberOfStores.appendTo(tr);
        tdNumberOfItems.appendTo(tr);
        tdPriceOfItems.appendTo(tr);
        tdPriceOfDelivery.appendTo(tr);
        tdTotalPrice.appendTo(tr);
        tr.click(function() {
            var currentTable = document.getElementById("OrderDetailsTable");
            if(currentTable!==null)
            {
                currentTable.parentElement.removeChild(currentTable);
            }
            addTable("OrderDetailsTable",8,"Details Of Order number: "+order.orderDto.m_Id,["ID","Name","Purchase Form","From Store","Amount","Price For Piece","Total Price","From Discount"], "zone-content")
            $.each(order.simpleStoreToOrderMap || [], function(index, simpleStoreToOrderMap){
                $.each(simpleStoreToOrderMap[1].m_ItemsDto || [], function(index, item){
                var trItem = $(document.createElement('tr'));
                var tdID = $(document.createElement('td')).text(item.m_ItemId);
                var tdName = $(document.createElement('td')).text(item.m_ItemName);
                var tdPurchaseForm = $(document.createElement('td')).text(item.m_PurchaseForm);
                var tdFormStore = $(document.createElement('td')).text("id:"+simpleStoreToOrderMap[0].id + ", name:" +simpleStoreToOrderMap[0].name);
                var tdAmount = $(document.createElement('td')).text(show2DecimalPlaces(item.m_AmountOfSell));
                var tdPricePerPiece = $(document.createElement('td')).text(show2DecimalPlaces(item.m_Price));
                var tdTotalPrice = $(document.createElement('td')).text(show2DecimalPlaces(item.m_Price * item.m_AmountOfSell));
                var tdFormDiscount = $(document.createElement('td')).text(item.m_FromDiscount);
                tdID.appendTo(trItem);
                tdName.appendTo(trItem);
                tdPurchaseForm.appendTo(trItem);
                tdFormStore.appendTo(trItem);
                tdAmount.appendTo(trItem);
                tdPricePerPiece.appendTo(trItem);
                tdTotalPrice.appendTo(trItem);
                tdFormDiscount.appendTo(trItem);
                $("#OrderDetailsTable table tbody").append(trItem)
            });
        });
        });

        $("#customerOrders table").append(tr)
    });
}

function refreshManagerOrderHistory() {
    $.ajax
    (
        {
            url: buildUrlWithContextPath("stores"),
            data: "zoneName=" + getCurrentZone(),
            success: function(stores) {
                displayOrdersForManager(stores)
            }
        }
    )
}

function displayOrdersForManager(stores)
{
    var divList = document.createElement("div")
    $.each(stores || [], function(index, store) {
        $('<li>' + store.m_Name + '</li>')
       .click(function() {
           $.ajax
           (
               {
                   url: buildUrlWithContextPath("orders"),
                   data: "zoneName=" + getCurrentZone() + "&storeId=" + store.m_Id,
                   success: function(orders) {
                       var currentTable = document.getElementById("managerOrders");
                       if(currentTable!==null)
                       {
                           currentTable.parentElement.removeChild(currentTable);
                       }
                       addTable("managerOrders",7,"Orders History",["Id","Data","Location","Number Of Items" , "Price Of Items", "Price Of Delivery", "Total Price"], "zone-content");
                       loadManagerHistoryTableData(orders)
                   }
               }
           )
       }).appendTo(divList)
            }
    )
    $("#zone-content").append(divList)
}
//probably we can avoid duplicate with customer orders
function loadManagerHistoryTableData(orders)
{
    $.each(orders || [], function(index, order){
        var tr = $(document.createElement('tr'));
        var tdID = $(document.createElement('td')).text(order.m_Id);
        var tdDate  = $(document.createElement('td')).text(order.m_Date.day + "." + order.m_Date.month+ "." + order.m_Date.year);
        var tdLocation = $(document.createElement('td')).text("("+order.m_DestLocation.x +"," +order.m_DestLocation.y +")");
        var tdNumberOfItems = $(document.createElement('td')).text(order.m_TotalItemsCount)
        var tdPriceOfItems = $(document.createElement('td')).text(show2DecimalPlaces(order.m_TotalItemsPrice))
        var tdPriceOfDelivery = $(document.createElement('td')).text(show2DecimalPlaces(order.m_DeliveryPrice))
        var tdTotalPrice = $(document.createElement('td')).text(show2DecimalPlaces(order.m_TotalOrderPrice))

        tdID.appendTo(tr);
        tdDate.appendTo(tr);
        tdLocation.appendTo(tr);
        tdNumberOfItems.appendTo(tr);
        tdPriceOfItems.appendTo(tr);
        tdPriceOfDelivery.appendTo(tr);
        tdTotalPrice.appendTo(tr);
        tr.click(function() {
            var currentTable = document.getElementById("OrderDetailsTable");
            if(currentTable!==null)
            {
                currentTable.parentElement.removeChild(currentTable);
            }
            addTable("OrderDetailsTable",7,"Details Of Order number: "+order.m_Id,["ID","Name","Purchase Form","Amount","Price For Piece","Total Price","From Discount"], "zone-content");
            $.each(order.m_ItemsDto || [], function(index, item){
                    var trItem = $(document.createElement('tr'));
                    var tdID = $(document.createElement('td')).text(item.m_ItemId);
                    var tdName = $(document.createElement('td')).text(item.m_ItemName);
                    var tdPurchaseForm = $(document.createElement('td')).text(item.m_PurchaseForm);
                    var tdAmount = $(document.createElement('td')).text(show2DecimalPlaces(item.m_AmountOfSell));
                    var tdPricePerPiece = $(document.createElement('td')).text(show2DecimalPlaces(item.m_Price));
                    var tdTotalPrice = $(document.createElement('td')).text(show2DecimalPlaces(item.m_Price * item.m_AmountOfSell));
                    var tdFormDiscount = $(document.createElement('td')).text(item.m_FromDiscount);
                    tdID.appendTo(trItem);
                    tdName.appendTo(trItem);
                    tdPurchaseForm.appendTo(trItem);
                    tdAmount.appendTo(trItem);
                    tdPricePerPiece.appendTo(trItem);
                    tdTotalPrice.appendTo(trItem);
                    tdFormDiscount.appendTo(trItem);
                    $("#OrderDetailsTable table tbody").append(trItem)
                });
            });
        $("#managerOrders table").append(tr)
    });
}

function refreshFeedbacksContent()
{
    $.ajax
    (
        {
            url: buildUrlWithContextPath("stores"),
            data: "zoneName=" + getCurrentZone(),
            success: function(stores) {
                displayFeedbacksForManager(stores)
            }
        }
    )
}

function displayFeedbacksForManager(stores)
{
    var divList = document.createElement("div")
    $.each(stores || [], function(index, store) {
            $('<li>' + store.m_Name + '</li>')
                .click(function() {
                    $.ajax
                    (
                        {
                            url: buildUrlWithContextPath("storeFeedback"),
                            data: "zoneName=" + getCurrentZone() + "&storeId=" + store.m_Id,
                            success: function(feedbacks) {
                                var currentTable = document.getElementById("feedbacksTable");
                                if(currentTable!==null)
                                {
                                    currentTable.parentElement.removeChild(currentTable);
                                }
                                addTable("feedbacksTable",4,"Feedbacks of the store \"" +store.m_Name +"\"",["Author","Data","Rank","Description"], "zone-content");
                                loadFeedbacksTableData(feedbacks)
                            }
                        }
                    )
                }).appendTo(divList)
        }
    )
    $("#zone-content").append(divList)
}

function loadFeedbacksTableData(feedbacks)
{
    $.each(feedbacks || [], function(index, feedback){
        var tr = $(document.createElement('tr'));
        var tdAuthor = $(document.createElement('td')).text(feedback.m_UserName);
        var tdDate  = $(document.createElement('td')).text(feedback.m_Date.day + "." + feedback.m_Date.month+ "." + feedback.m_Date.year);
        var tdRank = $(document.createElement('td')).text(feedback.m_Rank)
        var tdDescription = $(document.createElement('td')).text(feedback.m_Description === undefined? "-" : feedback.m_Description )

        tdAuthor.appendTo(tr);
        tdDate.appendTo(tr);
        tdRank.appendTo(tr);
        tdDescription.appendTo(tr);

        $("#feedbacksTable table").append(tr)
    });
}

