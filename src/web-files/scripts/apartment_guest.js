let vue = new Vue({
    el :"#vue-apartment",
    data : {
        apartment : {},
        apartmentLoaded : false,
        calendar : undefined,
        calendarVisible : false
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
                        console.log(response.data);
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

        let currentDay = new Date()
        this.calendar = new ej.calendars.Calendar({
            isMultiSelection: false,
            values: [],
            min : currentDay,
            showTodayButton : false
        });
    },
    updated() {
        this.calendar.appendTo('#element');
    }
});
