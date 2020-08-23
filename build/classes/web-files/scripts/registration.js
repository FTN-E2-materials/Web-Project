new Vue({
    el :"#vue-registration",
    data : {
        username : "", 
        firstName : "", 
        lastName : "",
        email : "",
        password : "",
        repeatPassword : "",
    },
    methods : {
        register : function() {
            let userAccount = {
                id : this.username,
                password : this.password,
                type : "GUEST", 
                person : {
                    firstName : this.firstName,
                    lastName : this.lastName,
                    gender : "MALE"
                }
            }
            axios.post("http://localhost:8080/WebProject/data/auth/registration", userAccount)
                .then(function(response) {
                    if (response.status === 200) {// success
                        window.location.replace("http://localhost:8080/WebProject/")
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
