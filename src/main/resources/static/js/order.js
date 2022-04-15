
function order(itemId){
    let orderType = $("input:radio[name=order-type]:checked").val();
    let quantity = $("#quantity").val();
    let deliveryAddress = $("#address").val() +" "+$("#address-detail").val();

    let data = {
                itemId : itemId,
                quantity : quantity,
                deliveryAddress: deliveryAddress
    };

    if(orderType === 'normal'){
            $.ajax({
                        type : "POST",
                        url : "/api/order",
                        data: JSON.stringify(data),
                        contentType : "application/json; charset=utf-8",
                        dataType: "json"
                    }).done(function(resp){
                        alert("주문이 완료되었습니다.");
                        location.href = "/";
                    }).fail(function(error){
                        let errorField = error.responseJSON.errorField;
                        let errorMsg = "";
                        for(key in errorField){
                            errorMsg += errorField[key] + '\n';
                        }
                        alert(errorMsg);
                    })

    }else{

    //가맹점 식별코드
    		IMP.init('imp42786441');
    		IMP.request_pay({
                      pg: "kcp",
                      pay_method: "card",
                       merchant_uid : 'merchant_' + new Date().getTime(),
                      name: $("#title").val(),
                      amount: $("#price").val() * quantity,
                      buyer_email: "gildong@gmail.com",
                      buyer_tel : '010-1234-5678',
                      buyer_addr: deliveryAddress
    		}, function(rsp) {
    			console.log(rsp);
    		    if ( rsp.success ) {
    		    	var msg = '결제가 완료되었습니다.';
    		        msg += '고유ID : ' + rsp.imp_uid;
    		        msg += '상점 거래ID : ' + rsp.merchant_uid;
    		        msg += '결제 금액 : ' + rsp.paid_amount;
    		        msg += '카드 승인번호 : ' + rsp.apply_num;

                  let payData = {
                         imp_uid: rsp.imp_uid,
                         itemId : itemId,
                         quantity : quantity,
                         deliveryAddress: deliveryAddress,
                         paid_amount :  rsp.paid_amount
                  };

                   $.ajax({
                            type : "POST",
                            url : "/api/order/payment",
                            data: JSON.stringify(payData),
                            contentType : "application/json; charset=utf-8",
                            dataType: "json"
                    }).done(function(resp){

                    }).fail(function(error){
                        alert(error);
                    })

    		    } else {
    		    	 var msg = '결제에 실패하였습니다.';
    		         msg += '에러내용 : ' + rsp.error_msg;
    		    }
    		    alert(msg);
    		});
//
//            IMP.init('imp42786441');
//            IMP.request_pay({ // param
//                      pg: "kcp",
//                      pay_method: "card",
//                       merchant_uid : 'merchant_' + new Date().getTime(),
//                      name: $("#title").val(),
//                      amount: $("#price").val() * quantity,
//                      buyer_email: "gildong@gmail.com",
//                      buyer_tel : '010-1234-5678',
//                      buyer_addr: deliveryAddress
//                  }, function (rsp) { // callback
//                      if (rsp.success) {
//                          // 결제 성공 시 로직,
//                      let payData = {
//                             imp_uid: rsp.imp_uid,
//                             itemId : itemId,
//                             quantity : quantity,
//                             deliveryAddress: deliveryAddress,
//                             price : $("#price").val()
//                      };
//                      $.ajax({
//                                  type : "POST",
//                                  url : "/api/order/payment",
//                                  data: JSON.stringify(payData),
//                                  contentType : "application/json; charset=utf-8",
//                                  dataType: "json"
//                              }).done(function(resp){
//                                  alert("주문이 완료되었습니다.");
//                                  location.href = "/";
//                              }).fail(function(error){
//                                  let errorField = error.responseJSON.errorField;
//                                  let errorMsg = "";
//                                  for(key in errorField){
//                                      errorMsg += errorField[key] + '\n';
//                                  }
//                                  alert(errorMsg);
//                              })
//                      } else {
//                           결제 실패 시 로직,
//                      }
//                  });


    }

}
