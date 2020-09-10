let vue = new Vue({
    el :"#vue-apartment",
    data : {
        apartment : {},
        apartmentLoaded : false,
        calendar : undefined,
        calendarVisible : false,
        availableDates : []
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
            alert("Creating reservation...")
        },
        hideCalendar : function() {
            Vue.set(vue, "calendarVisible", false);
        },
        showCalendar : function() {
            Vue.set(vue, "calendarVisible", true)
        }
    },
    beforeMount() {
        this.getApartment();
    },
    updated() {
        let currentDay = new Date()
        this.calendar = new ej.calendars.Calendar({
            isMultiSelection: false,
            values: [],
            min : currentDay,
            showTodayButton : false,
            renderDayCell: disabledDate,
        });

        this.calendar.appendTo('#element');

        function disabledDate(args) {
            for (let i = 0; i < vue.availableDates.length; i++) {
                if (vue.availableDates[i].toDateString() === args.date.toDateString()) {
                    return
                }
            }
            args.isDisabled = true 
        }
    }
});
