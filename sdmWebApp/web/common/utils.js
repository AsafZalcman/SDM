function isCustomer(){return localStorage["userRole"] === "Customer"}
function setUserRole(role){localStorage["userRole"] = role}
function setCurrentZone(zoneName){localStorage["currentZone"] = zoneName}
function getCurrentZone(){return localStorage["currentZone"]}
function getCurrentAlertVersion(){return localStorage["alertsVersion"]}
function setCurrentAlertVersion(alertsVersion){localStorage["alertsVersion"] = alertsVersion}

function show2DecimalPlaces (value){return Number(Math.round(parseFloat(value + 'e' + 2)) + 'e-' + 2).toFixed(2)}


function isFloatNumber(item,evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode===46)
    {
        var regex = new RegExp(/\./g)
        var count = $(item).val().match(regex).length;
        if (count > 1)
        {
            return false;
        }
    }
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

function getDistanceBetweenTwoPoints(pointA, pointB){
    var a = pointA.x - pointB.x;
    var b = pointA.y - pointB.y;

    return Math.sqrt( a*a + b*b );
}


