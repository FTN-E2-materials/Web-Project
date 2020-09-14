let vue = new Vue({
    el :"#vue-create-apartment",
    data : {
        name : "",
        numberOfGuests: "", 
        numberOfRooms: "",
        checkInTime: "",
        checkOutTime: "",
        pricePerNight: "", 
        streetName: "", 
        streetNumber: "",
        city: "",
        postalCode: "",
        type: "Apartment",
        amenities: [],
        imageLink : "",
        calendar : undefined,
        allAmenities : [],
        selectedAmenities : []
    },
    methods : {
        create : function() { 
            let splitCheckIn = this.checkInTime.split(":");
            let splitCheckOut = this.checkOutTime.split(":");
            let dates = []
                vue.calendar.values.forEach(date => {
                    dates.push({
                        "ticks" : date.getTime()
                    })      
                });
            let accommodationType = (vue.type === "Apartment" ? "APARTMENT" : "ROOM")

            let apartment = {
                title : this.name,
                type : accommodationType,
                availableDates : dates,
                numberOfRooms : this.numberOfRooms,
                numberOfGuests : this.numberOfGuests,
                pricePerNight : this.pricePerNight,
                checkInTime : {
                    hours : splitCheckIn[0],
                    minutes : splitCheckIn[1]
                },
                checkOutTime : {
                    hours : splitCheckOut[0],
                    minutes : splitCheckOut[1]
                },
                location : {
                    longitude : 0, 
                    latitude : 0,
                    address : {
                        streetName : this.streetName,
                        streetNumber : this.streetNumber,
                        city : {
                            name : this.city,
                            postalCode : this.postalCode
                        }
                    }
                },
                imageLink : this.imageLink,
                amenities : vue.selectedAmenities
            }

            axios.post("http://localhost:8080/WebProject/data/apartments", apartment)
                .then(function(response) {
                    if (response.status === 200) {
                        window.location.href = "http://localhost:8080/WebProject";
                    }
                    else {
                        alert(response.data);
                    }
                })
                .catch(error => {
                    console.log(error)
                })
        },
        selectAll : function() {
            /** TODO Call clear values for the current month to avoid duplicates! */
            let day = 1
            let currMonth = vue.calendar.currentDate.getMonth()
            let currYear = vue.calendar.currentDate.getFullYear()

            while(true){
                let date = new Date(currYear, currMonth, day++)
                if(date.getMonth() != currMonth)
                    break;
                vue.calendar.values.push(date)   
            }

            vue.calendar.refresh()
        },
        clearAll : function() {
            vue.calendar.values = []
            vue.calendar.refresh()
        },
        getAmenities : function() {
            axios.get("/WebProject/data/amenities")
                .then(response => {
                    if (response.status == 200) {
                        Vue.set(vue, "allAmenities", response.data)
                    }
                })
        },
        addAmenity : function(amenityID, index) {
            vue.selectedAmenities.push(vue.allAmenities[index]);
        },
        removeAmenity : function(amenityID) {
            vue.selectedAmenities = vue.selectedAmenities.filter(function(element) {
                return element.key != amenityID;
            })
        }
    },
    beforeMount() {
        let currentDay = new Date()
        this.calendar = new ej.calendars.Calendar({
            isMultiSelection: true,
            values: [],
            min : currentDay,
            showTodayButton : false
        });
    },
    mounted() {
        this.calendar.appendTo('#element');
        this.getAmenities();
    }
});
