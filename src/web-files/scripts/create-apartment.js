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
        imageLink : ""
    },
    methods : {
        create : function() { 
            let splitCheckIn = this.checkInTime.split(":");
            let splitCheckOut = this.checkOutTime.split(":");
            let apartment = {
                title : this.name,
                type : function() {
                    if (this.type === "Apartment") {
                        return "APARTMENT";
                    }
                    else {
                        return "ROOM";
                    }
                },
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
                amenities : this.amenities
            }

            axios.post("http://localhost:8080/WebProject/data/apartments", apartment)
                .then(function(response) {
                    if (response.status === 200) {
                        window.location.href = "http://localhost:8080/WebProject";
                    }
                    else {
                        alert(response.data);
                    }
                }
            )
        }
    }
});
