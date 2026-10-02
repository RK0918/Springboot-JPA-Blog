let index = {
    init: function() {
        $("#btn-save").on("click", ()=> { // function() {}, () => this를 바인딩하기 위해서
            this.save();
            });

        $("#btn-update").on("click", ()=> { // function() {}, () => this를 바인딩하기 위해서
            this.update();
            });
         },


    save: function() {
        // alert('user의 save함수 호출됨.')
        let data = {
            username : $("#username").val(),
            password : $("#password").val(),
            email : $("#email").val(),

        };

        // console.log(data);
        // ajax({오브젝트}).done(function(){}).fail(function(){});
        // -> 성공하면 done, 실패하면 fail 실행
        // ajax 호출시 default가 비동기 호출
        // ajax가 통신 성공-> 서버 json 리턴-> 자동으로 자자바 오브젝트로 변환
        $.ajax({
            type : "POST", // 아래에 /join을 하지 않는건 POST -> insert(회원가입)인걸 알기 때문이다 굳이 join까지 x
            url : "/auth/joinProc", // user 컨트롤러를 보면 /user로 시작하기 때문
            data : JSON.stringify(data),// http body 데이터, data: data로 해버리면 java에서 던져버릴 때 java에서 이해못함. json 문자열로 변경
            contentType : "application/json; charset = utf-8", // body데이터가 어떤 타입인지(MIME)
            dataType : "json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 문자열( 생긴게 json이면 =>
        }).done(function(resp){
            alert("회원가입 완료");
            console.log(resp);
            location.href = "/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        }); // ajax통신을 통해서 3개의 ㅍ ㅏ라미터를 데이터를 json 변경하고 insert 요청

    },

    // 또한 회원수정 시 세션값을 변경하기 위해 username 데이터도 보내야됨.
    // 그래야 Authentication 객체를 생성할 수 있음.

    update: function() {
            // 아래 data값을 수정하고 -
            let data = {
                id : $("#id").val(),
                username : $("#username").val(),
                password : $("#password").val(),
                email : $("#email").val(),

            };


            $.ajax({
                type : "PUT",
                // 이제 마지막으로 UserApiController 로 넘어가서 아래 url 페이지 작성("/user") -> 그리고 userService.회원수정() 메서드 만들러 userService로
                url : "/user", // user 컨트롤러를 보면 /user로 시작하기 때문
                data : JSON.stringify(data),// http body 데이터, data: data로 해버리면 java에서 던져버릴 때 java에서 이해못함. json 문자열로 변경
                contentType : "application/json; charset = utf-8", // body데이터가 어떤 타입인지(MIME)
                dataType : "json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 문자열( 생긴게 json이면 =>
            }).done(function(resp){
                alert("회원수정 완료");
                console.log(resp);
                location.href = "/";
            }).fail(function(error){

                alert(JSON.stringify(error));
            }); // ajax통신을 통해서 3개의 ㅍ ㅏ라미터를 데이터를 json 변경하고 insert 요청

        },

   }

index.init(); // 저기 위 let index = {} 은 오브젝트라 호출해줘야됨.