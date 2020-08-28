let vue = new Vue({
    el :"#vue-account",
    data : {
        
    },
    methods : {
        loadProfile : function() {
            // Call data service to fetch profile.
        },
        logout : function() {
            axios.post("http://localhost:8080/WebProject/data/auth/logout")
                .then(function(response) {
                    if (response.status == 200) {
                        window.location.href = "http://localhost:8080/WebProject/login"
                    }
                    else {
                        alert("Couldn't log out.")
                    }
                })
        }
    },
    beforeMount() {
        this.loadProfile();
    }
});
