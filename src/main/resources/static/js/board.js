let index = {
    init: function() {
        $("#btn-save").on("click", ()=> {
            this.save();
        });

        $("#btn-delete").on("click", ()=> {
            this.deleteById();

        });
        $("#btn-update").on("click", ()=> {
            this.update();

        });

    },


    save: function() {
        // alert('user의 save함수 호출됨.')
        let data = {
            title : $("#title").val(),
            content : $("#content").val()


        };

        $.ajax({
            type : "POST", // data(title, content)를 보낸다
            url : "/api/board", // 이 주소로
            data : JSON.stringify(data),// http body 데이터, data: data로 해버리면 java에서 던져버릴 때 java에서 이해못함. json 문자열로 변경
            contentType : "application/json; charset = utf-8", // body데이터가 어떤 타입인지(MIME)
            dataType : "json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 문자열( 생긴게 json이면 =>
        }).done(function(resp){
            alert("글쓰기 완료");
            console.log(resp);
            location.href = "/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        }); // ajax통신을 통해서 3개의 ㅍ ㅏ라미터를 데이터를 json 변경하고 insert 요청

    },

    deleteById: function() {
            let id = $("#id").text();
            // delete를 위해선 id값이 필요하고 이를 변수에 할당하여 아래
            // url -> /api/board/ + id 로 함


            $.ajax({
                type : "DELETE", // data(title, content)를 보낸다
                url : "/api/board/" + id, // 이 주소로
                /** save와 달리 data와 contentType이 필요없음 삭제만 할거니*/
                dataType : "json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 문자열( 생긴게 json이면 =>
            }).done(function(resp){
                alert("글삭제 완료");
                console.log(resp);
                location.href = "/";
            }).fail(function(error){
                alert(JSON.stringify(error));
            });

        },

        update: function() {
                // update(수정)을 위해서 id값을 js를 통해서 updateForm.jsp에 넘김
                let id = $("#id").val();

                let data = {
                    title : $("#title").val(),
                    content : $("#content").val()
                };


                $.ajax({
                    type : "PUT",
                    url : "/api/board/" + id, // 이 주소로 보냄( -> 이후에 boardApiController.java에서 update() 메서드 추가
                    data : JSON.stringify(data),// http body 데이터, data: data로 해버리면 java에서 던져버릴 때 java에서 이해못함. json 문자열로 변경
                    contentType : "application/json; charset = utf-8", // body데이터가 어떤 타입인지(MIME)
                    dataType : "json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 문자열( 생긴게 json이면 =>
                }).done(function(resp){
                    alert("글수정 완료");
                    console.log(resp);
                    location.href = "/";
                }).fail(function(error){
                    alert(JSON.stringify(error));
                }); // ajax통신을 통해서 3개의 ㅍ ㅏ라미터를 데이터를 json 변경하고 insert 요청

            },


   }

index.init(); // 저기 위 let index = {} 은 오브젝트라 호출해줘야됨.