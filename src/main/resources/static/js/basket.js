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