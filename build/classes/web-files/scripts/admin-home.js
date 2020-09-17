let vue = new Vue({
    el :"#vue-apartments",
    data : {
        apartments : [],
        apartmentsLoaded : false,
        apartmentImages : new Map()
    },
    methods : {
        getApartments : function() {
            rest.getApartments()
                .then(function(response) {
                    if (response.data) {
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
                rest.getImage(apartment.mainImage)
                    .then(response => {
                        if (response.data) {
                            Vue.set(vue, "apartmentImages", new Map(vue.apartmentImages.set(apartment.key, response.data.base64_string)))
                        }
                    })
            })
        },
        goToApartment : function(apartmentID) {
            window.location.href = "/WebProject/apartments/" + apartmentID;
        }
    },
    beforeMount() {
        this.getApartments()
    }
});
