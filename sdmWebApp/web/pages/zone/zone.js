function getContentOfSelectedBtn(selectedBtn) {
    let res = "";
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
        location.href = buildUrlWithContextPath("pages/dashboard/dashboard.html");
    });
});