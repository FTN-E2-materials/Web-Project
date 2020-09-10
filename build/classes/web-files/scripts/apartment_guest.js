let vue = new Vue({
    el :"#vue-apartment",
    data : {
        apartment : {},
        apartmentLoaded : false,
        calendar : undefined,
        calendarVisible : false,
        availableDates : [],
        numberOfNights : 1,
        calendarRendered : false
    },
    methods : {
        getApartment : function() {
            let addressBarTokens = window.location.href.split("/");
            let apartmentID = addressBarTokens[addressBarTokens.length-1];

            axios.get("http://localhost:8080/WebProject/data/apartments/" + apartmentID)
                .then(function(response) {
                    if (response.status == 200) {
                        Vue.set(vue, "apartment", response.data);
                        Vue.set(vue, "apartmentLoaded", true);
                        response.data.availableDates.forEach(date => {
                            vue.availableDates.push(new Date(date.calendar))
                        })
                    }
                })
        },
        goToReviews : function() {
            window.location.href += "/reviews"
        },
        createReservation : function() {
            let reservation = {
                startingDate : vue.calendar.value,
                numberOfNights : vue.numberOfNights,
                price : vue.apartment.pricePerNight * vue.numberOfNights,
                reservationMessage : "Hello there, this is a reservation message",
                status : "CREATED",
                apartment : {
                    key : vue.apartment.key,
                    title : vue.apartment.title,
                    numberOfRooms : vue.apartment.numberOfRooms,
                    numberOfGuests : vue.apartment.numberOfGuests,
                    imageLink : vue.apartment.imageLink,
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
        },
        showCalendar : function() {
            Vue.set(vue, "calendarVisible", true)


        },
    },
    beforeMount : function() {
        this.getApartment();
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
            alert("appended")
            vue.calendarRendered = true
        }
    }
});
