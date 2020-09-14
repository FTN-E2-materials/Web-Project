let vue = new Vue({
    el :"#vue-apartments",
    data : {
        apartments : [],
        apartmentsLoaded : false
    },
    methods : {
        searchApartments : function() {
            axios.get("http://localhost:8080/WebProject/data/apartments/")
                .then(function(response) {
                    if (response.status == 200) {
                        Vue.set(vue, "apartments", response.data)
                        Vue.set(vue, "apartmentsLoaded", true);
                    }
                    else {
                        alert("Couldn't load apartments.")
                    }
                })
        },
        goToApartment : function(apartmentID) {
            window.location.href = "http://localhost:8080/WebProject/apartments/" + apartmentID;
        },
        openApartmentCreation : function() {
            window.location.href = "http://localhost:8080/WebProject/createApartment"
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
        },
        createApartment : function() {
            window.location.href = "/WebProject/createApartment"
        }
    },
    beforeMount() {
        this.searchApartments();
    }
});
