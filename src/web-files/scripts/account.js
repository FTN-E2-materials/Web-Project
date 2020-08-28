let vue = new Vue({
    el :"#vue-account",
    data : {
        
    },
    methods : {
        loadProfile : function() {
            axios.get("http://localhost:8080/WebProject/data/apartments/")
                .then(function(response) {
                    if (response.status == 200) {
                        Vue.set(vue, "apartments", response.data)
                        Vue.set(vue, "apartmentsDownloaded", true);
                    }
                    else {
                        alert("Couldn't load apartments.")
                    }
                })
        },
        logout : function() {
            console.log("Loggin out...")
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
