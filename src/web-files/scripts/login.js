let vue = new Vue({
    el :"#vue-login",
    data : {
        username : "", 
        password : "",
        errorMsgDisplay : "",
        message : ""
    },
    methods : {
        login : function() {
            let wrapper = {
                stringArgs : [
                    this.username, 
                    this.password
                ]
            };
            console.log("Attempting login")
            axios.post("http://localhost:8080/WebProject/data/auth/login", wrapper)
                .then(function(response) {
                    if (response.status === 200) {// success
                        window.location.replace("http://localhost:8080/WebProject/")
                    }
                })
                .catch(error => {
                    alert(error.response.data)
                    vue.message = error.response.data
                })
        },
    } 
});
