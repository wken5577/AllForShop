let index = {
        init :function(){

            $("#order-btn").on("click",() =>{
                this.order();
            });

            $("#delete-btn").on("click",() =>{
                this.deleteItem();
            });
        },

        deleteItem : function(){
            let ids = $("input:checkbox[name=check]:checked").length;
            let idArr = new Array(ids);

            for(var i = 0; i < ids; i++){
                idArr[i] = $("input:checkbox[name=check]:checked").eq(i).val();
            }

            let data = {
                 ids : idArr
            }

            $.ajax({
                   type : "DELETE",
                    url : "/basket",
                    data: JSON.stringify(data),
                    contentType : "application/json; charset=utf-8",
                    dataType: "json"
            }).done(function(resp){
                    alert("삭제 성공")
                   location.href = "/mybasket";
              }).fail(function(error){
                   alert(error.responseText);
              })

        },

         order : function(){
                    let orderType = $("input:radio[name=order-type]:checked").val();
                    let data = makeOrderData();

                    let itemLine = $("input:checkbox[name=check]:checked").parent().prevAll();
                    let itemQuantity = $("input:checkbox[name=check]:checked").length;

                    let totalPrice = 0;
                    let cnt = 0;
                    for(var i = 0; i < itemLine.length; i++){
                        if(i % 4 === 1){
                            totalPrice += parseInt(itemLine[i].innerHTML) * cnt;
                        } else if(i % 4 === 0){
                            cnt = parseInt(itemLine[i].children[0].value);
                        }
                    }

                    let itemName = $("input:checkbox[name=check]:checked").parent().prev().prev().prev().html();

                      if(orderType === 'normal'){
                                $.ajax({
                                            type : "POST",
                                            url : "/basket/order",
                                            data: JSON.stringify(data),
                                            contentType : "application/json; charset=utf-8",
                                            dataType: "json"
                                        }).done(function(resp){
                                            alert("주문이 완료되었습니다.");
                                            location.href = "/";
                                        }).fail(function(error){
                                            alert(error);
                                        })
                        }else{
                            	IMP.init('imp42786441');
                                		IMP.request_pay({
                                                  pg: "kcp",
                                                  pay_method: "card",
                                                   merchant_uid : 'merchant_' + new Date().getTime(),
                                                  name: itemName + '외' + (itemQuantity - 1),
                                                  amount: totalPrice,
                                                  buyer_tel : '010-####-####',
                                                  buyer_addr: data.deliveryAddress
                                		}, function(rsp) {
                                		    if (rsp.success) {
                                		    	var msg = '결제가 완료되었습니다.';
                                		        msg += '결제 금액 : ' + rsp.paid_amount;
                                                data.totalPrice = totalPrice;
                                                data.imp_uid = rsp.imp_uid;

                                               $.ajax({
                                                        type : "POST",
                                                        url : "/basket/order/payment",
                                                        data: JSON.stringify(data),
                                                        contentType : "application/json; charset=utf-8",
                                                        dataType: "json"
                                                }).done(function(resp){
                                                     alert("주문 성공!!");
                                                      location.href = "/";
                                                }).fail(function(error){
                                                    alert(error);
                                                })

                                		    } else {
                                		    	 var msg = '결제에 실패하였습니다.';
                                		         msg += '에러내용 : ' + rsp.error_msg;
                                		    }
                                		     alert(msg);
                                		});
                        }
        }

}

index.init();

function addBasket(itemId){

     let data = {
              itemId : itemId
      };

        $.ajax({
            type : "POST",
            url : "/basket",
            data: JSON.stringify(data),
            contentType : "application/json; charset=utf-8",
            dataType: "json",
            async: false
        }).done(function(resp){
            alert("장바구니에 등록되었습니다.")
             location.href = "/item/" + itemId;
        }).fail(function(error){
              if(error.status == '403'){
                  alert("로그인 후 이용 가능합니다.");
                  location.href = "/loginForm";
             }else{
                alert(error.responseText);
             }
        })

}

function makeOrderData(){
    let ids = $("input:checkbox[name=check]:checked").length;
    let itemArr = new Array;

     for(var i = 0; i < ids; i++){
             let itemId = $("input:checkbox[name=check]:checked").eq(i).val();
             quantity = $('#quantity' + itemId).val();


            itemData = {
                    itemId : $("input:checkbox[name=check]:checked").eq(i).val(),
                    quantity : quantity
            }
            itemArr.push(itemData);
        }

        let deliveryAddress = $("#address").val() +" "+$("#address-detail").val();
        let data = {
            itemArr : itemArr,
            deliveryAddress : deliveryAddress
        }

        return data;
}
