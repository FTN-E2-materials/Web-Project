let vue = new Vue({
    el :"#vue-reservations",
    data : {
        reservations : [],
        holder : [],
        reservationsLoaded : false,
        apartmentImages : new Map(),
        query : "",
        reservationStatus : "All"
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
                    alert(error.response.data)
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
                        vue.reservations[index].status = "DENIED"   
                    }
                })
                .catch (error => {
                    alert(error.response.data)
                })
        },
        approve : function(reservationID, index) {
            let wrapper = {
                stringKey : reservationID
            }

            axios.put("/WebProject/data/reservations/approve", wrapper)
                .then(response => {
                    if (response.status == 200) {
                        vue.reservations[index].status = "APPROVED"
                    }
                })
                .catch (error => {
                    alert(error.response.data)
                })
        },
        finish : function(reservationID, index) {
            let wrapper = {
                stringKey : reservationID
            }

            axios.put("/WebProject/data/reservations/finish", wrapper)
                .then(response => {
                    if (response.status == 200) {
                        vue.reservations[index].status = "FINISHED"   
                    }
                })
                .catch (error => {
                    alert(error.response.data)
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
        },
        searchByGuestName() {
            if (!vue.query) {
                if (vue.holder.length > 0) {
                    Vue.set(vue, "reservations", vue.holder)
                }
            }
            else {
                vue.query = vue.query.toLowerCase()

                if (vue.holder.length == 0) {
                    vue.holder = vue.reservations
                }
                vue.reservations = []

                Array.prototype.forEach.call(vue.holder, reservation => {
                    if (reservation.guestID.toLowerCase().includes(vue.query)) {
                        vue.reservations.push(reservation)
                    }
                })
            }
        },
        filterType() {
            if (vue.reservationStatus === "All") {
                if (vue.holder.length > 0) {
                    Vue.set(vue, "reservations", vue.holder)
                }
                return;
            }

            if (vue.holder.length == 0) {
                vue.holder = vue.reservations
            }
            vue.reservations = []

            Array.prototype.forEach.call(vue.holder, reservation => {
                if (reservation.status === vue.reservationStatus) {
                    vue.reservations.push(reservation)
                }
            })
        }
    },
    beforeMount() {
        this.getReservations();
    }
});
