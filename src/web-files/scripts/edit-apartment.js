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
        mainImage : "",
        calendar : undefined,
        allAmenities : [],
        selectedAmenities : [],
        base64_array : [],
        oldImageLinks : [],
        imagesChanged : false,
        apartmentForUpload : undefined
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
                        vue.oldImageLinks = ap.images;

                        // Put element references from allAmenities into selectedAmenities so they can be highlighted!
                        for (let i = 0; i < vue.allAmenities.length; i++) {
                            for (let j = 0; j < ap.amenities.length; j++) {
                                if (vue.allAmenities[i].key == ap.amenities[j].key) {
                                    vue.selectedAmenities.push(vue.allAmenities[i]);
                                    break;
                                }
                            }
                        }

                        Array.prototype.forEach.call(ap.availableDates, date => {
                            vue.calendar.values.push(new Date(date.calendar))
                        })

                        vue.calendar.refresh()

                        // Images
                        axios.all([
                            vue.downloadImage(vue.oldImageLinks[0]),
                            vue.downloadImage(vue.oldImageLinks[1]),
                            vue.downloadImage(vue.oldImageLinks[2]),
                            vue.downloadImage(vue.oldImageLinks[3]),
                            vue.downloadImage(vue.oldImageLinks[4])
                        ])
                        .then(() => {
                            vue.displayAvailableImages();
                            console.log("Displaying images")
                        })

                    }
                })
        },
        update : function() { 
            // Split time input
            let splitCheckIn = this.checkInTime.split(":");
            let splitCheckOut = this.checkOutTime.split(":");
            // Take selected calendar values
            let dates = []
                vue.calendar.values.forEach(date => {
                    dates.push({
                        "ticks" : date.getTime()
                    })      
                });
            let accommodationType = (vue.type == "Apartment" ? "APARTMENT" : "ROOM")


            // Create an object to upload
            vue.apartmentForUpload = {
                key : vue.key,
                title : this.name,
                type : accommodationType,
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
                images : [],
                amenities : vue.selectedAmenities
            }

            // Array of images to be uploaded if there was a change
            if (vue.imagesChanged) {
                let jsonImages = []
                Array.prototype.forEach.call(vue.base64_array, base64Image => {
                    jsonImages.push({
                        base64_string : base64Image
                    })
                })

                console.log("Updated apartment images before: " + vue.apartmentForUpload.images.length)
                axios.all([
                    vue.uploadImage(jsonImages[0]),
                    vue.uploadImage(jsonImages[1]),
                    vue.uploadImage(jsonImages[2]),
                    vue.uploadImage(jsonImages[3]),
                    vue.uploadImage(jsonImages[4])
                ])
                .then(() => {
                    console.log("Updated apartment images after: " + vue.apartmentForUpload.images.length)
                    vue.uploadApartment(vue.apartmentForUpload)
                        .then(() => {
                            window.location.href = "/WebProject"
                        })
                        .catch(error => alert(error.response.data))
                })
            }
            else {
                vue.apartmentForUpload.images = vue.oldImageLinks
                vue.uploadApartment(vue.apartmentForUpload)
                .then(() => {
                    window.location.href = "/WebProject"
                })
                .catch(error => alert(error.response.data))
            }
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
        },
        uploadApartment(apartment) {
            return axios.put("/WebProject/data/apartments", apartment)
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
         uploadImage(image) {
             return axios.post("/WebProject/data/images", image)
             .then(response => {
                 if (response.data) {
                    vue.apartmentForUpload.images.push(response.data)
                    console.log("Pushed " + response.data + " to the updated apartment object.");
                 }
             })
         },
         downloadImage(image) {
             return axios.get("/WebProject/data/images/" + image)
                .then (response => {
                    if (response.status == 200  ||  response.data) {
                        vue.base64_array.push(response.data.base64_string);
                    }
                })
                .catch(() => {
                    console.log("Failed to fatch image.")
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
