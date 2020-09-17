let vue = new Vue({
    el :"#vue-reservations",
    data : {
        reservations : [],
        reservationsLoaded : false,
        apartmentImages : new Map()
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
                            this.getImage(reservation.apartment.mainImage)
                                .then(response => {
                                    if (response.status == 200) {
                                        Vue.set(vue, "apartmentImages", new Map(vue.apartmentImages.set(reservation.apartment.key, response.data.base64_string)))
                                    }
                                })

                            this.getApartment(reservation.apartment.key)
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
        getImage : function(imageID) {
            return axios.get("/WebProject/data/images/" + imageID)
        },
        getApartment : function(apartmentID) {
            return axios.get("/WebProject/data/apartments/" + apartmentID)
        },
        cancel : function(reservationID, index) {
            let wrapper = {
                stringKey : reservationID
            }

            axios.put("/WebProject/data/reservations/cancel", wrapper)
                .then(response => {
                    if (response.status == 200) {
                        vue.reservations[index].status = "CANCELLED"
                    }
                })
                .catch (error => {
                    alert(error.response.data);
                })
        },
        sortByLowestPrice() {
            let sortFunction = function(a,b){
                if (a.price < b.price) {
                    return -1;
                }
                if (a.price > b.price) {
                    return 1;
                }
                return 0;
            }

            vue.reservations.sort(sortFunction);
        },
        sortByHighestPrice() {
            let sortFunction = function(a,b){
                if (a.price < b.price) {
                    return 1;
                }
                if (a.price > b.price) {
                    return -1;
                }
                return 0;
            }

            vue.reservations.sort(sortFunction);
        }
    },
    beforeMount() {
        this.getReservations();
    }
});
