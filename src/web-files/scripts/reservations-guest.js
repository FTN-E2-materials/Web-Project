let vue = new Vue({
    el :"#vue-reservations",
    data : {
        reservations : [],
        reservationsLoaded : false
    },
    methods : {
        getReservations : function() {
            axios.get("/WebProject/data/reservations/")
                .then(response => {
                    if (response.status === 200) {
                        Vue.set(vue, "reservations", response.data);
                        Vue.set(vue, "reservationsLoaded", true);
                    }
                })
                .catch (error => {
                    console.log(error);
                })
        },
        goToApartment : function(apartmentID) {
            window.location.href = "/WebProject/apartments/" + apartmentID
        },
        cancel : function(reservationID) {
            
        }
    },
    beforeMount() {
        this.getReservations();
    }
});
