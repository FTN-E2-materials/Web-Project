new Vue({
    el :"#vue-login",
    data : {
        username : "", 
        password : "",
        errorMsgDisplay : "",
        errorMsg : "Incorrect email or password!"
    },
    methods : {
        login : function() {
            let reqWrapper = {
                stringArgs : [
                    this.username, 
                    this.password
                ]
            };
            axios.post("http://localhost:8080/WebProject/data/auth/login", reqWrapper)
                .then(function(response) {
                    if (response.status === 200) {// success
                        window.location.replace("http://localhost:8080/WebProject/registration")
                    }
                    else {
                        // Potentially redundant? 
                    }
                })
                .catch(function() {
                    alert("Login failed.")
                })
        },
    } 
});
