let vue = new Vue({
    el :"#vue-apartments",
    data : {
        apartments : []
    },
    methods : {
        searchApartments : function() {
            axios.get("http://localhost:8080/WebProject/data/apartments/")
                .then(function(response) {
                    if (response.status == 200) {
                        Vue.set(vue, "apartments", response.data)
                    }
                    else {
                        alert("Couldn't load apartments.")
                    }
                })
        },
        goToApartment : function(apartmentID) {
            axios.get("http://localhost:8080/WebProject/data/apartments/" + apartmentID)
                .then(function(response) {
                    if (response.status == 200) {
                        console.log(response.data);
                    }
                    else {
                        alert("Couldn't load apartments.")
                    }
                })
        },
        logOut : function() {
            axios.post("http://localhost:8080/WebProject/data/auth/logout")
                .then(function(response) {
                    if (response.status == 200) {
                        window.location.href = "http://localhost:8080/WebProject"
                    }
                    else {
                        alert("Couldn't log out.")
                    }
                })
        }
    },
    beforeMount() {
        this.searchApartments();
    }
});
