let index = {
    init :function(){
        $("#join-button").on("click",() =>{
            this.save();
        });
    },

    save : function(){
          let data = {
            username : $("#username").val(),
            password : $("#password").val(),
            email: $("#email").val()
          };

            $.ajax({
                type : "POST",
                url : "/join",
                data: JSON.stringify(data),
                contentType : "application/json; charset=utf-8",
                dataType: "json"
            }).done(function(resp){
                alert("회원가입이 완료되었습니다.");
                location.href = "/";
            }).fail(function(error){
                let errorField = error.responseJSON.errorField;
                let errorMsg = "";
                for(key in errorField){
                    errorMsg += errorField[key] + '\n';
                }
                alert(errorMsg);
            })

    }

}

index.init();