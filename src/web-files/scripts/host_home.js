let vue = new Vue({
    el :"#vue-apartments",
    data : {
        apartments : [],
        apartmentsLoaded : false,
        apartmentImages : new Map()
    },
    methods : {
        getApartments : function() {
            return axios.get("http://localhost:8080/WebProject/data/apartments/")
                .then(function(response) {
                    if (response.status == 200) {
                        Array.prototype.forEach.call(response.data, apartment => {
                            apartment.rating = Math.round(apartment.rating * 100)/100
                        })
                        Vue.set(vue, "apartments", response.data)
                        Vue.set(vue, "apartmentsLoaded", true);

                        vue.getApartmentImages()
                    }
                    else {
                        alert("Couldn't load apartments.")
                    }
                })
        },
        getApartmentImages() {
            Array.prototype.forEach.call(vue.apartments, apartment => {
                let tokens = apartment.mainImage.split("/");

                if (tokens[0].includes("http")) {
                    Vue.set(vue, "apartmentImages", new Map(vue.apartmentImages.set(apartment.key, apartment.mainImage)))
                }
                else {
                axios.get("/WebProject/data/images/" + apartment.mainImage)
                    .then(response => {
                        if (response.status == 200) {
                            Vue.set(vue, "apartmentImages", new Map(vue.apartmentImages.set(apartment.key, response.data.base64_string)))
                        }
                    })
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
        },
        loadMainImage(apartment) {
            
        }
    },
    beforeMount() {
        this.getApartments()
    }
});
