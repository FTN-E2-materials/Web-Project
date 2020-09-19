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
        calendar : undefined,
        allAmenities : [],
        selectedAmenities : [],
        base64_array : [],
        apartmentForUpload : undefined
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

            vue.apartmentForUpload = {
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
                images : [],
                mainImage : undefined,
                amenities : vue.selectedAmenities
            }

            // Create a promise for each image upload, then wait for those promises in order to continue 
            
            axios.all([
                vue.uploadImage(vue.base64_array[0]),
                vue.uploadImage(vue.base64_array[1]),
                vue.uploadImage(vue.base64_array[2]),
                vue.uploadImage(vue.base64_array[3]),
                vue.uploadImage(vue.base64_array[4])
            ])
            .then(() => {
                vue.apartmentForUpload.mainImage = vue.apartmentForUpload.images[0].key // First image is the main one
                axios.post("http://localhost:8080/WebProject/data/apartments", vue.apartmentForUpload)
                .then(function(response) {
                    if (response.status === 200) {
                        window.location.href = "http://localhost:8080/WebProject";
                    }
                    else {
                        alert(response.data);
                    }
                })
                .catch(error => {
                    alert(error.response.data)
                })
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
                        let amenities = []
                        Array.prototype.forEach.call(response.data, amenity => {
                            amenities.push({
                                key : amenity.key,
                                name : amenity.name
                            })
                        })
                        Vue.set(vue, "allAmenities", amenities)
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
        },
         displayAvailableImages() {
            vue.base64_array = vue.base64_array.filter(function (el) {
                return el != null;
              });

            if (vue.base64_array[0]) {
                img1.src = vue.base64_array[0]
             }
             else {
                 img1.src = "https://www.htmlcsscolor.com/preview/gallery/B1B1B1.png"
             }
             if (vue.base64_array[1]) {
                img2.src = vue.base64_array[1]
             }
             else {
                 img2.src = "https://www.htmlcsscolor.com/preview/gallery/B1B1B1.png"
             }
             if (vue.base64_array[2]) {
                img3.src = vue.base64_array[2]
             }
             else {
                 img3.src = "https://www.htmlcsscolor.com/preview/gallery/B1B1B1.png"
             }
             if (vue.base64_array[3]) {
                img4.src = vue.base64_array[3]
             }
             else {
                 img4.src = "https://www.htmlcsscolor.com/preview/gallery/B1B1B1.png"
             }
             if (vue.base64_array[4]) {
                img5.src = vue.base64_array[4]
             }
             else {
                 img5.src = "https://www.htmlcsscolor.com/preview/gallery/B1B1B1.png"
             }
         },
         uploadImage(image64) {
             let imageObj = {
                 base64_string : image64
             }
             return axios.post("/WebProject/data/images", imageObj)
             .then(response => {
                 if (response.data) {
                    vue.apartmentForUpload.images.push(response.data)
                 }
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
