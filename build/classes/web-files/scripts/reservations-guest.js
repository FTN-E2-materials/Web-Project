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
                        response.data.forEach(reservation => {      // Cut ratings to 2 decimals short
                            reservation.apartment.rating = Math.round(reservation.apartment.rating * 100)/100
                        })
                        Vue.set(vue, "reservations", response.data);
                        Vue.set(vue, "reservationsLoaded", true);

                        vue.reservations.forEach(reservation => {
                            axios.get("/WebProject/data/apartments/" + reservation.apartment.key)
                                .then(response => {
                                    if (response.status == 200) {
                                        reservation.apartment.rating = Math.round(response.data.rating * 100)/100
                                    }
                                })
                        })
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
            let wrapper = {
                stringKey : reservationID
            }

            axios.put("/WebProject/data/reservations/cancel", wrapper)
                .then(response => {
                    if (response.status == 200) {
                        window.location.href = ""   // This means refresh?
                    }
                })
                .catch (error => {
                    console.log(error);
                })
        }
    },
    beforeMount() {
        this.getReservations();
    }
});
