$(document).ready(function(){
    
    alert("Hello.")

    // Logout button functionality
    $("#logout_button").click(function(){
        $.post({
            url : "u/auth/logout",
                success : function(data){
                    if (!data) {
                        alert("Successfully logged out.")
                    }
                },
                error : function(data){
                    alert("An error occurred!");
                }
            });
    })

    // Login button functionality
    $("#login_button").click(function(){

        let username = $("#username");
        let pass = $("#pass");

        if (!username.val()) {
            username.css("border-color", "red");
        }
        if (!pass.val()) {
            pass.css("border-color", "red");
        }

        $.post({
            url : "u/auth/login",
                contentType : "application/json",
                data : JSON.stringify({
                    "stringArgs" : [
                        username.val(),
                        pass.val()
                    ]
                }),
                success : function(data){
                    if (!data) {
                        alert("Login success.")
                        window.location.replace("http://localhost:8080/WebProject/home.html"); 
                    }
                },
                error : function(data){
                    alert("An error occurred!");
                }
            });
        })
    })
            
        /* 
        let repairCodeField = $("#repairCode");
        let fullNameField =  $("#fullName");
        let phoneNumberField =  $("#phoneNumber");
        let typeField =  $("#type");
        let manufacturerField =  $("#manufacturer");
        let deadlineField =  $("#deadline");

        if (!repairCodeField.val()){
            repairCodeField.css("border-color", "red");
        }
        if (!fullNameField.val()){
            fullNameField.css("border-color", "red");
        }
        if (!phoneNumberField.val()){
            phoneNumberField.css("border-color", "red");
        }
        if (!manufacturerField.val()){
            manufacturerField.css("border-color", "red");
        }
        if (!deadlineField.val()){
            deadlineField.css("border-color", "red");
        }
        else {
            // If everything is correct
 
            $.post({
                url : "rest/repairs",
                contentType : "application/json",
                data : JSON.stringify({
                    "repairCode" : repairCodeField.val(),
                    "fullName" : fullNameField.val(),
                    "phoneNumber" : phoneNumberField.val(),
                    "type" : typeField.val(),
                    "manufacturer" : manufacturerField.val(),
                    "deadline" : deadlineField.val()
                }),
                success : function(data){
                    if (data){
                	    window.location.replace("/PocetniREST/repairList.html");
                    }
                    else{
                        $("error").text("Repair with the same code already exists!.").show()
                        .delay(3000).fadeOut(); 
                    }
                },
                error : function(data){
                    $("error").text("Couldn't connect to the server.").show()
                                    .delay(3000).fadeOut();
                }
            });
 
            return;
        }

        $("#error").text("Sva polja moraju biti popunjena!")
                   .css("color", "red").show().delay(3000).fadeOut(); */
