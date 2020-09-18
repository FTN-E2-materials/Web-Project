let vue = new Vue({
    el :"#vue-registration",
    data : {
        username : "", 
        firstName : "", 
        lastName : "",
        email : "",
        password : "",
        repeatPassword : "",
        gender : "MALE"
    },
    methods : {
        register : function() {
            if(!vue.username || !vue.password || !vue.lastName || !vue.email || !vue.repeatPassword || !vue.password) {
                alert("Please enter all the data.")
                return;
            }

            console.log(vue.gender)

            let userAccount = {
                key : vue.username,
                password : vue.password,
                type : "GUEST", 
                email : vue.email,
                person : {
                    firstName : vue.firstName,
                    lastName : vue.lastName,
                    gender : vue.gender
                }
            }
            axios.post("/WebProject/data/auth/registration", userAccount)
                .then(function(response) {
                    if (response.status === 200) {// success
                        window.location.replace("http://localhost:8080/WebProject/")
                    }
                })
                .catch(error => {
                    alert(error.response.data)
                })
        },
    } 
});
