let vue = new Vue({
    el :"#vue-apartment",
    data : {
        apartment : {},
        apartmentLoaded : false,
        calendar : undefined,
        calendarVisible : false,
        availableDates : [],
        numberOfNights : 1,
        reservationMessage : "",
        calendarRendered : false,
        apartmentImage : ""
    },
    methods : {
        loadData : function() {
            let addressBarTokens = window.location.href.split("/");
            let apartmentID = addressBarTokens[addressBarTokens.length-1];

            this.getApartment(apartmentID)
                .then(function(response) {
                    if (response.status == 200) {
                        Vue.set(vue, "apartment", response.data);
                        Vue.set(vue, "apartmentLoaded", true);

                        vue.getImage(response.data.mainImage)
                            .then(response => {
                                if (response.status == 200) {
                                    vue.apartmentImage = response.data.base64_string
                                }
                            })

                        response.data.availableDates.forEach(date => {
                            vue.availableDates.push(new Date(date.calendar))
                        })
                    }
                })
        },
        goToReviews : function() {
            let tokens = window.location.href.split("/")
            let apartmentID = tokens[tokens.length-1]

            window.location.href = "/WebProject/reviews/" + apartmentID
        },
        getImage : function(imageID) {
            return axios.get("/WebProject/data/images/" + imageID)
        },
        getApartment : function(apartmentID) {
            return axios.get("/WebProject/data/apartments/" + apartmentID)
        },
        createReservation : function() {
            let reservation = {
                startingDate : {
                    ticks : vue.calendar.value.getTime()
                },
                numberOfNights : vue.numberOfNights,
                price : vue.apartment.pricePerNight * vue.numberOfNights,
                reservationMessage : vue.reservationMessage,
                status : "CREATED",
                apartment : {
                    key : vue.apartment.key,
                    title : vue.apartment.title,
                    numberOfRooms : vue.apartment.numberOfRooms,
                    numberOfGuests : vue.apartment.numberOfGuests,
                    mainImage : vue.apartment.mainImage,
                    rating : vue.apartment.rating,
                    numberOfRatings : vue.apartment.numberOfRatings,
                    hostID : vue.apartment.hostID
                }
            }
            
            axios.post("/WebProject/data/reservations", reservation)
                .then(response => {
                    if (response.status === 200) {
                        alert("Successfully created reservation!")
                    }
                })
                .catch(error => {
                    console.log(error)
                })
        },
        hideCalendar : function() {
            Vue.set(vue, "calendarVisible", false);
            vue.calendarRendered = false;
        },
        showCalendar : function() {
            Vue.set(vue, "calendarVisible", true)
        },
    },
    beforeMount : function() {
        this.loadData();
    },
    mounted : function() {
        let currentDay = new Date()
        this.calendar = new ej.calendars.Calendar({
            isMultiSelection: false,
            values: [],
            min : currentDay,
            showTodayButton : false,
            renderDayCell: disabledDate,
        });
        
        function disabledDate(args) {
            for (let i = 0; i < vue.availableDates.length; i++) {
                if (vue.availableDates[i].toDateString() === args.date.toDateString()) {
                    return
                }
            }
            args.isDisabled = true 
        }
    },
    updated : function() {
        if (vue.calendarRendered === false  &&  vue.calendarVisible === true){
            vue.calendar.appendTo('#element');
            vue.calendarRendered = true
        }
    },
    getYMD : function(date) {
        let month = dateObj.getUTCMonth() + 1; //months from 1-12
        let day = dateObj.getUTCDate();
        let year = dateObj.getUTCFullYear();

        return day + "/" + month + "/" + year
    }
});
