let vue = new Vue({
    el :"#vue-edit-apartment",
    data : {
        key : "",
        name : "",
        status : "",
        numberOfGuests: "", 
        numberOfRooms: "",
        checkInTime: "",
        checkOutTime: "",
        pricePerNight: "", 
        streetName: "", 
        streetNumber: "",
        city: "",
        postalCode: "",
        type: "",
        amenities: [],
        imageLink : "",
        calendar : undefined,
        allAmenities : [],
        selectedAmenities : []
    },
    methods : {
        get : function() {
            let tokens = window.location.href.split("/")
            let apartmentID = tokens[tokens.length-1]
            console.log(apartmentID);

            axios.get("/WebProject/data/apartments/" + apartmentID)
                .then(result => {
                    if (result.status == 200) {
                        let ap = result.data

                        vue.key = ap.key
                        vue.name = ap.title
                        vue.numberOfGuests = ap.numberOfGuests
                        vue.numberOfRooms = ap.numberOfRooms
                        vue.checkInTime = ap.checkInTime.hours + ":" + ap.checkInTime.minutes
                        vue.checkOutTime = ap.checkOutTime.hours + ":" + ap.checkOutTime.minutes
                        vue.pricePerNight = ap.pricePerNight
                        vue.streetName = ap.location.address.streetName
                        vue.streetNumber = ap.location.address.streetNumber
                        vue.city = ap.location.address.city.name
                        vue.postalCode = ap.location.address.city.postalCode
                        vue.type = (ap.type == "APARTMENT" ? "Apartment" : "Room")
                        vue.imageLink = ap.imageLink
                        vue.status = ap.status;

                        // Put element references from allAmenities into selectedAmenities so they can be highlighted!
                        for (let i = 0; i < vue.allAmenities.length; i++) {
                            for (let j = 0; j < ap.amenities.length; j++) {
                                if (vue.allAmenities[i].key == ap.amenities[j].key) {
                                    vue.selectedAmenities.push(vue.allAmenities[i]);
                                }
                            }
                        }

                        Array.prototype.forEach.call(ap.availableDates, date => {
                            vue.calendar.values.push(new Date(date.calendar))
                        })

                        vue.calendar.refresh()
                    }
                })
        },
        update : function() { 
            let splitCheckIn = this.checkInTime.split(":");
            let splitCheckOut = this.checkOutTime.split(":");
            let dates = []
                vue.calendar.values.forEach(date => {
                    dates.push({
                        "ticks" : date.getTime()
                    })      
                });

            let apartment = {
                key : vue.key,
                title : this.name,
                type : function() {
                    if (this.type === "Apartment") {
                        return "APARTMENT";
                    }
                    else {
                        return "ROOM";
                    }
                },
                status : vue.status,
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

            axios.put("/WebProject/data/apartments", apartment)
                .then(function(response) {
                    if (response.status === 200) {
                        window.location.href = "/WebProject";
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
                        response.data.forEach(amenity => {
                            vue.allAmenities.push({
                                "name" : amenity.name,
                                "key" : amenity.key
                            })
                        })
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
        this.getAmenities();
        this.get();
        this.calendar.appendTo('#element');
    }
});
