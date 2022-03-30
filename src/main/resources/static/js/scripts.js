
function order(){
    var url = window.location.href;
    var itemId = url.substring(url.lastIndexOf('/')+1);
    var area = document.getElementById('inputQuantity');
    var quantity = area.value;

    var deliveryAddress = "address";

    var requestUrl = '/order';

    var form = document.createElement('form');
    form.setAttribute('method', 'post');
    form.setAttribute('action', requestUrl);
    document.charset = "utf-8";

    var input1 = document.createElement('input');
    input1.setAttribute('type','hidden');
    input1.setAttribute('name','itemId');
    input1.setAttribute('value', itemId);

    var input2 = document.createElement('input');
    input2.setAttribute('type','hidden');
    input2.setAttribute('name','quantity');
    input2.setAttribute('value', quantity);

    var input3 = document.createElement('input');
    input3.setAttribute('type','hidden');
    input3.setAttribute('name','deliveryAddress');
    input3.setAttribute('value', deliveryAddress);


    form.appendChild(input1);
    form.appendChild(input2);
    form.appendChild(input3);


    document.body.appendChild(form);
    form.submit();


}