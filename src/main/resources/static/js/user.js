let index = {
    init: function() {
        $("#btn-save").on("click", ()=>{
            this.save();
        });
        $("#btn-update").on("click", ()=>{
            this.update();
        });
//        $("#btn-login").on("click", ()=>{
//            this.login();
//        });
    },

   save: function() {
        // alert("user의 save함수 호출");
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };
        // console.log(data);

        // ajax 호출시 default가 비동기 호출
        // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
        // 회원가입 수행 요청 - 성공 done / 실패 fail
        $.ajax({
           type: "POST",
           url: "/auth/api/user",
           data: JSON.stringify(data),
           contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지
           dataType: "json" // 요청을 서버로 해서 응답이 왔을 때
        }).done(function(res) {
            if (res.data == 1) {
                alert("회원가입이 완료되었습니다.");
                location.href = "/";
            } else {
                alert("이미 존재하는 회원입니다.");
                location.href = "/auth/joinform";
            }
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
   },
   update: function() {
           let data = {
               id: $("#id").val(),
               username: $("#username").val(),
               password: $("#password").val(),
               email: $("#email").val()
           };
           $.ajax({
              type: "PUT",
              url: "/api/user",
              data: JSON.stringify(data),
              contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지
              dataType: "json" // 요청을 서버로 해서 응답이 왔을 때
           }).done(function(res) {
               alert("회원수정이 완료되었습니다.");
               location.href = "/";
           }).fail(function(error) {
               alert(JSON.stringify(error));
           });
      }
}

index.init();


//   login: function() {
//           let data = {
//               username: $("#username").val(),
//               password: $("#password").val()
//           };
//           $.ajax({
//              type: "POST",
//              url: "/api/user/login",
//              data: JSON.stringify(data),
//              contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지
//              dataType: "json" // 요청을 서버로 해서 응답이 왔을 때
//           }).done(function(res) {
//               alert("로그인이 완료되었습니다.");
//               location.href = "/";
//           }).fail(function(error) {
//               alert(JSON.stringify(error));
//           });
//      }