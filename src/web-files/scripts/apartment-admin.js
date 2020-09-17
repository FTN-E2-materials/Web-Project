let vue = new Vue({
    el :"#vue-apartment",
    data : {
        apartment : {},
        apartmentLoaded : false,
        apartmentImage : "",
        deletionMessage : ""
    },
    methods : {
        loadData : function() {
            let addressBarTokens = window.location.href.split("/");
            let apartmentID = addressBarTokens[addressBarTokens.length-1];

            rest.getApartment(apartmentID)
                .then(function(response) {
                    if (response.data) {
                        Vue.set(vue, "apartment", response.data);
                        Vue.set(vue, "apartmentLoaded", true);

                        rest.getImage(response.data.mainImage)
                            .then(response => {
                                if (response.data) {
                                    vue.apartmentImage = response.data.base64_string;
                                }
                            })
                    }
                })
        }, 
        edit : function() {
            window.location.href = "http://localhost:8080/WebProject/apartments/edit/" + vue.apartment.key;
        },
        goToReviews : function() {
            window.location.href = "/WebProject/reviews/" + vue.apartment.key
        },
        deleteApartment : function() { 
            let apartmentID = vue.apartment.key
            rest.deleteApartment({
                stringKey : apartmentID
            })
            .then(response => {
                if (response.data) {
                    vue.deletionMessage = "This apartment has been successfully deleted."
                }
            })
        }
    },
    beforeMount() {
        this.loadData();
    }
});
